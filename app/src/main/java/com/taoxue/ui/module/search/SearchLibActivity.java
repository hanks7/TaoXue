package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter.CityListAdapter;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter.ResultListAdapter;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.db.DBManager;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.City;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.utils.StringUtils;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.view.SideLetterBar;
import com.taoxue.ui.module.search.bean.SearchSupplierBean;
import com.taoxue.ui.view.PopWinMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taoxue.R.id.tv_located_city;
import static com.taoxue.utils.StatusbarUtils.StatusBarLightMode;

/**
 * 图书馆搜索
 */
public class SearchLibActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.listview_search_result)
    ListView listviewSearchResult;
    @BindView(R.id.tv_query_all_result)
    TextView tvQueryAllResult;
    @BindView(tv_located_city)
    TextView tvLocatedCity;
    @BindView(R.id.layout_locate)
    LinearLayout layoutLocate;
    @BindView(R.id.listview_all_city)
    ListView listviewAllCity;
    @BindView(R.id.tv_letter_overlay)
    TextView tvLetterOverlay;
    @BindView(R.id.side_letter_bar)
    SideLetterBar sideLetterBar;
    @BindView(R.id.empty_view)
    LinearLayout emptyView;

    @BindView(R.id.fa_home_edt_search_selector)
    TextView mTvSearchSelector;
    @BindView(R.id.iv_search_clear)
    ImageView clearBtn;

    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private DBManager dbManager;

    private AMapLocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarLightMode(this);//设置状态栏颜色为黑色
        setContentView(R.layout.activity_city_picker);
        ButterKnife.bind(this);
        final PopWinMenu popWinMenu=  new PopWinMenu(this);//初始化popwinMenu mTvSearchSelector
        mTvSearchSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWinMenu.showPopupWindow(mTvSearchSelector);
            }
        });

        dbManager = new DBManager(this, "SearchLib.db");
        initView();
        mCityAdapter = new CityListAdapter(this, dbManager.getAllCities());
        listviewAllCity.setAdapter(mCityAdapter);
        mResultAdapter = new ResultListAdapter(this, null);
        initLocation();
        netWork("");
        initData();
    }


    /**
     * 网络请求
     */
    private void netWork(final String cityName) {
        HttpAdapter.getService().searchList(cityName)
                .enqueue(new OnResponseListener<SearchSupplierBean>(this) {
                    @Override
                    protected void onSuccess(SearchSupplierBean bean) {
                        if (cityName.equals(""))
                            insertSQL((ArrayList) bean.getItem());
                        mCityAdapter.sjdfkljsadfkljaskdfl((ArrayList) bean.getItem());
                        initResult();
                    }
                });
    }

    private boolean isNotFirst;

    /**
     * 初始化定位
     */
    private void initLocation() {

        tvLocatedCity.setText(mActivity.getString(R.string.cp_locating));
        mLocationClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        mLocationClient.setLocationOption(option);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        String location = StringUtils.extractLocation(city, district);
                        tvLocatedCity.setText(location);
                        if (isNotFirst) {
                            netWork(city);
                        }
                        isNotFirst = true;
                    } else {
                        //定位失败
                        tvLocatedCity.setText(R.string.cp_located_failed);
                    }
                }
            }
        });
        mLocationClient.startLocation();
    }

    private void initData() {

        sideLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                listviewAllCity.setSelection(position);
            }
        });
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(City city) {
                next(city);
            }
        });
    }

    /**
     * 初始化搜索结果.
     */
    private void initResult() {
        etSearch.addTextChangedListener(new TextWatcher() {
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
                    listviewSearchResult.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    listviewSearchResult.setVisibility(View.VISIBLE);
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

        listviewSearchResult.setAdapter(mResultAdapter);
        listviewSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                next(mResultAdapter.getItem(position));
            }
        });
    }

    /**
     * 向数据库插入数据
     *
     * @param mAllCities
     */
    @NonNull
    private void insertSQL(ArrayList<City> mAllCities) {
        dbManager.insertAndUpdateData(mAllCities);
    }

    private void initView() {
        etSearch = (EditText) findViewById(R.id.et_search);
        mTvSearchSelector.setText("图书馆");
        etSearch.setHint("请输入图书馆名称");
        sideLetterBar.setOverlay(tvLetterOverlay);

    }


    private void next(City city) {
//        Bundle bundle = new Bundle();
//        bundle.putString("ids", city.getIds());
//        UtilIntent.intentDIYLeftToRight(mActivity, WebLibHomeActivity.class, bundle);
        launch(WebLibHomeActivity.class,city.getIds());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
    }

    @OnClick({
            R.id.back,
            R.id.layout_locate,
            R.id.tv_query_all_result,
            R.id.iv_search_clear
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.iv_search_clear:
                etSearch.setText("");
                clearBtn.setVisibility(View.GONE);
                emptyView.setVisibility(View.GONE);
                listviewSearchResult.setVisibility(View.GONE);
                break;
            case R.id.tv_query_all_result:
                mCityAdapter.sjdfkljsadfkljaskdfl(dbManager.getAllCities());
                break;
            case R.id.layout_locate:
                tvLocatedCity.setText(mActivity.getString(R.string.cp_locating));
                mLocationClient.startLocation();
                break;
        }
    }
}
