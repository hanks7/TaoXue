package com.taoxue.ui.module.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.GetDetailBean;
import com.taoxue.ui.module.search.BaseMyAdapter;
import com.taoxue.ui.module.search.QryResourceByTagAdapter;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.ui.view.ListRefeshMoreView;
import com.taoxue.ui.view.TopBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 从分类进入的列表
 */
public class NormalListViewActivity extends BaseActivity  {


    @BindView(R.id.topbar)
    TopBar topbar;


    private String keyWord;//搜索关键词
    private String activityName;//页面名称

    @BindView(R.id.ListRefeshMoreView)
    ListRefeshMoreView mListRefeshMoreView;

    private ArrayList<GetDetailBean> list;
    private BaseMyAdapter adapter;

    /**
     * 得到上一页放回的数据
     *
     * @return
     */
    private void getIntentData() {
        keyWord = (String) getIntentKey1();
        activityName = (String) getIntentKey2();
        topbar.setTitle(keyWord);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);
        getIntentData();
        init();
    }




    /**
     * 获得收藏列表(刷新)
     */
    private void netWork(int page) {
        HttpAdapter.getService().qryResourceByTag(page, keyWord, activityName).enqueue(
                new OnResponseNoDialogListener<BasePageModel<GetDetailBean>>() {

            @Override
            protected void onSuccess(BasePageModel<GetDetailBean> pageModel) {
                mListRefeshMoreView.onSuccess(pageModel);
            }

            @Override
            protected void onFailure() {
                mListRefeshMoreView.onFailure();
            }
        });


    }

    private void init() {
        list = new ArrayList<>();
        adapter = new QryResourceByTagAdapter(this, list);

        mListRefeshMoreView.initListView(adapter, new ListRefeshMoreView.NetworkInterface() {
            @Override
            public void http(int page) {
                netWork(page);
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launch(ResourceDetailActivity.class, list.get(position).getResource_id() + "");
            }

        });
    }



}


