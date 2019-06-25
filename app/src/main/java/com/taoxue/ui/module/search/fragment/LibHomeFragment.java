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
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.CgsHomeInfoBean;
import com.taoxue.ui.model.ResourceBean;
import com.taoxue.ui.module.search.adapter.LibHomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *采购商首页
 */
public class LibHomeFragment extends BaseFragment {


    @BindView(R.id.myrv)
    RecyclerView rlsv;

    Unbinder unbinder;

    List<ResourceBean> list;
    String ids;
    private LibHomeAdapter myAdapter;

    @Override
    protected int getLayout() {
        Bundle bundle = getArguments();
        ids = bundle.getString("ids");
        list = new ArrayList<>();
        return R.layout.fragment_serach_home;
    }

    @Override
    protected void onInit() {
        initRecyclerView();
        netWork();
    }

    /**
     * 网络请求
     */
    private void netWork() {
        HttpAdapter.getService().getCgsHomeInfo(ids)
                .enqueue(new OnResponseListener<BaseResultModel<CgsHomeInfoBean>>(getActivity()) {
                    @Override
                    protected void onSuccess(BaseResultModel<CgsHomeInfoBean> bean) {

                        ResourceBean resourceBean1 = new ResourceBean();
                        resourceBean1.setFlag(1);
                        list.add(resourceBean1);
                        list.addAll(bean.getData().getCommend_resource());

                        ResourceBean resourceBean2 = new ResourceBean();
                        resourceBean2.setFlag(2);
                        list.add(resourceBean2);
                        list.addAll(bean.getData().getHot_resource());

                        ResourceBean resourceBean3 = new ResourceBean();
                        resourceBean3.setFlag(3);
                        list.add(resourceBean3);
                        list.addAll(bean.getData().getLike_resource());

                        myAdapter.addList(list);
                    }
                });
    }


    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        rlsv.setLayoutManager(manager);
        myAdapter = new LibHomeAdapter(getActivity(), null);
        rlsv.setAdapter(myAdapter);
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
