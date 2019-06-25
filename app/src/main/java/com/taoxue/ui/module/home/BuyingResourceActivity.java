package com.taoxue.ui.module.home;

import com.taoxue.base.BaseListActivity;
import com.taoxue.ui.module.fragment.BuyingResourceFragment;
import com.taoxue.ui.module.fragment.MyCollectionFragment;

public class BuyingResourceActivity extends BaseListActivity<MyCollectionFragment> {
    @Override
    protected Class getFragmentClass() {
        return BuyingResourceFragment.class;
    }
}
