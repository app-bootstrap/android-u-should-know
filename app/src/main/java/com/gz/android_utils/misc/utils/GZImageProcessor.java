package com.gz.android_utils.misc.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import com.gz.android_utils.GZApplication;
import com.gz.android_utils.concurrency.loop.GZCommonTaskLoop;
import com.gz.android_utils.config.GZConsts;
import com.gz.android_utils.misc.log.GZAppLogger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * created by Zhao Yue, at 3/10/16 - 9:22 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZImageProcessor {

    public static Bitmap loadBitmapWithRequestSize(Uri path, int resultWidth, int resultHeight) {
        InputStream inputStream = null;
        ContentResolver cr = GZApplication.sharedInstance().getApplicationContext().getContentResolver();
        try {
            inputStream = cr.openInputStream(path);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inDither = false;
            options.inScaled = false;
            options.inPurgeable = true;

            BitmapFactory.decodeStream(inputStream, null, options);
            options.inSampleSize = computeSampleSize(options, resultWidth, resultWidth * resultHeight);
            options.inJustDecodeBounds = false;

            inputStream.close();
            inputStream = cr.openInputStream(path);
            // Note:
            // the inputStream should be close and renew.
            // reason: http://stackoverflow.com/questions/2503628/bitmapfactory-decodestream-returning-null-when-options-are-set

            Bitmap bufferBitmap = BitmapFactory.decodeStream(inputStream, null, options);
            //TODO - Android Bug - enable it once the bug gets fixed https://code.google.com/p/android/issues/detail?id=32470
            //Bitmap bufferBitmap = BitmapFactory.decodeFileDescriptor(inputStream.getFD(), null, options);
            if (bufferBitmap != null) {
                Bitmap decodedBitMap = ThumbnailUtils.extractThumbnail(bufferBitmap, resultWidth, resultHeight, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                String realPath = (new File(path.toString())).getAbsolutePath();

                if (decodedBitMap != null) {
                    Bitmap croppedBitMap;
                    if (__getImageOrientation(realPath) == 90 || __getImageOrientation(realPath) == 270)
                        croppedBitMap = scaleBitmap(decodedBitMap, resultHeight, resultWidth);
                    else
                        croppedBitMap = scaleBitmap(decodedBitMap, resultWidth, resultHeight);

                    if (croppedBitMap != decodedBitMap)
                        decodedBitMap.recycle();
                    return croppedBitMap;
                }
            }
        } catch (IOException e) {
            GZAppLogger.e(e);
        } catch (OutOfMemoryError e) {
            GZAppLogger.e(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    GZAppLogger.e(e);
                }
            }
        }
        return null;
    }

    private static int __getImageOrientation(final String file) throws IOException {
        ExifInterface exif = new ExifInterface(file);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return 0;
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270;
            default:
                return 0;
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int sourceWidth = bitmap.getWidth();
        int sourceHeight = bitmap.getHeight();
        if (sourceWidth * maxHeight > sourceHeight * maxWidth) {
            return Bitmap.createScaledBitmap(bitmap, (int) (sourceWidth / (float) sourceHeight * (float) maxHeight), maxHeight, true);
        } else {
            return Bitmap.createScaledBitmap(bitmap, maxWidth, (int) (sourceHeight / (float) sourceWidth * (float) maxWidth), true);
        }
    }

    private static boolean copyFile(String srFile, String dtFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File f1 = new File(srFile);
            File f2 = new File(dtFile);
            in = new FileInputStream(f1);
            out = new FileOutputStream(f2);
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.flush();
            in.close();
            out.close();
            return true;
        } catch (IOException e) {
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                }
            }
        }
        return false;
    }

    public static String saveImageToCameraAlbum(Bitmap bitmap, String description) {
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        String result = null;
        String path = formExternalCameraPath() + System.currentTimeMillis() + ".jpg";
        try {
            File newImage = new File(path);
            OutputStream out = new FileOutputStream(newImage);
            out.write(b);
            out.close();
            result = (Uri.fromFile(newImage)).toString();
        } catch (IOException e) {
        }
        GZAppLogger.i("image saved to local " + result);
        return result;
    }

    public static String formExternalCameraPath() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();
        StringBuilder newPath = new StringBuilder();
        newPath.append(path);
        newPath.append("/Camera/");

        //check if the path exists
        File f = new File(newPath.toString());
        if (f.isDirectory() && f.exists()) {
            // do nothing
        } else
            f.mkdirs();
        return newPath.toString();
    }

    public static byte[] getImagedataFromPath(Uri path){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(GZApplication.sharedInstance().getContentResolver(), path);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsFromBuffer(byteBuffer);
        byte[] bytes = byteBuffer.array();
        return bytes;
    }

    public static Bitmap loadOriginalJpeg(String filePath) {
        if (!GZPathManager.sharedInstance().isFileExists(filePath)) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;

        Bitmap bitmap = null;
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filePath);
            bitmap = BitmapFactory.decodeStream(stream, null, options);
        } catch (Exception e) {
        } catch (OutOfMemoryError e) { //out of memory error is not an EXCEPTION
            System.gc();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                }
            }
        }
        if (bitmap != null) {
            bitmap.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        }
        return bitmap;
    }

    public static void handleCameraData(final String path, final byte[] data){
        GZCommonTaskLoop.getInstance().post(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream outStream = null;
                    ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
                    Bitmap raw = BitmapFactory.decodeByteArray(data, 0, data.length);
                    raw.compress(Bitmap.CompressFormat.JPEG, GZConsts.Camera.Compression_Rate, byteArrayBitmapStream);
                    raw.recycle();

                    File file = new File(path);
                    file.createNewFile();

                    outStream = new FileOutputStream(path);
                    outStream.write(byteArrayBitmapStream.toByteArray());
                    outStream.close();
                } catch (Exception e) {
                    GZAppLogger.e(e);
                }
            }
        });
    }
}
