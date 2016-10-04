package com.gz.android_utils.misc.utils;

import android.os.Environment;

import com.gz.android_utils.GZApplication;
import com.gz.android_utils.misc.log.GZAppLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * created by Zhao Yue, at 25/9/16 - 2:10 PM
 * for further issue, please contact: zhaoy.samuel@gmail.com
 */
public class GZPathManager {

    public static GZPathManager mInstance;

    public static GZPathManager sharedInstance() {
        if (mInstance == null) {
            mInstance = new GZPathManager();
        }

        return mInstance;
    }

    /*Path Manager Configuration*/
    private String rootPath;
    private String userPath;
    private String imageDirPath;
    private String commonDirPath;
    private String multiMediaDirPath;

    private  GZPathManager() {
        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + GZApplication.ApplicationName;
        reset();
    }

    private void reset() {
        checkFolder(rootPath);
        userPath = null;
        imageDirPath = null;
        multiMediaDirPath = null;
    }

    private void checkFolder(String folder) {
        File f = new File(folder);
        if (f.isDirectory() && f.exists()) {
            return;
        }
        boolean b = f.mkdirs();
        if (!b) {
            GZAppLogger.e("create folder:%s error", folder);
        } else {
            try {
                File file = new File(f.getPath() + File.separator + ".nomedia");
                if (!file.exists())
                    file.createNewFile();
            } catch (IOException e) {
                GZAppLogger.e(e.getMessage());
            }
        }
    }

    public synchronized String getUserPath() {
        if (userPath == null) {
            userPath = rootPath + File.separator + GZApplication.ApplicationUserIdentifier;
            checkFolder(userPath);
        }

        return userPath;
    }

    public synchronized String getImageDirPath() {
        if (imageDirPath == null) {
            imageDirPath = getUserPath() + File.separator + "image";
            checkFolder(imageDirPath);
        }

        return imageDirPath;
    }

    public synchronized String getMultiMediaDirPath() {
        if (multiMediaDirPath == null) {
            multiMediaDirPath = getUserPath() + File.separator + "media";
            checkFolder(multiMediaDirPath);
        }

        return multiMediaDirPath;
    }

    public synchronized String getCommonDirPath() {
        if (commonDirPath == null) {
            commonDirPath = getUserPath() + File.separator + "commonDir";
            checkFolder(commonDirPath);
        }

        return commonDirPath;
    }

    public boolean isFileExists(String fullPath) {
        File f = new File(fullPath);
        return f.isFile() && f.exists();
    }

    public void copyFolder(File src, File dest)
            throws IOException{

        if(src.isDirectory()){

            //if directory not exists, create it
            if(!dest.exists()){
                dest.mkdirs();
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyFolder(srcFile,destFile);
            }

        }else{

            //if file, then copy it
            //Use bytes stream to support all file types
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }

            GZAppLogger.i("Melon File Manager file shifted " + dest.getName());
            in.close();
            out.close();
        }
    }

    public File[] browseFilesInDir(File dir) {
        if (dir == null) {
            return new File[0];
        }

        File[] filesList = dir.listFiles();
        return filesList;
    }

}

