package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter.CityListThreeAdapter;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter.ResultListAdapter;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.db.DBManager;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.City;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.view.SideLetterBar;
import com.taoxue.ui.module.search.bean.SearchSupplierBean;
import com.taoxue.ui.view.PopWinMenu;
import com.taoxue.utils.UtilIntent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.taoxue.utils.UtilStatusbar.StatusBarLightMode;

/**
 * 搜索供应商
 */
public class SearchSupplierActivity extends BaseActivity implements View.OnClickListener {
    private ListView mListView;
    private ListView mResultListView;//搜索结果的adapter
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ImageView backBtn;
    private ViewGroup emptyView;
    @BindView(R.id.fa_home_edt_search_selector)
    TextView mTvSearchSelector;

    private CityListThreeAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarLightMode(this);//设置状态栏颜色为黑色
        setContentView(R.layout.activity_city_picker);
        dbManager = new DBManager(this,"SearchSupplierActivityDB");
        ButterKnife.bind(this);
        final PopWinMenu popWinMenu=  new PopWinMenu(this);//初始化popwinMenu mTvSearchSelector
        mTvSearchSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWinMenu.showPopupWindow(mTvSearchSelector);
            }
        });


        initView();
        netWork();
    }

    /**
     * 网络请求
     */
    private void netWork() {
        HttpAdapter.getService().gysList("")
                .enqueue(new OnResponseListener<SearchSupplierBean>(this) {
                    @Override
                    protected void onSuccess(SearchSupplierBean bean) {
                        initData((ArrayList) bean.getItem());
                    }
                });
    }


    private void initData(ArrayList<City> hotCities) {
        dbManager.insertAndUpdateData(hotCities);
        mCityAdapter = new CityListThreeAdapter(this, dbManager.getAllCities(), hotCities, false);
//        mAllCities = dbManager.getAllCities();
        mCityAdapter.setOnCityClickListener(new CityListThreeAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(City name) {
                next(name);
            }


        });
        mListView.setAdapter(mCityAdapter);

        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });
        mResultAdapter = new ResultListAdapter(this, null);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                next(mResultAdapter.getItem(position));
            }
        });
    }


    private void initView() {
        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mListView = (ListView) findViewById(R.id.listview_all_city);
        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        backBtn = (ImageView) findViewById(R.id.back);
        searchBox = (EditText) findViewById(R.id.et_search);
        findViewById(R.id.cp_view_locate_city).setVisibility(View.GONE);
        mLetterBar.setOverlay(overlay);

        mTvSearchSelector.setText("供应商");
        searchBox.setHint("请输入供应商名称");

        clearBtn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
    }

    /**
     * 跳转到下一页
     * @param city
     */
    private void next(City city) {
        Bundle bundle= new Bundle();
        bundle.putString("ids",city.getIds());
        UtilIntent.intentDIYLeftToRight(this, HomeSupplierActivity.class,bundle);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        } else if (i == R.id.back) {
            finish();

        }
    }


}
