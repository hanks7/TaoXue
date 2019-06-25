package com.taoxue.ui.module.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageLoader;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.login.LoginActivity;
import com.taoxue.ui.view.roundedimageview.RoundedImageView;
import com.taoxue.utils.UtilDate;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilTools;
import com.taoxue.utils.Utildialog;
import com.taoxue.utils.glide.UtilGlide;
import com.taoxue.utils.permission.PermissionReq;
import com.taoxue.utils.permission.PermissionResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyInformationActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_occupation)
    TextView tvOccupation;
    private String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information2);
        CommitContent.setColorLeftSilde(this);//设置向右滑动退出
        ButterKnife.bind(this);
        netWork();
        intTimePickerView();//选择时间
        setViewData(TaoXueApplication.get().getUserModel());
    }

    /**
     * 初始化数据
     *
     * @param bean
     */
    private void setViewData(UserModel bean) {
        tvNickname.setText(bean.getName() + "");
        tvSex.setText(bean.getSex());
        tvBirthday.setText(bean.getBirth_year());
        tvEducation.setText(bean.getEducation() + "");
        tvIndustry.setText(bean.getHangye());//行业
        tvOccupation.setText(bean.getJob());//职业

        UtilGlide.loadImgForIvHead(this, UtilTools.getStringEND(bean.getPhoto()), ivHead);
    }


    @OnClick({
            R.id.ll_nickname,
            R.id.ll_sex,
            R.id.iv_head,
            R.id.ll_birthday,
            R.id.ll_education,
            R.id.ll_industry,
            R.id.ll_occupation

    })
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ll_nickname:
                next(bundle, 101, "昵称", tvNickname.getText().toString().trim());
                break;
            case R.id.ll_sex:
                intSexPickView(0);//选择性别
                break;
            case R.id.iv_head:
                getPermission();//获取权限

                break;
            case R.id.ll_birthday:
                pvTime.show();
                break;
            case R.id.ll_education:
                intSexPickView(1);//选择学历
                break;
            case R.id.ll_industry:
                next(bundle, 102, "行业", tvIndustry.getText().toString().trim());
                break;
            case R.id.ll_occupation:
                next(bundle, 103, "职业", tvOccupation.getText().toString().trim());
                break;


        }
    }

    /**
     * 获取权限
     */
    public void getPermission() {
        PermissionReq.with(this)
                .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .result(new PermissionResult() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied() {

                    }

                    @Override
                    public void onNext() {
                        PermissionReq.with(mActivity)
                                .permissions(
                                        Manifest.permission.CAMERA
                                )
                                .result(new PermissionResult() {
                                    @Override
                                    public void onGranted() {
                                    }

                                    @Override
                                    public void onDenied() {

                                    }

                                    @Override
                                    public void onNext() {
                                        toPhoto();
                                    }

                                })
                                .request();

                    }

                })
                .request();
    }

    private void next(Bundle bundle, int code, String flag, String content) {
        bundle.putInt("code", code);
        bundle.putString("flag", flag);
        bundle.putString("content", content);
        UtilIntent.intentResultDIY(this, NickNameActivity.class, bundle, code);
    }

    /**
     * 得到个人信息
     */
    private void netWork() {
        HttpAdapter.getService().info()
                .enqueue(new OnResponseListener<BaseResultModel<UserModel>>(this) {
                    @Override
                    protected void onSuccess(BaseResultModel<UserModel> resultModel) {
                        UserModel bean = resultModel.getData();

                        UserModel userModel = TaoXueApplication.get().getUserModel();
                        userModel.setBirth_year(bean.getBirth_year());
                        userModel.setName(bean.getName());
                        userModel.setEducation(bean.getEducation());
                        userModel.setJob(bean.getJob());
                        userModel.setPhoto(bean.getPhoto());
                        TaoXueApplication.get().setUserModel(userModel);
                        setViewData(bean);
                    }
                });
    }

    /**
     * 更改个人信息
     */
    private void netWorkInforChange() {

        HttpAdapter.getService().updUserInfo(
                TaoXueApplication.get().getUserModel().getUser_id(),
                tvSex.getText().toString().trim() + "",
                tvBirthday.getText().toString().trim() + "",
                tvIndustry.getText().toString().trim() + "",
                tvOccupation.getText().toString().trim() + "",
                tvEducation.getText().toString().trim() + ""

        )
                .enqueue(new OnResponseListener<BaseResultModel<BaseModel>>(this) {
                    @Override
                    protected void onSuccess(BaseResultModel<BaseModel> UserModelBaseResultModel) {
                    }
                });
    }

    /**
     * 上传照片
     *
     * @param strPath
     * @param runnable
     */
    private void netWorkInforChange(String strPath, final Runnable runnable) {

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File(strPath));

        HttpAdapter.getService().uploadAndSavePhoto(
                TaoXueApplication.get().getUserModel().getUser_id(),
                fileBody
        ).enqueue(new OnResponseListener<BaseResultModel>(this) {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {
                runnable.run();

            }
        });
    }


    //********************************选择照片*************************************************
    private void toPhoto() {

        if (!PermissionReq.judgePermisson(this, Manifest.permission.CAMERA)) return;
        if (!PermissionReq.judgePermisson(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) return;

        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                .crop(1, 1, 500, 500)
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();


        ImageSelector.open(this, imageConfig);   // 开启图片选择器
    }

    /**
     * 得到照片返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            for (final String path : pathList) {
//                UtilToast.showText(path);

                netWorkInforChange(path, new Runnable() {
                    @Override
                    public void run() {
                        UtilGlide.loadImgForIvHead(mActivity, path, ivHead);
                        netWork();
                    }
                });
            }

        }

        if (requestCode == 101 || requestCode == 102 || requestCode == 103 && null != data) {
            netWork();
        }
    }

    class GlideLoader implements ImageLoader {

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .placeholder(com.jaiky.imagespickers.R.mipmap.imageselector_photo)
                    .centerCrop()
                    .into(imageView);
        }

    }


    OptionsPickerView pvOptions;

    public void intSexPickView(final int i) {

        final ArrayList<String> options1Items = new ArrayList<>();
        if (i == 0) {
            options1Items.add("男");
            options1Items.add("女");
        }

//        case R.id.ll_industry:
//        intSexPickView(2);//选择行业
//        break;
//        case R.id.ll_occupation:
//        intSexPickView(3);//选择职业
        if (i == 1) {
            options1Items.add("研究生");
            options1Items.add("本科");
            options1Items.add("大专");
            options1Items.add("中专");
            options1Items.add("高中");
            options1Items.add("初中");
            options1Items.add("小学");
        }
        if (i == 2) {
            options1Items.add("个体户");
            options1Items.add("中小型企业");
            options1Items.add("民企");
            options1Items.add("国企");
            options1Items.add("央企");
            options1Items.add("事业单位");
            options1Items.add("公务员");
            options1Items.add("社会闲散人员");
            options1Items.add("学生");
            options1Items.add("群众");
        }
        if (i == 3) {
            options1Items.add("测试1");
            options1Items.add("测试2");
            options1Items.add("测试3");
            options1Items.add("测试4");
            options1Items.add("测试5");
        }
        pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(options1Items);
        pvOptions.setCyclic(false, true, true);
        pvOptions.setSelectOptions(0);
        pvOptions.setCancelable(true);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                switch (i) {
                    case 0:
                        tvSex.setText(options1Items.get(options1));
                        break;
                    case 1:
                        tvEducation.setText(options1Items.get(options1));
                        break;
                    case 2:
                        tvIndustry.setText(options1Items.get(options1));
                        break;
                    case 3:
                        tvOccupation.setText(options1Items.get(options1));
                        break;
                }

                netWorkInforChange();

            }
        });
        pvOptions.show();

    }

    TimePickerView pvTime;

    private void intTimePickerView() {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 67, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvBirthday.setText(UtilDate.getSimpleDateFormatTime(date));
                netWorkInforChange();
            }
        });
    }
}
