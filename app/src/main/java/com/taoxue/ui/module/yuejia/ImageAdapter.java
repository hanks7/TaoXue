package com.taoxue.ui.module.yuejia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.taoxue.R;
import com.taoxue.ui.model.VolumnBean;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

/**
 * Created by User on 2018/2/6.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<VolumnBean> mData;
    SparseArray<View>mViews;
   private int i=0;
  private Context context;
  private int   currentIndex=0;

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    protected OnImageItemClickListener mOnItemClickListener;

    public void setOnItemAdapterClickListener(OnImageItemClickListener mOnItemAdapterClickListener) {
        this.mOnItemClickListener = mOnItemAdapterClickListener;
    }

    public ImageAdapter(Context context,List<VolumnBean> data) {
        this.context=context;
        this.mData = data;
        mViews=new SparseArray<>();
        this.i=0;
    }

    public void updateData(List<VolumnBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public SparseArray<View> getmViews() {
        return mViews;
    }
   public ImageView getItemView(int position){
        return  (ImageView) mViews.get(position);
    }
    public ImageView getImageItemView(int position){
        return  (ImageView) mViews.get(position).findViewById(R.id.iv_image);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_rv, parent, false);
       ViewHolder viewHolder = new ViewHolder(v);

        // 实例化viewholder

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mViews.put(position,holder.iv);
        LogUtils.D("创建新的");
        holder.iv.setImageAlpha(ScanImageActivity.banTouming);
        if (position==currentIndex){
            holder.iv.setImageAlpha(ScanImageActivity.imageTranstion);
        }
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v,position);
                }
            }
        });
        // 绑定数据
        UtilGlide.loadImg(context,mData.get(position).getUrl(),holder.iv);
//        UtilGlide.loadImageThumbnailRequest(context,mData.get(position).getUrl(),holder.iv);

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {

        View v;
        ImageView iv;
        public ViewHolder(View itemView) {
            super(itemView);
            this.v=itemView;
            iv = (ImageView) itemView.findViewById(R.id.iv_image);

}
    }
}
