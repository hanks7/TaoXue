package com.taoxue.ui.module.search;

import android.os.Bundle;

import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.module.search.bean.ResoureHotSearchKeyBean;
import com.taoxue.utils.UtilIntent;

import java.util.ArrayList;
import java.util.List;

public class SearchResourcesLibActivity extends SearchActivity {
    String cgs_id;
    @Override
    public void setSelectorTasks() {
        mTvSearchSelector.setText("资源");
        et_search.setHint("请输入资源名称");
        getIntentData();
        netWork();
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
         cgs_id = bundle.getString("cgs_id");
    }

    public List<String> getHttpData(List<ResoureHotSearchKeyBean.ItemBean> list) {
        final List<String> tags = new ArrayList<>();
        for (ResoureHotSearchKeyBean.ItemBean bean : list) {
            tags.add(bean.getKeyword());
        }
        return tags;
    }

    /**
     * 网络请求
     */
    private void netWork() {
        HttpAdapter.getService().resource()
                .enqueue(new OnResponseNoDialogListener<ResoureHotSearchKeyBean>() {
                    @Override
                    protected void onSuccess(ResoureHotSearchKeyBean bean) {
                        initTagView(getHttpData( bean.getItem()));
                    }
                });
    }


    /**
     * 点击处理的业务
     * @param name
     */
    @Override
    public void onclickSearch(String name) {
        super.onclickSearch(name);
        Bundle bundle = new Bundle();
        bundle.putString("keyWord", name);
        bundle.putString("cgs_id", cgs_id);
        UtilIntent.intentDIYLeftToRight(this, SRResourceLibActivity.class, bundle);
    }
}
