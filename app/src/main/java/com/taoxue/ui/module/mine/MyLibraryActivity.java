package com.taoxue.ui.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;

import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.classification.HttpRequest;
import com.taoxue.ui.module.login.ContactReaderCardActivity;
import com.taoxue.ui.module.search.WebLibHomeActivity;
import com.taoxue.ui.view.RDividerItemDecoration;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MyLibraryActivity extends BaseActivity {

    @BindView(R.id.my_liblay_rv)
    RecyclerView myLiblayRv;
    @BindView(R.id.my_liblay_bind_reader_card_tv)
    TextView myLiblayBindReaderCardTv;
    List<MyLibInfo> myLibInfos;
    @BindView(R.id.my_liblay_bind_success_ll)
    LinearLayout myLiblayBindSuccessLl;
    @BindView(R.id.my_liblay_bind_reader_tv)
    TextView myLiblayBindReaderTv;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.D("onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.D("onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.D("onResume()");
        user_id = CommitContent.isLogin(this);
//        myLibBean = (MyLibBean) UtilGson.fromJson((String) SPUtil.get(SPUtil.MY_LIB_INFO_LIST_KEY, ""), MyLibBean.class);
        if (user_id != null) {
                queryMyLib(user_id);
        }
    }

    private void saveMyLibInfo() {
        StringBuilder order_string=new StringBuilder();
        for (int i=0;i<myLibInfos.size();i++){
            order_string.append(user_id+"|");
            order_string.append(myLibInfos.get(i).getCgs_id()+"|");
            if (i==myLibInfos.size()-1) {
                order_string.append(i);
            }else {
                order_string.append(i + ",");
            }
        }

        Call<BaseResultModel>call= HttpAdapter.getService().updatePayOrder(order_string.toString());
    call.enqueue(new OnResponseNoDialogListener<BaseResultModel>() {
        @Override
        protected void onSuccess(BaseResultModel baseResultModel) {
            LogUtils.D("修改支付顺序---》"+baseResultModel.toString());
            if (baseResultModel.getCode()==1){
                queryMyLib(user_id);
            }
        }
    });
//        myLibBean = new MyLibBean();
//        myLibBean.setMyLibInfos(myLibInfos);
//        SPUtil.put(SPUtil.MY_LIB_INFO_LIST_KEY, UtilGson.toJson(myLibBean)); //将信息添加进文件中缓存
    }
        private class OnItemClick implements OnItemAdapterClickListener {
                @Override
                public void onItemClick(View view, RvViewHolder holder, int position, int viewType) {
                    launch(WebLibHomeActivity.class, myLibInfos.get(position).getCgs_id());
                }
            }

   RvComAdapter commonAdapter;
    private void updateView() {
        myLiblayRv.setNestedScrollingEnabled(false);
//        myLibInfos=myLibBean.getMyLibInfos();
        RvComAdapter.Builder builder=new RvComAdapter.Builder(this,R.layout.my_library_item,myLibInfos);
           builder.setOnItemAdapterClickListener(new OnItemClick());
         commonAdapter=builder.into(myLiblayRv, new InitViewCallBack<MyLibInfo>() {
                 @Override
                 public void convert(RvViewHolder holder, MyLibInfo myLibInfo, int position) {
                     UtilGlide.loadImg(MyLibraryActivity.this,myLibInfo.getLogo(),(ImageView)holder.getView(R.id.my_liblay_item_image_iv));
//                     holder.setImageUrl(R.id.my_liblay_item_image_iv, myLibInfo.getLogo());
                     holder.setText(R.id.my_liblay_name_tv, myLibInfo.getName());

                     if (position == 0) {
                         holder.setVisible(R.id.my_libray_item_up, View.GONE);
                     }
                     if (position == (myLibInfos.size() - 1)) {
                         holder.setVisible(R.id.my_libray_item_down, View.GONE);
                     }
                     if (holder.getViewVisibility(R.id.my_libray_item_up) != View.GONE) {
                         holder.setOnClickListener(R.id.my_libray_item_up, new paixu(position));
                     }
                     if (holder.getViewVisibility(R.id.my_libray_item_down) != View.GONE) {
                         holder.setOnClickListener(R.id.my_libray_item_down, new paixu1(position));
                     }
                 }
             });

        RDividerItemDecoration rid=new RDividerItemDecoration(this,RDividerItemDecoration.VERTICAL);
        rid.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        myLiblayRv.addItemDecoration(rid);

    }

    @OnClick(R.id.my_liblay_bind_reader_card_tv)
    public void onViewClicked() {
         toConReaderCardActivity();
    }

    public void toConReaderCardActivity(){
        Intent intent=new Intent(MyLibraryActivity.this,ContactReaderCardActivity.class);
        intent.putExtra("type","mycard");
        startActivityForResult(intent,reqCode);
    }


    private class paixu1 implements View.OnClickListener {
        private int position;

        public paixu1(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            int replaceNum = position + 1;
            myLibInfos.add(position, myLibInfos.get(replaceNum));
            myLibInfos.remove(replaceNum + 1);
//            commonAdapter.notifyDataSetChangedRv();
            saveMyLibInfo();
        }
    }


    private class paixu implements View.OnClickListener {
        private int position;

        public paixu(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            LogUtils.D("position--->" + position);
            int replaceNum1 = position - 1;
            LogUtils.D("replaceNum1--->" + replaceNum1);
            int replaceNum2 = position;
            myLibInfos.add(replaceNum1, myLibInfos.get(replaceNum2));
            LogUtils.D("myLibInfos--->" + myLibInfos.toString());
            myLibInfos.add(replaceNum2 + 1, myLibInfos.get(replaceNum1 + 1));
            LogUtils.D("myLibInfos--->" + myLibInfos.toString());
            myLibInfos.remove(replaceNum1 + 1);
            LogUtils.D("myLibInfos--->" + myLibInfos.toString());
            myLibInfos.remove(replaceNum2 + 1);
            LogUtils.D("myLibInfos--->" + myLibInfos.toString());
//            commonAdapter.notifyDataSetChangedRv();
            saveMyLibInfo();
        }
    }

    private  int reqCode=1;
//    private MyLibBean myLibBean;

  public static final int GUANLIAN_READER_CARD=10;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        reader_card_id=data.getStringExtra("reader_card_id");
//        cgs_id=data.getStringExtra("cgs_id");
//        LogUtils.D("注册");
        if (data==null){
            return;
        }
        LogUtils.D("返回数据");
        switch (requestCode){
            case GUANLIAN_READER_CARD:
                LogUtils.D("返回数据");
                queryMyLib(user_id);
                break;
        }
    }
    //查询我的图书馆信息
    private void queryMyLib(String user_id) {
        HttpRequest.queryMyLibInfo(user_id, new HttpRequest.RequestMyLibCallBack() {
            @Override
            public void onSuccess(List<MyLibInfo> myLibInfos) {//查到图书馆信息
                MyLibraryActivity.this.myLibInfos=myLibInfos;
//                saveMyLibInfo();
                updateView();
            }

            @Override
            public void onUnSuccess(String msg) {//未查到我的图书馆信息
                myLiblayBindSuccessLl.setVisibility(View.GONE);
                myLiblayBindReaderTv.setVisibility(View.VISIBLE);
                myLiblayBindReaderTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        launch(ReaderCardActivity.class,CommitContent.MY_LIB_CANSHU);
                                 toConReaderCardActivity();

//                        Intent intent=new Intent(MyLibraryActivity.this,ReaderCardActivity.class);
//
//                        Bundle bundle =new Bundle();
//                        bundle.putSerializable(CommitContent.MY_LIB,CommitContent.MY_LIB_CANSHU);
//////                        intent.putExtra(CommitContent.MY_LIB,CommitContent.MY_LIB_CANSHU);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        startActivityForResult(intent,reqCode);
                    }
                });
            }
        });
    }
}
