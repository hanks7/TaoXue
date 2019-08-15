package com.taoxue;

import android.os.Bundle;

import com.taoxue.app.BaseApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.adapter.LsvERBAdapter;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.ReadBean;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestDemoActivity extends BaseActivity {

    @BindView(R.id.test_demo_lsv)
    MyListView lsv;
    private LsvERBAdapter adapter;
    List<ApiOneBean.EveryDayBean> list;

    private int page = 1;
    private int pageSize = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_demo);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        ApiOneBean.EveryDayBean bean = new ApiOneBean.EveryDayBean();
        for (int i = 0; i < 20; i++) {
            list.add(bean);
        }
        list.add(new ApiOneBean.EveryDayBean());
        adapter = new LsvERBAdapter(this, list);//每天读本书
        lsv.setAdapter(adapter);
    }


    /**
     * 加载更多
     */
    private void netMore() {
        HttpAdapter.getService().read(BaseApplication.get().getUserModel().getUser_id(), page, pageSize)
                .enqueue(new OnResponseNoDialogListener<BasePageModel<ReadBean>>() {
                    @Override
                    protected void onSuccess(BasePageModel<ReadBean> basePageModel) {
                        List<ReadBean> vlist = basePageModel.getPage().getResult();
//                        if (page > 1) {
//                            adapter.addList(vlist, vlist.size() < pageSize ? false : true);
//                        } else {
//                            adapter.updata(vlist, vlist.size() < pageSize ? false : true);
//                        }
                    }

                    @Override
                    protected void onFailure() {
                        if (page > 1) page--;
                    }


                });
    }
}
