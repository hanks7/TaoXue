package com.taoxue.ui.module.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.QryMyCollectionBean;
import com.taoxue.ui.module.search.BaseMyAdapter;
import com.taoxue.ui.module.search.MyCollectAdapter;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.ui.view.ListRefeshMoreView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyCollectActivity extends BaseActivity {

    @BindView(R.id.ListRefeshMoreView)
    ListRefeshMoreView mListRefeshMoreView;

    private ArrayList<QryMyCollectionBean> list;
    private BaseMyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.bind(this);


        init();
    }




    private void init() {
        list = new ArrayList<>();
        adapter = new MyCollectAdapter(this, list);
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


    /**
     * 获得收藏列表(刷新)
     */
    private void netWork(int page) {
        HttpAdapter.getService().collection3(page).enqueue(new OnResponseNoDialogListener<BasePageModel<QryMyCollectionBean>>() {
            @Override
            protected void onSuccess(BasePageModel<QryMyCollectionBean> pageModel) {
                mListRefeshMoreView.onSuccess(pageModel);
            }

            @Override
            protected void onFailure() {
                mListRefeshMoreView.onFailure();
            }

        });


    }


}
