package com.taoxue.ui.module.search;

import android.os.Bundle;

import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.module.search.bean.ResoureHotSearchKeyBean;
import com.taoxue.utils.UtilIntent;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源库搜索
 */
public class SearchZiYuanKuActivity extends SearchActivity {


    @Override
    public void setSelectorTasks() {
        mTvSearchSelector.setText("资源库");
        et_search.setHint("请输入资源库名称");
        netWork();
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
        UtilIntent.intentDIYLeftToRight(this, SRSupplierActivity.class, bundle);
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
        HttpAdapter.getService().keywordDrdata()
                .enqueue(new OnResponseNoDialogListener<ResoureHotSearchKeyBean>() {
                    @Override
                    protected void onSuccess(ResoureHotSearchKeyBean bean) {
                        initTagView(getHttpData( bean.getItem()));
                    }
                });
    }
}

