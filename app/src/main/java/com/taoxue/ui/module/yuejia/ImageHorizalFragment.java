package com.taoxue.ui.module.yuejia;

import android.os.Bundle;
import android.widget.ImageView;

import com.taoxue.R;
import com.taoxue.base.BaseFragment;
import com.taoxue.utils.glide.UtilGlide;

/**
 * Created by User on 2018/2/2.
 */

public class ImageHorizalFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.image_rv;
    }

    @Override
    protected void onInit() {
     String imageUrl=  getArguments().getString("imagkey");
        UtilGlide.loadImg(getActivity(),imageUrl,(ImageView)findViewById(R.id.iv_image));
    }

    public static ImageHorizalFragment newInstance(String imageUrl) {
        Bundle args = new Bundle();
        args.putString("imagkey",imageUrl);
        ImageHorizalFragment fragment = new ImageHorizalFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
