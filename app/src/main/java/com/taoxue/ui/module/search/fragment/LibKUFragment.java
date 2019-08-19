package com.taoxue.ui.module.search.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.base.BaseListNoTopBarFragment;
import com.taoxue.base.BaseRecyclerAdapter;
import com.taoxue.base.BaseViewHolder;
import com.taoxue.http.OnRefreshListResponseListener;
import com.taoxue.ui.model.GysDrResultBean;
import com.taoxue.ui.module.classification.resourceLib.ResourceLibraryActivity;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;


public class LibKUFragment extends BaseListNoTopBarFragment<LibKUFragment.ExampleHolder, GysDrResultBean> {

    @Override
    protected void loadData(int page, int pageSize) {
        getServer().getCgsDrdatafasdfasdf(pageSize, page, getArguments().getString("ids"))
                .enqueue(new OnRefreshListResponseListener<GysDrResultBean>((BaseActivity) getActivity(), getRecyclerView(), getPageInfoModel()));
    }

    @Override
    protected void onInit() {
        super.onInit();
        getRecyclerView().setPullToRefreshEnable(false);
        setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GysDrResultBean bean = getAdapter().getItem(position);
                ((BaseActivity) getActivity()).launch(ResourceLibraryActivity.class, bean.getId());
            }
        });
    }


    @Override
    protected BaseRecyclerAdapter<LibKUFragment.ExampleHolder, GysDrResultBean> createAdapter() {
        return new LibKUFragment.DiaryListAdapter();
    }

    private class DiaryListAdapter extends BaseRecyclerAdapter<LibKUFragment.ExampleHolder, GysDrResultBean> {

        @Override
        protected int getItemLayout() {
            return R.layout.ui_frag_search_result;
        }

        @Override
        protected void bindView(LibKUFragment.ExampleHolder holder, GysDrResultBean bean, int position) {
            bindAdapterItemView(holder, bean, position);
        }

        @Override
        protected LibKUFragment.ExampleHolder getHolder(View itemView) {
            return new LibKUFragment.ExampleHolder(itemView);
        }
    }

    public class ExampleHolder extends BaseViewHolder {

        @BindView(R.id.frg_search_result_iv_pic)
        ImageView iIvPic;
        @BindView(R.id.frg_search_result_tv_name)
        TextView iTvName;
        @BindView(R.id.frg_search_result_tv_supplier_name)
        TextView iTvSupplierName;

        public ExampleHolder(View itemView) {
            super(itemView);
        }
    }


    /**
     * description : "小破孩"和"小丫"两个胖乎乎的卡通人物,造型简洁流畅,具有典型的中国风格.故事的取材和表现非常多样
     * gys_id : 402883a95ce25928015ce27375bd0002
     * gys_name : 澳通大连科技发展有限公司
     * id : 402883d25d73f0f8015d73f1f1070001
     * logo : http://117.71.57.47:10000/resource/uploadFiles/2017/07/24/1500888756757059711_150.jpg
     * name : 小破孩希系列
     */
    protected void bindAdapterItemView(LibKUFragment.ExampleHolder holder, final GysDrResultBean bean, int position) {
        UtilGlide.loadRoundImage(getActivity(), bean.getLogo(), holder.iIvPic);
        holder.iTvName.setText(bean.getName());
        holder.iTvSupplierName.setText(bean.getGys_name());
    }


}
