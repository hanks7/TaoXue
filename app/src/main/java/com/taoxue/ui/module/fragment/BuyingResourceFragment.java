package com.taoxue.ui.module.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.BaseApplication;
import com.taoxue.base.BaseListFragment;
import com.taoxue.base.BaseRecyclerAdapter;
import com.taoxue.base.BaseViewHolder;
import com.taoxue.http.OnRefreshListResponseListener;
import com.taoxue.ui.model.BuyingResource;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;

/**
 * Created by hopshine on 2017/1/5.PlayRecordFragment
 */

public class BuyingResourceFragment extends BaseListFragment<BuyingResourceFragment.ExampleHolder, BuyingResource> {

    @Override
    protected void loadData(int page, int pageSize) {
        getServer().userPay(BaseApplication.get().getUserModel().getUser_id(), page, pageSize)
                .enqueue(new OnRefreshListResponseListener<BuyingResource>(getActivity(),getRecyclerView(), getPageInfoModel()));
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
    protected BaseRecyclerAdapter<ExampleHolder, BuyingResource> createAdapter() {
        return new DiaryListAdapter();
    }

    private class DiaryListAdapter extends BaseRecyclerAdapter<BuyingResourceFragment.ExampleHolder, BuyingResource> {

        @Override
        protected int getItemLayout() {
            return R.layout.ui_frag_my_collection;
        }

        @Override
        protected void bindView(BuyingResourceFragment.ExampleHolder holder, BuyingResource bean, int position) {
            bindAdapterItemView(holder, bean, position);
        }

        @Override
        protected BuyingResourceFragment.ExampleHolder getHolder(View itemView) {
            return new BuyingResourceFragment.ExampleHolder(itemView);
        }
    }

    @Override
    protected void onInitTopBar(TopBar topBar) {
        topBar.setTitle(R.string.buying_resource);
    }

    public class ExampleHolder extends BaseViewHolder {

        @BindView(R.id.frag_collection_iv_pic)
        ImageView iIvPic;
        @BindView(R.id.frag_collection_tv_book_name)
        TextView iTvBookName;
        @BindView(R.id.frag_collection_tv_format_name)
        TextView iTvFormatName;
        @BindView(R.id.frag_collection_tv_time)
        TextView iTvTime;

        public ExampleHolder(View itemView) {
            super(itemView);
        }
    }


    private void toDetailActivity(String id){
        launch(ResourceDetailActivity.class,id);
    }
    /**
     * description : 破孩动画具有浓郁的中国特色，风格多样，有些幽默搞笑，又有一定讽刺意味；有些温馨浪漫，崇尚温情；有些童气十足，老少皆宜。小破孩动画注重娱乐性、思想性、艺术性的结合，动画内容取材广泛，不局限于中国传统文化素材，也可以是现代的、国外的、即时的素材，各种题材又融入了许多现代、时尚的元素，更适广大观众的口味。
     * pay_time : 2017-08-11 14:57:21
     * pay_type : ZFB
     * resource_id : 402883d25d6334d1015d6341b9660004
     * resource_name : 小破孩-包
     * resource_picture : http://117.71.57.47:10000/resource/uploadFiles/2017/08/04/1501824773680068069_150.png
     * total_fee : 0.02
     */
    protected void bindAdapterItemView(BuyingResourceFragment.ExampleHolder holder, final BuyingResource bean, int position) {
        holder.iTvBookName.setText(bean.getResource_name()+"");
        holder.iTvFormatName.setText("购买时间："+bean.getPay_time());
        holder.iTvTime.setText("");
        UtilGlide.loadImg(getActivity(),bean.getResource_picture(),holder.iIvPic);
    }


}
