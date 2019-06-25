package com.taoxue.ui.module.yuejia;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseFragment;

/**
 * Created by User on 2018/2/9.
 */

public class BaseResourceFragment extends BaseFragment implements View.OnClickListener {
    Dialog dialog;
   View orderRecentlyRead;
   View  orderRecentlyPay;
    View  orderRecentlyWord;
    public void show(){
        dialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pai_xu, null);
            orderRecentlyRead=inflate.findViewById(R.id.order_recently_read);
           orderRecentlyPay =inflate.findViewById(R.id.order_recently_pay);
        orderRecentlyWord =inflate.findViewById(R.id.order_recently_word);
        orderRecentlyPay.setOnClickListener(this);
        orderRecentlyRead.setOnClickListener(this);
        orderRecentlyWord.setOnClickListener(this);

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.height= LinearLayout.LayoutParams.WRAP_CONTENT;
        lp.width= LinearLayout.LayoutParams.MATCH_PARENT;
//        lp.y = 30;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }
    public  void hiddenDialog(){
        if (dialog!=null){
            dialog.cancel();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void onInit() {

    }
}
