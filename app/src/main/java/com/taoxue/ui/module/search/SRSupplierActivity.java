package com.taoxue.ui.module.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.DrdataBean;
import com.taoxue.ui.model.DrdataBean.PageBean.ResultBean;
import com.taoxue.ui.view.RecyclerViewDivider;
import com.taoxue.utils.UtilToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 资源库搜索结果
 */
public class SRSupplierActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @BindView(R.id.ll_empty_hint)
    View emptyHint;

    @BindView(R.id.pop_cilck_ll)
    LinearLayout popCilckLl;
    @BindView(R.id.pop_click_tv)
    TextView pop_click_tv;

    @BindView(R.id.pop_click_iv_jiantou)
    ImageView mIvjiantou;

    private List<ResultBean> list;

    private GridLayoutManager mLayoutManager;
    private SRSupplierAdapter adapter;
    private int page = 1;
    private String keyWord;//搜索关键词

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_supplier);
        ButterKnife.bind(this);
        getIntentData();//得到上一页放回的数据
        initRefreshLayout();//初始化下拉刷新控件
        initRecyclerView();//初始化recyclerView
        http();//获得收藏列表(刷新)
        initView();//初始化头部列表
    }

    /**
     * 初始化头部列表
     */
    private void initView() {
        final ArrayList<String> list = new ArrayList();
        list.add("综合排序");
        list.add("热门");
        list.add("评分");
        new PopWinSearchResultMenu(this, mIvjiantou,popCilckLl, list, new PopWinSearchResultMenu.ItemClickListener() {
            @Override
            public void itemClickListener(int position) {
                UtilToast.showText("" + list.get(position));
                pop_click_tv.setText("" + list.get(position));
                Bundle bundle=new Bundle();
                bundle.putString("arg",list.get(position));
            }
        });
    }


    /**
     * 得到上一页放回的数据
     *
     * @return
     */
    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        keyWord = bundle.getString("keyWord");
    }

    /**
     * 初始化下拉刷新控件
     */
    private void initRefreshLayout() {

        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.disableWhenHorizontalMove(true);
        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, recyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                http();

            }
        });
    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        list = new ArrayList<>();
        adapter = new SRSupplierAdapter(this, list, true);
        recyclerView.setAdapter(adapter);
        mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //这句就是添加我们自定义的分隔线
        recyclerView.addItemDecoration(new RecyclerViewDivider(
                this, LinearLayoutManager.HORIZONTAL, R.drawable.divider_hor));


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter.hasMore) {//监听底部事件
                    page++;
                    http();

                }
            }

        });

    }


    /**
     * 获得收藏列表(刷新)
     */
    private void http() {
        HttpAdapter.getService().drdata(page, keyWord).enqueue(new OnResponseNoDialogListener<DrdataBean>() {
            @Override
            protected void onSuccess(DrdataBean bean) {
                refreshLayout.refreshComplete();
                List<ResultBean> vlist = bean.getPage().getResult();
                if(vlist.size()==0){
                    emptyHint.setVisibility(View.VISIBLE);
                }else{
                    emptyHint.setVisibility(View.GONE);
                }

                if (page > 1) {
                    adapter.addAllData(vlist, vlist.size() < 10 ? false : true);
                } else {
                    adapter.updateList(vlist, vlist.size() < 10 ? false : true);
                }
            }

            @Override
            protected void onFailure() {
                refreshLayout.refreshComplete();
                if (page > 1) page--;
                if (page < 1)  emptyHint.setVisibility(View.VISIBLE);
            }
        });


    }


}
