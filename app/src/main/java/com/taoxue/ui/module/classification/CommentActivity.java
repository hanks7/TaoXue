package com.taoxue.ui.module.classification;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnLoadMoreListener;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.AudioResourceBean;
import com.taoxue.ui.model.ComentModel;
import com.taoxue.ui.model.CommitPageModel;
import com.taoxue.ui.model.PageModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.view.StarBar;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class CommentActivity extends BaseActivity {

    @BindView(R.id.comment_introduction_topbar)
    TopBar commentIntroductionTopbar;
    @BindView(R.id.comment_recyclerview)
    RecyclerView commentRecyclerview;
    @BindView(R.id.comment_no_content_ll)
    RelativeLayout commentNoContentTv;
    @BindView(R.id.comment_start_bar)
    StarBar commentStartBar;
    @BindView(R.id.comment_Content_et)
    EditText commentContentEt;
    @BindView(R.id.coment_commit_tv)
    TextView comentCommitTv;
    @BindView(R.id.coment_commit_ll)
    RelativeLayout comentCommitLl;
    @BindView(R.id.commit_ll)
    LinearLayout commitLl;

    List<ComentModel> comentModels;
    AudioResourceBean data;
    Handler handler = new Handler();
    private  int pageNo=1;
    private  int pageSize=10;
//    private boolean isComment=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        getIntentData();
    }
  private void getIntentData(){
      data=(AudioResourceBean) getIntentKey1();
      if (data!=null){
          requestQueryCommitContent();
      }else {
          showToast("未获取资源");
      }
      commentStartBar.setIntegerMark(true);//设置整数评分

      CommitContent.addTextChange(this,commentContentEt);//editText控制显示
  }
    private void requestQueryCommitContent() {
//        commentContentEt.setFocusable(true);
//        commentContentEt.setFocusableInTouchMode(true);
//        commentContentEt.requestFocus();
//        InputMethodManager inputManager = (InputMethodManager)commentContentEt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.showSoftInput(commentContentEt, 0);

        LogUtils.D("查询评论请求");
        Call<CommitPageModel<PageModel<ComentModel>>> call = HttpAdapter.getService().queryResourceComment(data.getResource_id(), pageSize+"", pageNo+"");
        call.enqueue(new OnResponseNoDialogListener<CommitPageModel<PageModel<ComentModel>>>() {
            @Override
            protected void onSuccess(CommitPageModel<PageModel<ComentModel>> model) {
                LogUtils.D("98775454csdckhsjdbhdcjsb");
                LogUtils.D("model.getCode()--->" + model.getCode());
                if (model.getCode() == 1 && null != model.getPage() && !"[]".equals(model.getPage()+"") && null != model.getPage().getResult() && !"[]".equals((model.getPage().getResult()+""))) {
                    LogUtils.D("model.getPage().getResult()--->"+model.getPage().getResult());
                    if (pageNo>=2) {
                       if (model.getPage().getResult().size()>0){
                           comentModels.addAll(model.getPage().getResult());
                          singleItemAdapter.notifyDataSetChanged();
                       }
                   }else{
                       if (model.getPage().getResult().size() > 0) {
                           comentModels = model.getPage().getResult();
                           setAdapter();
                       }else{
                           noContent();
                       }
                   }
                }else {
                    LogUtils.D("没有评论");
                    noContent();
                }
            }
        });
    }

    private void noContent() { //没有评论
//        noContentTv.setText("暂时没有评论，请可以添加评论哦 +");
//        noContentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
//        noContentTv.setTextColor(Color.BLACK);
        commentRecyclerview.setVisibility(View.GONE);
        commentNoContentTv.setVisibility(View.VISIBLE);
    }

    RvComAdapter singleItemAdapter;
    //设置评论中的页面布局
    private void setAdapter() {
        RvComAdapter.Builder builder=new RvComAdapter.Builder<ComentModel>(this,R.layout.book_introduction_item,comentModels);
              builder.setOnLoadMoreListener(new OnLoadMoreListener() {
                  @Override
                  public void onLoadMore(RvViewHolder holder) {
                      if (comentModels.size()==pageNo*pageSize){
                          pageNo++;
                          requestQueryCommitContent();
                      }else {
                          LogUtils.D("评论加载更多");
                          holder.setText(R.id.more_progress_text, "已经没有更多了");
                          holder.setVisible(R.id.more_progress_pb, View.GONE);
                      }
                  }
              });

        singleItemAdapter=builder.into(commentRecyclerview, new InitViewCallBack<ComentModel>() {
                         @Override
                         public void convert(RvViewHolder holder, ComentModel comentModel, int position) {
                             UtilGlide.loadImg(CommentActivity.this,comentModel.getPhoto(),(ImageView) holder.getView(R.id.commit_image_iv));
//                             holder.setImageUrl(R.id.commit_image_iv, comentModel.getPhoto());
                             holder.setText(R.id.commit_time_tv, CommitContent.nullToSting(comentModel.getAddtime()));
                             holder.setText(R.id.commit_content_tv, nullToSting(comentModel.getContent()));
                             holder.setText(R.id.commit_name_tv, nullToSting(comentModel.getName()));
                             float score=Float.parseFloat(comentModel.getScore());
                             ((StarBar) holder.getView(R.id.commit_start_bar)).setStarMark(score);
                             ((StarBar) holder.getView(R.id.commit_start_bar)).setChangMark(false);
                         }
                     }
             );
        commentRecyclerview.setHasFixedSize(true);
        commentRecyclerview.setNestedScrollingEnabled(false);
    }





    private void updateView() {
        pageNo = 1;
        requestQueryCommitContent();
    }



    //添加评论
    private void addCommitContent() {
        LogUtils.D("发送评论请求");
        String user_id = CommitContent.isLogin(this);
        String comment = commentContentEt.getText().toString();
      String score=commentStartBar.getStarMark()+"";




        if (TextUtils.isEmpty(comment)) {
           showToast("请先输入内容");
            return;
        }

        if (user_id != null) {
            HttpRequest.addCommitContent(data.getResource_id(), comment,score,new HttpRequest.RequestCallBack() {
                @Override
                public void onSuccess(String msg) {
                    updateView();
                    if (commentNoContentTv.getVisibility()==View.VISIBLE){
                        commentRecyclerview.setVisibility(View.VISIBLE);
                        commentNoContentTv.setVisibility(View.GONE);
                    }
                    commentContentEt.setText("");
//                    hideInputMethod(CommentActivity.this, commentContentEt);
                    commentStartBar.setStarMark(0);
                }
                @Override
                public void onRequested(String msg) { //表示评论过了
                    showToast(msg);
                    commentContentEt.setText("");
//                    hideInputMethod(CommentActivity.this, commentContentEt);
                }
                @Override
                public void onUnSuccess(String msg) {//表示评论失败
                    showToast(msg);
                }
            });


//            Call<CheckSignModel> call = HttpAdapter.getService().addResourceComment(data.getResource_id(), user_id, comment);
//            call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
//                @Override
//                protected void onSuccess(CheckSignModel checkSignModel) {
//                    if (checkSignModel.getCode() == 1) {
//
//                    } else if (checkSignModel.getCode() == 400) {
//                        if (null != checkSignModel.getMsg() && checkSignModel.getMsg().equals("已经评论")) {
//                            showToast("您已经评论过了，本评论不显示");
//                            commentContentEt.setText("");
//                            hideInputMethod(CommentActivity.this, commentContentEt);
//                        }else {
//                            showToast(checkSignModel.getMsg());
//                        }
//                    }
//                    LogUtils.D("code--->" + checkSignModel.getCode());
//                }
//            });
        }
    }

//    /**
//     * 隐藏软键盘
//     *
//     * @param context
//     * @param v
//     */
//    public void hideInputMethod(final Context context, final View v) {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void onErrorClick() {
//                LogUtils.D("yingcang");
//                InputMethodManager imm = (InputMethodManager) context
//                        .getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }, 10);
//    }
    @OnClick({R.id.coment_commit_ll,R.id.comment_no_content_tv})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.coment_commit_ll:
                    addCommitContent();
                break;
            case R.id.comment_no_content_tv:


                break;


        }

    }
}
