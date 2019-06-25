package com.taoxue.ui.module.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseFragment;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.module.home.BuyingResourceActivity;
import com.taoxue.ui.module.home.MyCollectActivity;
import com.taoxue.ui.module.home.PlayRecordActivity;
import com.taoxue.ui.module.search.WebHomeActivity;
import com.taoxue.ui.module.setting.SettingActivity;
import com.taoxue.ui.view.CircleImageView;
import com.taoxue.ui.view.HeaderWaveHelper;
import com.taoxue.ui.view.HeaderWaveView;
import com.taoxue.ui.view.ShareFragment;
import com.taoxue.utils.UtilTools;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by CC on 2016/11/16.
 *
 * @author yysleep
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.ac_my_iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.ac_my_tv_name)
    TextView mTvName;
    @BindView(R.id.header_wave_view)
    HeaderWaveView mHeaderWaveView;
    @BindView(R.id.ac_my_rl_my_infor)
    View view;

    Unbinder unbinder;
    private UserModel bean;
    private HeaderWaveHelper mHeaderWaveHelper;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_information;
    }


    @Override
    protected void onInit() {
        mHeaderWaveHelper = new HeaderWaveHelper(mHeaderWaveView,null);
    }

    private void setData() {

        if (TaoXueApplication.get().isLogin()) {
            bean = TaoXueApplication.get().getUserModel();
            mTvName.setText(bean.getName() + "");
            UtilGlide.loadImgForIvHead(getActivity(), bean.getPhoto(), mIvHead);
        } else {
            mTvName.setText(UtilTools.getResourcesString(R.string.please_login_first));
            mIvHead.setImageResource(R.mipmap.icon_test_head);
        }
        mHeaderWaveHelper.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onStop() {
        super.onStop();
        mHeaderWaveHelper.cancel();
    }

    @OnClick({
            R.id.ac_my_rl_my_infor,
            R.id.ac_my_rl_setting,
            R.id.ac_my_rl_share,
            R.id.ac_my_rl_history_record,
            R.id.ac_my_rl_my_collection,
            R.id.ac_my_rl_my_buying_resource,
            R.id.ac_my_rl_help,
            R.id.ac_my_rl_my_lib
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_my_rl_my_infor:
                if (!UtilTools.judgeIsLogin(getActivity())) break;
                launch(MyInformationActivity.class);
                break;
            case R.id.ac_my_rl_history_record:
                if (!UtilTools.judgeIsLogin(getActivity())) break;
                launch(PlayRecordActivity.class);
                break;
            case R.id.ac_my_rl_my_collection://我的收藏
                if (!UtilTools.judgeIsLogin(getActivity())) break;
                launch(MyCollectActivity.class);
                break;
            case R.id.ac_my_rl_my_lib://我的读者证
                if (!UtilTools.judgeIsLogin(getActivity())) break;
                launch(MyLibraryActivity.class);
                break;
            case R.id.ac_my_rl_setting:
                launch(SettingActivity.class);
                break;
            case R.id.ac_my_rl_help:
                launch(  WebHomeActivity.class,9);
                break;
            case R.id.ac_my_rl_share:
                ShareFragment.show(getActivity());
                break;
            case R.id.ac_my_rl_my_buying_resource://已购买的资源
                if (!UtilTools.judgeIsLogin(getActivity())) break;
                launch(BuyingResourceActivity.class);
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mHeaderWaveHelper.cancel();
    }
}
