package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.search.adapter.SupplierKUAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TestSearchActivity extends AppCompatActivity {

    @BindView(R.id.myrv)
    RecyclerView rlsv;

    Unbinder unbinder;

    List<ApiOneBean.BdqdBean> list;
    private SupplierKUAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_serach_home);
        ButterKnife.bind(this);

    }



}