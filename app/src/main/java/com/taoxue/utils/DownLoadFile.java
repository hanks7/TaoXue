package com.taoxue.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import com.taoxue.app.AppConfig;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.module.classification.CommitContent;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 下载图片
 */

public class DownLoadFile {
    public static long downLoadId;
    private static Context context;

    public static void setContext(Context context) {
        DownLoadFile.context = context;
    }

    private static DownLoadBroadcastReceiver mBroadcastReceiver;

    public static void downloadImage(Context context, String url) {
        setContext(context);
      File  file=searchFileByFilePath(url.substring(url.lastIndexOf("/")+1),new File(AppConfig.getDownLoadImagePath()),url.substring(url.lastIndexOf(".")));
        if (file!=null){
            ((BaseActivity) context).showToast("图片保存在" + AppConfig.getDownLoadImagePath() + "文件夹");
            return;
        }
        try {
            DownloadManager downloadManager;
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            //可使用部分
//            // 仅允许在WIFI连接情况下下载
//            request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
//            /**设置漫游状态下是否可以下载*/
//            request.setAllowedOverRoaming(false);

//            // 通知栏中将出现的内容
//            request.setTitle("我的下载");
//            request.setDescription("下载一个大文件");
//            // 下载过程和下载完成后通知栏有通知消息。
//            request.setNotificationVisibility(Request.VISIBILITY_VISIBLE | Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//
//            // 此处可以由开发者自己指定一个文件存放下载文件。
//            // 如果不指定则Android将使用系统默认的
//            // request.setDestinationUri(Uri.fromFile(new File("")));
//
//            // 默认的Android系统下载存储目录
//            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "my.apk");


            //设置文件存放路径
            request.setDestinationInExternalPublicDir(AppConfig.getDownLoadImagePath(), url.substring(url.lastIndexOf("/")));
            downLoadId = downloadManager.enqueue(request);

            mBroadcastReceiver = new DownLoadBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            context.registerReceiver(mBroadcastReceiver, intentFilter);//注册来电监听
//            LogUtils.D("reference" + reference);
//            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void unregister() {
        if (mBroadcastReceiver != null) {
            context.unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver=null;
        }
    }

    private static class DownLoadBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

                if (completeDownloadId == downLoadId) {
                        if (context instanceof BaseActivity) {
                            ((BaseActivity) context).showToast("图片保存在" + AppConfig.getDownLoadImagePath() + "文件夹");

                        }

                }
                unregister();
            }
        }
    }

    static File  mfile;
    static String url;

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        DownLoadFile.url = url;
    }

    //下载图书
    public static void downloadPdf(Context context, String url) {
        setContext(context);
        setUrl(url);
        File  file=searchFileByFilePath(url.substring(url.lastIndexOf("/")+1),new File(AppConfig.getDownLoadImagePath()),url.substring(url.lastIndexOf(".")));
        if (file!=null){
                //可进行数据操作
            return;
        }

        try {
            DownloadManager downloadManager;
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            //设置文件存放路径
//            request.setDestinationInExternalPublicDir(AppConfig.getDownLoadBookPath(), url.substring(url.lastIndexOf("/")));
            downLoadId = downloadManager.enqueue(request);

            mBroadcastReceiver = new DownLoadBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            context.registerReceiver(mBroadcastReceiver, intentFilter);//注册来电监听
//            LogUtils.D("reference" + reference);
//            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//       s 代表后缀名
    //filename  表示文件的名称 包含后缀名
    //mFile 代表名称在的路径
    //根据文件后缀名查找文件是否存在
    private static  File searchFileByFilePath( String fileName,File mFile,final String s) {
        LogUtils.D("fileName--->" + fileName);
//    /判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            if (!mFile.exists()){
                return null;
            }
            File[] files = mFile.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(s);//根据后缀名
                }
            });

            if (files.length > 0) {
                for (File file : files) {
                    LogUtils.D("files--->" + file.getName());
                    if (fileName.equals(file.getName())) {
                        LogUtils.D("找到文件");
                        return file;
                    }
                }
            }
        }
        ;
        return null;
    }

}

