package com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.City;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.model.LocateState;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.utils.PinyinUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

/**
 * author zaaach on 2016/1/26.
 */
public class CityListThreeAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 2;

    private Context mContext;
    private LayoutInflater inflater;
    private List<City> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;
    private ArrayList<City> hotCities;
    private boolean isHaveLoc;

    public CityListThreeAdapter(Context mContext, ArrayList<City> mCities, ArrayList<City> hotCities, boolean isHaveLoc) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.hotCities = hotCities;
        this.isHaveLoc = isHaveLoc;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null){
            mCities = new ArrayList<>();
        }
        mCities.add(0, new City("热门", "0","",""));
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++){
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getIndex());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getIndex()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     * @param state
     */
    public void updateLocateState(int state, String city){
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter){
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0: mCities.size();
    }

    @Override
    public City getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType){

            case 0:     //热门
                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
                TagContainerLayout tvTagView = (TagContainerLayout) view.findViewById(R.id.gridview_hot_city);
                TextView tvHotCity = (TextView) view.findViewById(R.id.tv_hot_city);
                tvHotCity.setText(mContext.getResources().getString(R.string.cp_hot_city));
                ArrayList<String> tags=new ArrayList<>();
                for (City city:hotCities) {
                    tags.add(city.getName());
                }
                tvTagView.setTags(tags);
                tvTagView.setOnTagClickListener(new TagView.OnTagClickListener() {
                    @Override
                    public void onTagClick(int position, String text) {
                        if (onCityClickListener != null){
                            onCityClickListener.onCityClick(hotCities.get(position));
                        }
                    }

                    @Override
                    public void onTagLongClick(int position, String text) {
                    }

                    @Override
                    public void onTagCrossClick(int position) {

                    }
                });


//                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext,hotCities);
//                gridView.setAdapter(hotCityGridAdapter);
//                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        if (onCityClickListener != null){
//                            onCityClickListener.onCityClick(hotCities.get(position));
//                        }
//                    }
//                });
                break;
            case 1:     //所有
                if (view == null){
                    view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                }else{
                    holder = (CityViewHolder) view.getTag();
                }
                if (position >= 1){
                    final String city = mCities.get(position).getName();
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getIndex());
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getIndex()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)){
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    }else{
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null){
                                onCityClickListener.onCityClick(mCities.get(position));
                            }
                        }
                    });
                }
                break;
        }
        return view;
    }

    public static class CityViewHolder{
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener){
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener{
        void onCityClick(City name);
    }
}
