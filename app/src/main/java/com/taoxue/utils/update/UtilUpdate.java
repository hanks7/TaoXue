package com.taoxue.utils.update;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.taoxue.app.Constants;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.utils.UtilSystem;

import java.io.File;

public class UtilUpdate {

    /**
     * 功能描述: <br>
     * 〈功能详细描述〉 sd卡中创建一个目标文件
     *
     * @param name
     * @return Author: 14052012 zyn Date: 2014年11月7日 下午3:10:35
     */
    public static String createSDCardDir(String name) {
        File sdcardDir = Environment.getExternalStorageDirectory();
        String path = sdcardDir.getPath() + "/MUDOWN";
        File file = null;
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment
                    .getExternalStorageState())) {

                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                file = new File(dir + File.separator + name);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return file.getPath();
    }

    public static void installApk(String urlPath, Context context) {
        Intent apkIntent = new Intent();
        apkIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        apkIntent.setAction(Intent.ACTION_VIEW);
        File apkFile = new File(urlPath);
        Log.i("jone", "apk length " + apkFile.length() + "");
        Uri uri = Uri.fromFile(apkFile);
        apkIntent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(apkIntent);
    }

    /**
     * 判断是否更新,是提示更新,不是就提示已经是最新版本
     *
     * @param context
     */
    public static void judgeUpdate(final BaseActivity context, final Runnable runnable) {
        HttpAdapter.getService().getApkVersionUpdate(UtilSystem.getVersionCode())
                .enqueue(new OnResponseListener<BaseResultModel<IsUpdateBean>>(context) {
                    @Override
                    protected void onSuccess(BaseResultModel<IsUpdateBean> resultBean) {
                        IsUpdateBean bean = resultBean.getData();
                        Constants.apkURL = bean.getDownload_apk_url();

                        UpdateManager manager = new UpdateManager(context, bean);
                        if (!manager.checkUpdate(bean.getInfo() == null)) {//有升级
                            if (runnable != null) runnable.run(bean.getInfo());
                        }
                    }
                });
    }

    public interface Runnable {
        void run(String str);
    }


}
