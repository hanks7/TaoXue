package com.taoxue.ui.module.home;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.module.search.HomeLibActivity;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilToast;
import com.taoxue.utils.UtilTools;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;


/**
 *
 * @Package:        com.taoxue.ui.module.home.ZXingActivity
 * @Author:         侯建军
 * @CreateDate:     2019/11/26 15:16
 * @Description:    要执行baseApplication onCreate中的执行initDisplayOpinion()方法；否则会报错
 */
public class ZXingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
    }

    @Override
    protected void setOnTranslucent() {
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            final String resultfjasdklf = result.substring(result.lastIndexOf("type_value="));
            if(result.contains("type_key=gys")){
                activityToDetail(ResourceDetailActivity.class, resultfjasdklf.replace("type_value=",""), "");

            }else if(result.contains("type_key=cgs")){
                Bundle bundle = new Bundle();
                bundle.putString("ids", resultfjasdklf.replace("type_value=",""));
                UtilIntent.intentDIYLeftToRight(ZXingActivity.this, HomeLibActivity.class, bundle);
            }
        }

        @Override
        public void onAnalyzeFailed() {
            Log.e("zp","找不到资源");
        }
    };

    public void back(View view) {
        onBackPressed();
    }

    boolean isLightOn;
    Camera m_Camera;

    public void flashLight(View view) {
        isLightOn = !isLightOn;
        CodeUtils.isLightEnable(isLightOn);
    }
    public static final int REQUEST_IMAGE = 1032;
    public void choosePhoto(View view) {
        //只跳转到相册
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);

//跳转到相册或者文件系统，
//        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intent, REQUEST_IMAGE);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (m_Camera != null) {
            m_Camera.stopPreview();
            m_Camera.release();
        }
    }

    /**
     * 跳转到详情页
     * @param clazz
     * @param id
     * @param cgs_id
     */
    private void activityToDetail(Class clazz,String id,String cgs_id) {
        Intent in = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(UtilTools.getActivityKey1(clazz), id);
        bundle.putSerializable(UtilTools.getActivityKey2(clazz), cgs_id);
        in.putExtras(bundle);
        in.setClass(this, clazz);
        startActivity(in);
    }

    /**
     * 扫尾二维码回调放回
     *
     * @param requestCode
     * @param resultCode
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {

                String filePath = getPathString(data);//对路径进行的判断(因为我们不知道是三种情况中的那一种)

                CodeUtils.analyzeBitmap(filePath, new CodeUtils.AnalyzeCallback() {
                    @Override
                    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                        UtilToast.showText("解析结果:" + result);
                    }

                    @Override
                    public void onAnalyzeFailed() {
                        UtilToast.showText("解析二维码失败");
                    }
                });


            }
        }
    }

    /**
     *  对路径进行的判断(因为我们不知道是三种情况中的那一种)
     * @param data
     * @return
     */
    private String getPathString(Intent data) {
        String filePath = "$$";
        Uri contentUri = data.getData();
        if (DocumentsContract.isDocumentUri(this, contentUri)) {
            String wholeID = DocumentsContract
                    .getDocumentId(contentUri);
            String id = wholeID.split(":")[1];
            String[] column = {MediaStore.Images.Media.DATA};
            String sel = MediaStore.Images.Media._ID + "=?";
            Cursor cursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);
            int columnIndex = cursor.getColumnIndex(column[0]);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        } else {
            if (!TextUtils.isEmpty(contentUri.getAuthority())) {
                Cursor cursor = getContentResolver().query(contentUri,
                        new String[]{MediaStore.Images.Media.DATA},
                        null, null, null);
                if (null == cursor) {
                    UtilToast.showText("图片没找到");
                }
                cursor.moveToFirst();
                filePath = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            } else {
                filePath = data.getData().getPath();
            }
        }
        return filePath;
    }
}
