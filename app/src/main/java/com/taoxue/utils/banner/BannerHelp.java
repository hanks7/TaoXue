package com.taoxue.utils.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

/**
 * Created by User on 2017/8/28.
 */

public class BannerHelp {
    ConvenientBanner mBanner;

    /**
     * 初始广告轮播页
     */
    public BannerHelp(ConvenientBanner mConvenientBanner) {
        this.mBanner = mConvenientBanner;
        mBanner.setFocusable(true);
        mBanner.setFocusableInTouchMode(true);
        mBanner.requestFocus();
        mBanner
                .setPageIndicator(new int[]{R.mipmap.u1733, R.mipmap.u1735})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);

    }

    public void addData( List<ApiOneBean.BannerBean> textUrlList) {
        mBanner.setPages(
                new CBViewHolderCreator<LocalIImageHolderView>() {
                    @Override
                    public LocalIImageHolderView createHolder() {
                        return new LocalIImageHolderView();
                    }
                }, textUrlList);
    }

    public void start() {
        mBanner.startTurning(3000);
    }

    public void stop() {
        mBanner.stopTurning();
    }

    /**
     * 广告轮播内部类
     */
    class LocalIImageHolderView implements Holder<ApiOneBean.BannerBean> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, ApiOneBean.BannerBean data) {
            UtilGlide.loadImg(context, data.getImg(), imageView);
        }
    }
}
