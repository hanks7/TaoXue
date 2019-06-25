package com.taoxue.ui.module.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseListFragment;
import com.taoxue.base.BaseRecyclerAdapter;
import com.taoxue.base.BaseViewHolder;
import com.taoxue.http.OnRefreshListResponseListener;
import com.taoxue.ui.model.ReadBean;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hopshine on 2017/1/5.PlayRecordFragment
 */

public class PlayRecordFragment extends BaseListFragment<PlayRecordFragment.ExampleHolder, ReadBean> {

    @Override
    protected void onInitTopBar(TopBar topBar) {
        topBar.setTitle(R.string.play_record);
    }

    @Override
    protected void loadData(int page, int pageSize) {
        getServer().read(TaoXueApplication.get().getUserModel().getUser_id(), page, pageSize)
                .enqueue(new OnRefreshListResponseListener<ReadBean>(getActivity(),
                        getRecyclerView(), getPageInfoModel()));
    }
    private void toDetailActivity(String id){
        launch(ResourceDetailActivity.class,id);
    }
    @Override
    protected void onInit() {
        super.onInit();
        setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toDetailActivity(getAdapter().getItem(position).getResource_id());
            }
        });
    }

    @Override
    protected BaseRecyclerAdapter<ExampleHolder, ReadBean> createAdapter() {
        return new DiaryListAdapter();
    }

    private class DiaryListAdapter extends BaseRecyclerAdapter<PlayRecordFragment.ExampleHolder, ReadBean> {

        @Override
        protected int getItemLayout() {
            return R.layout.ui_frag_my_collection;
        }

        @Override
        protected void bindView(PlayRecordFragment.ExampleHolder holder, ReadBean readBean, int position) {
            bindAdapterItemView(holder, readBean, position);
        }

        @Override
        protected PlayRecordFragment.ExampleHolder getHolder(View itemView) {
            return new PlayRecordFragment.ExampleHolder(itemView);
        }


    }

    /**
     * /**
     * resource_id : 402883d25d5a16b0015d5a3a1d260011
     * visit_time : 2017-08-03 16:32:16
     * resource_name : 林砺儒先生
     * resource_picture : http://117.71.57.47:10000/resource/uploadFiles/2017/07/19/1500457342482036143_150.jpg
     * file_type : audio
     * file_format :
     * file_path :
     * url :
     * filesize :
     * status : 1
     *
     * @param holder
     * @param readBean
     * @param position
     */
    protected void bindAdapterItemView(PlayRecordFragment.ExampleHolder holder, ReadBean readBean, int position) {
        UtilGlide.loadImg(getActivity(),readBean.getResource_picture(),holder.vhIvPic);
        holder.vhTvBookName.setText(readBean.getResource_name());
        holder.vhTvFormatName.setText("格式："+readBean.getFile_format());
        holder.vhTvTime.setText(readBean.getVisit_time());
    }

    public class ExampleHolder extends BaseViewHolder {
        @BindView(R.id.frag_collection_iv_pic)
        ImageView vhIvPic;
        @BindView(R.id.frag_collection_tv_book_name)
        TextView vhTvBookName;
        @BindView(R.id.frag_collection_tv_format_name)
        TextView vhTvFormatName;
        @BindView(R.id.frag_collection_tv_time)
        TextView vhTvTime;


        ExampleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }

}
