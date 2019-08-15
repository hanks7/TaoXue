package com.taoxue.ui.module.yuejia;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.BaseApplication;
import com.taoxue.base.BaseFragment;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.UtilDate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
  *@desc 阅+
  *@time 2018/2/3 13:27
  */

public class SpecialFragment extends BaseFragment {
    @BindView(R.id.toolbar_title)
    TopBar toolbarTitle;
    @BindView(R.id.reader_name)
    TextView readerName;
    @BindView(R.id.read_day)
    TextView readDay;
    @BindView(R.id.read_tab)
    TabLayout readTab;
    @BindView(R.id.read_tab_vp)
    ViewPager readTabVp;
    Unbinder unbinder;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;
    ContentPagerAdapter contentAdapter;
    @Override
    protected int getLayout() {
        return R.layout.fragment_special;
    }

    @Override
    protected void onInit() {
        initContent();
        readTab.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.gray), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        readTab.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        readTab.setupWithViewPager(readTabVp,true);
    }

    private void initContent(){
        tabIndicators = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
            tabIndicators.add("阅读记录");
            tabIndicators.add("已购");
            tabIndicators.add("收藏");
            tabIndicators.add("关注");
//        }
        tabFragments = new ArrayList<>();
         tabFragments.add(new ReaderJiluFragment());
        tabFragments.add(new YiGouFragment());
        tabFragments.add(new CollectionFragment());
        tabFragments.add(new GuanZhuFrament());
        contentAdapter = new ContentPagerAdapter(getActivity().getSupportFragmentManager());
        readTabVp.setAdapter(contentAdapter);

        updateView();
    }

   public void updateView(){
       String name;
       if (BaseApplication.get().getUserModel()!=null&&!BaseApplication.get().getUserModel().equals("")){
           name= BaseApplication.get().getUserModel().getName()==null?"游客":BaseApplication.get().getUserModel().getName();
       }else{
          name="游客";
       }
       readerName.setText(name+"读者");
       String date= UtilDate.getCurrentMonthAndDay();
       String cd=UtilDate.getCurrentQufen();
       readDay.setText(cd+"好"+",今天是"+date);
   }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            updateView();
        }
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
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

