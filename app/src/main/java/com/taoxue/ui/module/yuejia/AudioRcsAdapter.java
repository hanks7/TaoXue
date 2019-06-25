package com.taoxue.ui.module.yuejia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.model.VolumnBean;

import java.util.List;

/**
 * Created by User on 2018/2/7.
 */

public class AudioRcsAdapter extends RecyclerView.Adapter<AudioRcsAdapter.ViewHolder> {
    private List<VolumnBean> mData;
    SparseArray<View> mViews;
    int preIndex=-1;
    int currentIndex=0;
    private Context context;

    protected OnImageItemClickListener mOnItemClickListener;

    public void setOnItemAdapterClickListener(OnImageItemClickListener mOnItemAdapterClickListener) {
        this.mOnItemClickListener = mOnItemAdapterClickListener;
    }

    public AudioRcsAdapter(Context context, List<VolumnBean> data, int currentIndex) {
        this.context=context;
        this.mData = data;
        this.currentIndex=currentIndex;
        mViews=new SparseArray<>();
    }

    public void updateData(List<VolumnBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public SparseArray<View> getmViews() {
        return mViews;
    }
    public View getItemView(int position){
        return   mViews.get(position);
    }
    public ImageView getImageItemView(int position){
        return  (ImageView) mViews.get(position).findViewById(R.id.iv_image);
    }


    @Override
    public AudioRcsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_list_item, parent, false);
        final AudioRcsAdapter.ViewHolder viewHolder = new AudioRcsAdapter.ViewHolder(v);

        // 实例化viewholder

        return viewHolder;
    }

   public interface ClickCallBack{
        void onClickIndex(int position);
   }
    public void onClick(final ClickCallBack mClickCallBack){
        setOnItemAdapterClickListener(new OnImageItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position!=preIndex){
                    ((TextView)v.findViewById(R.id.audio_list_title_tv)).setTextColor(context.getResources().getColor(R.color.audio_seclect_textcolor));
                    ((TextView)v.findViewById(R.id.audio_list_index_tv)).setTextColor(context.getResources().getColor(R.color.audio_seclect_textcolor));
                    ((TextView)getItemView(preIndex).findViewById(R.id.audio_list_title_tv)).setTextColor(context.getResources().getColor(R.color.audio_dialog_nomal_textcolor));
                    ((TextView)getItemView(preIndex).findViewById(R.id.audio_list_index_tv)).setTextColor(context.getResources().getColor(R.color.audio_dialog_nomal_textcolor));
                   preIndex=position;
                }
                if (mClickCallBack!=null){
                    mClickCallBack.onClickIndex(position);
                }

            }
        });
    }
  public void setCurrentIndex(int position){
      if (position!=preIndex){
          ((TextView)getItemView(position).findViewById(R.id.audio_list_title_tv)).setTextColor(context.getResources().getColor(R.color.audio_seclect_textcolor));
          ((TextView)getItemView(position).findViewById(R.id.audio_list_index_tv)).setTextColor(context.getResources().getColor(R.color.audio_seclect_textcolor));
          ((TextView)getItemView(preIndex).findViewById(R.id.audio_list_title_tv)).setTextColor(context.getResources().getColor(R.color.audio_dialog_nomal_textcolor));
          ((TextView)getItemView(preIndex).findViewById(R.id.audio_list_index_tv)).setTextColor(context.getResources().getColor(R.color.audio_dialog_nomal_textcolor));
          preIndex=position;
      }
  }


    @Override
    public void onBindViewHolder(AudioRcsAdapter.ViewHolder holder, final int position) {
        mViews.put(position,holder.v);
        holder.tvindex.setText(position+1+"");
        holder.titletv.setText(mData.get(position).getResource_name());
     if (currentIndex==position){
         holder.tvindex.setTextColor(context.getResources().getColor(R.color.audio_seclect_textcolor));
         holder.titletv.setTextColor(context.getResources().getColor(R.color.audio_seclect_textcolor));
         preIndex=currentIndex;
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
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }
//public



    public static class ViewHolder extends RecyclerView.ViewHolder {

        View v;
        TextView  titletv;
        TextView  tvindex;
        public ViewHolder(View itemView) {
            super(itemView);
            this.v=itemView;
            titletv = (TextView) itemView.findViewById(R.id.audio_list_title_tv);
            tvindex=(TextView) itemView.findViewById(R.id.audio_list_index_tv);
        }
    }
}
