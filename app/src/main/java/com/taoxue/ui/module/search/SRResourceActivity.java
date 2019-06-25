package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.MainActivity;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.SearchDigitalResourcesBean;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.ui.view.ListRefeshMoreView;
import com.taoxue.utils.UtilIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 资源搜索结果
 */
public class SRResourceActivity extends BaseActivity {

    @BindView(R.id.pop_cilck_ll)
    LinearLayout popCilckLl;
    @BindView(R.id.pop_click_tv)
    TextView pop_click_tv;
    @BindView(R.id.pop_click_iv_jiantou)
    ImageView mIvjiantou;

    @BindView(R.id.ListRefeshMoreView)
    ListRefeshMoreView mListRefeshMoreView;

    private BaseMyAdapter adapter;
    private List<SearchDigitalResourcesBean> list;

    private String keyWord;//搜索关键词
    private String typeFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_new);
        ButterKnife.bind(this);

        getIntentData();//得到上一页放回的数据
        initView();//初始化排序列表
        init();

    }

    @Override
    public void dealOnBackPressed() {
        UtilIntent.intentDIYLeftToRight(this,
                MainActivity.class,
                android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 得到上一页放回的数据
     *
     * @return
     */
    private void getIntentData() {
        keyWord = ((String) getIntentKey1());
    }

    /**
     * 初始化排序列表
     */
    private void initView() {
        final ArrayList<String> list = new ArrayList();
        list.add("综合");
        list.add("热门");
        list.add("评分");
        typeFlag = list.get(0);
        new PopWinSearchResultMenu(this, mIvjiantou, popCilckLl, list, new PopWinSearchResultMenu.ItemClickListener() {
            @Override
            public void itemClickListener(int position) {
                pop_click_tv.setText(list.get(position));
                typeFlag = list.get(position);
                mListRefeshMoreView.setAutoRefresh();
            }
        });
    }


    private void init() {
        list = new ArrayList<>();
        adapter = new SRResourceAdapter(this, list);
        mListRefeshMoreView.setErrorPic(R.mipmap.zan_no_data,getString(R.string.no_search_results));
        mListRefeshMoreView.initListView(adapter, new ListRefeshMoreView.NetworkInterface() {
            @Override
            public void http(int page) {
                netWork(page);
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launch(ResourceDetailActivity.class, list.get(position).getId() + "");
            }

        });
    }


    /**
     * 获得收藏列表(刷新)
     */
    private void netWork(int page) {
        HttpAdapter.getService().searchDigitalResources(page, typeFlag, keyWord).enqueue(new OnResponseNoDialogListener<BasePageModel<SearchDigitalResourcesBean>>() {
            @Override
            protected void onSuccess(BasePageModel<SearchDigitalResourcesBean> pageModel) {
                mListRefeshMoreView.onSuccess(pageModel);
            }

            @Override
            protected void onFailure() {
                mListRefeshMoreView.onFailure();
            }
        });
    }


}

