package com.taoxue.ui.module.search;

import android.os.Bundle;

import com.taoxue.MainActivity;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.utils.UtilIntent;

import butterknife.ButterKnife;

public class SRResourceLibActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_new);
        ButterKnife.bind(this);
    }

    @Override
    public void dealOnBackPressed() {
        UtilIntent.intentDIYLeftToRight(this,
                MainActivity.class,
                android.R.anim.fade_in, android.R.anim.fade_out);
    }

}

