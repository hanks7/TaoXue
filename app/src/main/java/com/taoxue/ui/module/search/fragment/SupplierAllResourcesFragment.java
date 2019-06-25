package com.taoxue.ui.module.search.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoxue.R;
import com.taoxue.base.BaseFragment;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.GysResourceBean;
import com.taoxue.ui.model.ResourceBean;
import com.taoxue.ui.module.search.adapter.SupplierAllResourcesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;



/**
 * 供应闪全部资源
 */
public class SupplierAllResourcesFragment extends BaseFragment {
    @BindView(R.id.myrv)
    RecyclerView rlv;
    private int page = 1;
    private int pageSize = 15;
    private GridLayoutManager mLayoutManager;

    Unbinder unbinder;

    List<ResourceBean> list;
    String ids;
    private SupplierAllResourcesAdapter adapter;

    @Override
    protected int getLayout() {
        Bundle bundle = getArguments();
        ids = bundle.getString("ids");
        list = new ArrayList<>();
        return R.layout.fragment_serach_home;
    }

    @Override
    protected void onInit() {
        initRecyclerView();//初始化recyclerView
        http();//获得收藏列表(刷新)

    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        list = new ArrayList<>();
        adapter = new SupplierAllResourcesAdapter(getActivity(), null, true);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        rlv.setLayoutManager(mLayoutManager);
        rlv.setAdapter(adapter);
        rlv.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        HttpAdapter.getService().getGysResource(pageSize,page, ids).enqueue(new OnResponseNoDialogListener<GysResourceBean>() {
            @Override
            protected void onSuccess(GysResourceBean GysResourceBean) {
                List<ResourceBean> vlist = GysResourceBean.getPage().getResult();
                if (page > 1) {
                    adapter.addAllData(vlist, vlist.size() < pageSize ? false : true);
                } else {
                    adapter.updateList(vlist, vlist.size() < pageSize ? false : true);
                }
            }

            @Override
            protected void onFailure() {
                if (page > 1) page--;
            }
        });


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
    }

}
