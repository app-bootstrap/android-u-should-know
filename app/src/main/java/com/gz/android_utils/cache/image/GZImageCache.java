package com.gz.android_utils.cache.image;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;
import com.gz.android_utils.config.GZConsts;
import com.gz.android_utils.hardware.GZEnvInfo;
import com.gz.android_utils.misc.log.GZAppLogger;
import com.gz.android_utils.misc.utils.GZImageProcessor;

import java.io.File;

/**
 * created by Zhao Yue, at 3/10/16 - 9:16 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZImageCache {

    private static GZImageCache instance = null;

    public static GZImageCache getInstance(){
        if(instance == null)
            instance = new GZImageCache();
        return instance;
    }

    private BitmapLruCache lruCache;
    private LocalImageCache localImageCache;
    private LocalVideoImageCache localVideoImageCache;
    private GZImageCache(){init();}

    private synchronized void init(){
        //final int cacheSize = 4 * 1024 * 1024; // 4MiB
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        lruCache = new BitmapLruCache(cacheSize);
        localImageCache = new LocalImageCache();
        localVideoImageCache = new LocalVideoImageCache();
    }

    public LocalImageCache getLocalImageCache() {
        return localImageCache;
    }

    public LocalVideoImageCache getLocalVideoImageCache() {
        return localVideoImageCache;
    }

    // url is the absolute image path of the file
    public class LocalImageCache implements ImageLoader.ImageCache{

        public Bitmap getBitmap(String path, int width, int height) {
            if(path == null)
                return null;

            Bitmap cachedData = lruCache.get(path);
            if(cachedData != null && !cachedData.isRecycled()) {
                return  cachedData;
            }

            File f = new File(path);
            if (!f.isFile()) {
                return null;
            }

            Bitmap result = GZImageProcessor.loadBitmapWithRequestSize(Uri.fromFile(f),width, height);
            if (result == null) {
                return null;
            }

            lruCache.put(path, result);
            return result;
        }

        @Override
        public Bitmap getBitmap(String path) {
            return getBitmap(path, GZConsts.ImageCache.Default_Width, GZConsts.ImageCache.Default_Height);
        }

        // so far this part won't be used, it is designed to put downloaded data into local dir
        @Override
        public void putBitmap(String url, Bitmap bitmap) {}
    }

    // url is the absolute image path of the file
    public class LocalVideoImageCache implements ImageLoader.ImageCache{

        @Override
        public Bitmap getBitmap(String path) {
            if(path == null)
                return null;

            Bitmap cachedData = lruCache.get(path);
            if(cachedData != null && !cachedData.isRecycled()) {
                return  cachedData;
            }

            File f = new File(path);
            if (!f.isFile()) {
                return null;
            }

            Bitmap bMap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
            if (bMap == null) {
                return null;
            }

            lruCache.put(path, bMap);
            return bMap;
        }

        // so far this part won't be used, it is designed to put downloaded data into local dir
        @Override
        public void putBitmap(String url, Bitmap bitmap) {}
    }



    private class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

        private BitmapLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        public Bitmap getBitmap(String url) {
            if (url != null) {
                return get(url);
            }
            return null;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            if (url != null && bitmap != null) {
                put(url, bitmap);
            }
        }

        @Override
        @TargetApi(19)
        protected int sizeOf(String key, Bitmap value) {
            if (GZEnvInfo.hasKitKat()) {
                if (!value.isRecycled()) {
                    try {
                        return value.getAllocationByteCount() / 1024;
                    } catch (Exception ex) {
                        GZAppLogger.e(ex);
                    }
                }
                return value.getByteCount() / 1024;
            }
            if (GZEnvInfo.hasHoneycombMR1()) {
                return value.getByteCount() / 1024;
            }
            return value.getRowBytes() * value.getHeight() / 1024;
        }
    }

}
