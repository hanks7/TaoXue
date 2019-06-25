package com.taoxue.ui.module.classification.resourceLib;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.taoxue.R;
import com.taoxue.ui.model.CgsModel;
import com.taoxue.ui.model.LibCgsModel;
import com.taoxue.ui.model.LibInfoModel;
import com.taoxue.ui.model.ResourceLibModel;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.classification.vpFragment.BaseVpFragment;
import com.taoxue.ui.module.search.HomeLibActivity;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.glide.UtilGlide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 2017/6/21.
 */

public class ResourceLibDetailFragment extends BaseVpFragment {


    Unbinder unbinder;

    LibInfoModel model;
    @BindView(R.id.resource_lib_description_tv)
    TextView resourceLibDescriptionTv;
    @BindView(R.id.resource_lib_recycle_view)
    RecyclerView resourceLibRecycleView;

    @Override
    protected void getActiArguments(Serializable arguments) {
        model = (LibInfoModel) arguments;
    }

    private List<LibCgsModel>cgsModels;
    @Override
    protected void onInit(View view) {
        resourceLibDescriptionTv.setText(CommitContent.nullToSting(model.getDr_data_info().getDescription()));
        if (null!=model.getCgs()&&!(model.getCgs()+"").equals("[]")&&model.getCgs().size()>0) {
            cgsModels=model.getCgs();
//            addData();
            setAdapter();
        }
    }
//    private  void addData(){
//        cgsModels=model.getBuy_drdata_cgs();
//        for (int i=0;i<5;i++){
//            CgsModel cgs=new CgsModel();
//            cgs.setLogo(model.getBuy_drdata_cgs().get(0).getLogo());
//            cgs.setCgs_name(model.getBuy_drdata_cgs().get(0).getCgs_name()+i+"dfc");
//            cgsModels.add(cgs);
//         }
//        }

   private class OnItemClick implements OnItemAdapterClickListener {
           @Override
           public void onItemClick(View view, RvViewHolder holder, int position, int viewType) {
               Bundle bundle = new Bundle();
               bundle.putString("ids", cgsModels.get(position).getCgs_id());
               UtilIntent.intentDIYLeftToRight(getActivity(), HomeLibActivity.class, bundle);
           }
       }

 private void setAdapter(){
     RvComAdapter.Builder builder=new RvComAdapter.Builder(getActivity(),R.layout.ui_image_text,cgsModels);
     builder.setOrientation(LinearLayoutManager.HORIZONTAL);
     builder.setOnItemAdapterClickListener(new OnItemClick());
     builder.into(resourceLibRecycleView, new InitViewCallBack<LibCgsModel>() {
         @Override
         public void convert(RvViewHolder holder, LibCgsModel cgsModel, int i) {
             UtilGlide.loadImg(getActivity(),cgsModel.getLogo(),(ImageView) holder.getView(R.id.image_iv));
//             holder.setImageUrl(R.id.image_iv,cgsModel.getLogo());
             holder.setText(R.id.text_tv,cgsModel.getName());
         }
     });


//
//
//
//
//     LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//
//     CommonAdapter adapter =new CommonAdapter<CgsModel>(getActivity(),R.layout.ui_image_text,cgsModels) {
//         @Override
//         protected void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//         }
//         @Override
//         protected void convert(ViewHolder holder, CgsModel cgsModel, int position) {
//
//         }
//     };
//     resourceLibRecycleView.setLayoutManager(manager);
     resourceLibRecycleView.setHasFixedSize(true);
//     resourceLibRecycleView.setAdapter(adapter);
     resourceLibRecycleView.setPadding(0,20,20,15);
 }
    @Override
    protected int getLayout() {
        return R.layout.vp_resource_lib_detail;
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
