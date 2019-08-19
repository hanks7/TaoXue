package com.taoxue.utils.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;

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

    public void addData(List<ApiOneBean.BannerBean> textUrlList) {
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
//            String url = null;
//            switch (new Random().nextInt(10)) {
//                case 0:
//                    url = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2564176338.jpg";
//                    break;
//                case 1:
//                    url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2564170314.webp";
//                    break;
//                case 2:
//                    url = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2562934437.webp";
//                    break;
//                case 3:
//                    url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2564458983.webp";
//                    break;
//                case 4:
//                    url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2549177902.jpg";
//                    break;
//                case 5:
//                    url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2564368261.jpg";
//                    break;
//                case 6:
//                    url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2562953341.jpg";
//                    break;
//                case 7:
//                    url = "https://c-ssl.duitang.com/uploads/item/201406/30/20140630185226_xniwt.jpeg";
//                    break;
//                case 8:
//                    url = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2560975437.jpg";
//                    break;
//                case 9:
//                    url = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2562677270.jpg";
//                    break;
//                case 10:
//
//                    break;
//
//                default:
//                    url = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2541901817.jpg";
//            }
            Glide.with(context)
                    .load(data.getImg())
                    .placeholder(com.jaiky.imagespickers.R.mipmap.imageselector_photo)
                    .centerCrop()
                    .into(imageView);
        }
    }
}
