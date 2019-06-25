package com.taoxue.utils.update;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.utils.UtilToast;
import com.taoxue.utils.permission.PermissionReq;
import com.taoxue.utils.permission.PermissionResultTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


/**
 * @author coolszy
 * @version 现在只显示通知，不显示dialog
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */

public class UpdateManager {

    /*此值直非常重要-在构造方法中传值-存储收到的版本信息*/
    private IsUpdateBean bean;
    private Activity mActivity;


    public UpdateManager(Activity activity, IsUpdateBean bean) {
        this.mActivity = activity;
        this.bean = bean;
    }

    /**
     * 检测软件更新
     * @param flag
     * 为true去升级
     * @return
     */
    public boolean checkUpdate(boolean flag) {
        if (flag ){
            try {
                showNoticeDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
       return false;

    }


    /**
     * 显示软件更新对话框--不需要等待
     */
    private void showNoticeDialog() {
   /*     Utildialog.show(mActivity, "更新", bean.getRemark(), "确定", "取消", false,new Runnable() {
            @Override
            public void run() {
                if (!UtilPermission.judgePermisson(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    return;
                downloadApkShowPrograssStateBar();
            }
        });*/

        LayoutInflater inflaterDl = LayoutInflater.from(mActivity);
        View view =inflaterDl.inflate(R.layout.custom_dialog_two_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(mActivity,R.style.dialog_update_style).create();
        dialog.show();
        dialog.getWindow().setContentView(view);
        dialog.setCanceledOnTouchOutside(false);//点击空白处无法取消

        // 构造对话框
        TextView tvContent = (TextView) view.findViewById(R.id.update_tv_content);
        TextView btnOk = (TextView) view.findViewById(R.id.update_btn_commit);
        tvContent.setText(bean.getRemark());

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PermissionReq.with(mActivity)
                        .permissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        .result(new PermissionResultTask(Manifest.permission.READ_EXTERNAL_STORAGE,mActivity) {
                            @Override
                            public void onGranted() {
                                downloadApkShowPrograssStateBar();
                                dialog.dismiss();
                                downloadApk();
                            }
                        })
                        .request();

            }
        });


    }


    /**
     * 下载apk文件
     */
    private void downloadApkShowPrograssStateBar() {
        //首先检测下载服务是否在运行
//        if(UtilService.isServiceWork(mActivity, MyUpdateService.class.getName())) {
//            UtilToast.t("请稍等，正在下载版本更新!");
//            return;
//        }
        // 启动后台服务下载软件
        Intent intent = new Intent(mActivity, MyUpdateService.class);
        mActivity.startService(intent);
    }


    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;
    /* 更新进度条 */
//    private AnimButtonLayout mAnimButtonLayout;

    HashMap<String, String> mHashMap;//-----------------------------此直非常重要---在构造方法中传值
    //	mHashMap.put("url", "http://gdown.baidu.com/data/wisegame/f98d235e39e29031/baiduxinwen.apk");
    //	mHashMap.put("name", "夜掌柜升级包");
    //	mHashMap.put("version", "");


    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                // 正在下载
                case DOWNLOAD:
//                    // 设置进度条位置
//                    mAnimButtonLayout.setState(AnimDownloadProgressButton.DOWNLOADING);
//                    mAnimButtonLayout.setProgress(progress);
//                    mAnimButtonLayout.setProgressText("下载中", mAnimButtonLayout.getProgress());
//                    if (mAnimButtonLayout.getProgress() + 1 >= 100) {
//                        mAnimButtonLayout.setState(AnimDownloadProgressButton.INSTALLING);
//                        mAnimButtonLayout.setCurrentText("安装中");
//                    }


                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        }

        ;
    };


//    /**
//     * 显示软件下载对话框
//     */
//    public void showDownloadDialog() {
//
//        LayoutInflater inflaterDl = LayoutInflater.from(mActivity);
//        View view = inflaterDl.inflate(R.layout.softupdate_progress, null);
//        mDownloadDialog = new AlertDialog.Builder(mActivity, R.style.CustomDialogII).create();
//        mDownloadDialog.show();
//        mDownloadDialog.getWindow().setContentView(view);
//
//        mAnimButtonLayout = (AnimButtonLayout) view.findViewById(R.id.anim_btn3);
//        mAnimButtonLayout.setCurrentText("安装");
//        mAnimButtonLayout.setTextSize(30f);
//        mAnimButtonLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 下载文件
//                downloadApk();
//            }
//        });
//        mDownloadDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                UtilToast.t("取消下载");
//                cancelUpdate = true;
//            }
//        });
//
//        mDownloadDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//
//    }

    private downloadApkThread mDownloadApkThread;

    /**
     * 下载apk文件
     */
    private void downloadApk() {
        // 启动新线程下载软件

        //启动下载线程
        if (mDownloadApkThread == null) {
            mDownloadApkThread = new downloadApkThread();
            mDownloadApkThread.start();
        } else {
            UtilToast.showText("取消下载");
            cancelUpdate = true;
        }
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";

                    mHashMap = new HashMap<>();


                    mHashMap.put("url", bean.getDownload_apk_url());
                    mHashMap.put("name", mActivity.getResources().getString(R.string.app_name) + "升级包.apk");
                    mHashMap.put("version", "");


                    URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("name"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0) {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ;

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(mSavePath, mHashMap.get("name"));
        if (!apkfile.exists()) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);// 通过Intent安装APK文件
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mActivity.startActivity(i);
    }
}

