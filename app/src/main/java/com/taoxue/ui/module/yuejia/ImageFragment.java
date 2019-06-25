package com.taoxue.ui.module.yuejia;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.taoxue.R;
import com.taoxue.base.BaseFragment;
import com.taoxue.utils.glide.UtilGlide;

/**
 * Created by User on 2018/2/2.
 */

public class ImageFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.image_vp;
    }

    @Override
    protected void onInit() {
     String imageUrl=  getArguments().getString("imagekey");
        UtilGlide.loadImgNomal(getActivity(),imageUrl,(ImageView)findViewById(R.id.iv_image));
    }

    public static ImageFragment newInstance( String imageUrl) {
        Bundle args = new Bundle();
        args.putString("imagekey",imageUrl);
        ImageFragment fragment = new ImageFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
