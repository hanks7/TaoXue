package com.taoxue.ui.module.classification.audioService;

import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;
import com.taoxue.app.AppConfig;
import com.taoxue.utils.LogUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by User on 2017/8/28.
 */

public class AudioDiskCache {
    Context context;
    DiskLruCache mDiskLruCache;
    private  long maxsize=150 * 1024 * 1024;//最大缓存大小

  public static   AudioDiskCache mAudioDiskCache;

    public static AudioDiskCache getInstance(Context context) {
        if (mAudioDiskCache==null){
           return mAudioDiskCache=new AudioDiskCache(context);
        }
        return mAudioDiskCache;
    }
    public AudioDiskCache(Context context) {
        this.context = context;
        LogUtils.D("context--->"+context);
        mDiskLruCache = getDisk();
    }

    public File getDiskCacheDir(String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = AppConfig.getAppCachePath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public int getAppVersion() {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public void addDiskCache(final String urlPath) {
        if (mDiskLruCache == null) {
            LogUtils.D("addDiskCache===null");
            return;
        }
        if (getFileDescriptor(urlPath)!=null){//当已缓存过时不再缓存
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtils.D("加入缓存");
                    String key = hashKeyForDisk(urlPath);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(urlPath, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public FileDescriptor getFileDescriptor(String  urlPath) {
        if (mDiskLruCache==null){
            return null;
        }
        FileDescriptor mFileDescriptor;
        try {
            String key = hashKeyForDisk(urlPath);
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                if (is instanceof FileInputStream) {
                    mFileDescriptor = ((FileInputStream) is).getFD();
                    LogUtils.D("从磁盘中获取文件");
                  return mFileDescriptor;
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
  public  void remove(String urlPath){//移除
      try {
          String key = hashKeyForDisk(urlPath);
          mDiskLruCache.remove(key);
      } catch (IOException e) {
          e.printStackTrace();
      }
  }

    //删除所有缓存文件
    public  void deleteDiskCache(){
        try{
            if (mDiskLruCache!=null){
                mDiskLruCache.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void closeDiskCache(){
        try{
        if (mDiskLruCache!=null){
            mDiskLruCache.close();

        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public DiskLruCache  getDisk(){
    try {
        File cacheDir = getDiskCacheDir( "audio");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(), 1, maxsize);
        return  mDiskLruCache;
    } catch (IOException e) {
        e.printStackTrace();
        return  null;
    }
}
}
