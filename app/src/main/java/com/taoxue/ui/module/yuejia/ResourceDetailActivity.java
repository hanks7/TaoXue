package com.taoxue.ui.module.yuejia;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.AudioResourceBean;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.ReadInfoBean;
import com.taoxue.ui.model.ResDetailBean;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.ui.module.classification.AudioPlayActivity;
import com.taoxue.ui.module.classification.CommentActivity;
import com.taoxue.ui.module.home.PlayActivity;
import com.taoxue.ui.view.PlayBottomView;
import com.taoxue.ui.view.StarBar;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.UtilGson;
import com.taoxue.utils.glide.UtilGlide;
import com.txt.readerlibrary.TxtReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResourceDetailActivity extends BaseActivity {
    //    http://117.71.57.47:11000/frontend/mt/h5/20/resource/getDetail.do?resource_id=402883d26033f002016034136f6b001c
    String resource_id;
    @BindView(R.id.introduction_topbar)
    TopBar mTopbar;
    @BindView(R.id.iv_resource_pic)
    ImageView ivResourcePic;
    @BindView(R.id.tv_resource_name)
    TextView tvResourceName;
    @BindView(R.id.tv_resource_jiage)
    TextView tvResourceJiage;
    @BindView(R.id.xiangqing)
    TextView xiangqing;
    @BindView(R.id.tv_resource_author)
    TextView tvResourceAuthor;
    @BindView(R.id.tv_resource_pushtion)
    TextView tvResourcePushtion;
    @BindView(R.id.tv_resource_jianjie)
    TextView tvResourceJianjie;
    @BindView(R.id.pinglun)
    TextView pinglun;
    @BindView(R.id.iv_irrow_right)
    ImageView ivIrrowRight;
    @BindView(R.id.rv_pinglun_info)
    RecyclerView rvPinglunInfo;
    @BindView(R.id.tuijian)
    TextView tuijian;
    @BindView(R.id.rv_tuijian_info)
    RecyclerView rvTuijianInfo;
    @BindView(R.id.startRead)
    TextView startRead;

    @BindView(R.id.ac_play_bottom_bar)
    PlayBottomView mPlayBottomView;
    @BindView(R.id.iv_small)
    ImageView ivSmall;
    @BindView(R.id.pinglun_all)
    TextView pinglunAll;
    @BindView(R.id.introduction_scrollview)
    ScrollView mSv;

    Context context;
    ResDetailBean bean;
    String cgs_id = "";
    String gys_id = "";


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_detail2);
        ButterKnife.bind(this);
        resource_id = (String) getIntentKey1();
        cgs_id = (String) getIntentKey2();
        context = this;
//       resource_id="402883d26033f002016034136f6b001c";//测试时使用
//        resource_id="402883d2615a382c01615a3b662d0002";
        httpRequest();

        onClickdo();

        mSv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {

                mTopbar.setNeedTranslucent(getTransAlpha());

            }
        });


    }
    //渐变开始位置
    private int transStartY = 50;
    //渐变结束位置
    private int transEndY = 400;
    /**
     * 获取透明度
     *
     * @return
     */
    private int getTransAlpha() {
        float scrollY = mSv.getScrollY();
        if (transStartY != 0) {
            if (scrollY <= transStartY) {
                return 0;
            } else if (scrollY >= transEndY) {
                return 255;
            } else {
                return (int) ((scrollY - transStartY) / (transEndY - transStartY) * 255);
            }
        } else {
            if (scrollY >= transEndY) {
                return 255;
            }
            return (int) ((transEndY - scrollY) / transEndY * 255);
        }
    }

    //获取  txt
    public void onHttp() {
        HttpAdapter.getService().getResources(resource_id, cgs_id).enqueue(new OnResponseListener<BaseResultModel<ReadInfoBean>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<ReadInfoBean> txtReadBeanBaseListModel) {
                if (txtReadBeanBaseListModel.getData().getItem() != null && txtReadBeanBaseListModel.getData().getItem().size() > 0) {
                    if (txtReadBeanBaseListModel.getData().getItem().get(0).getVolumn() != null && txtReadBeanBaseListModel.getData().getItem().get(0).getVolumn().size() > 0) {
                        String url = txtReadBeanBaseListModel.getData().getItem().get(0).getVolumn().get(0).getUrl();
                        TxtReader.newInstance(ResourceDetailActivity.this).openBookByUrl(url, ResourceDetailActivity.this, txtReadBeanBaseListModel.getData().getResource_name());
                    }
                }
            }
        });

    }

    public void onAudioHttp() {
//        resource_id="402883d2615a382c01615a7c8dbe0158";
        HttpAdapter.getService().getResources(resource_id, cgs_id).enqueue(new OnResponseListener<BaseResultModel<ReadInfoBean>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<ReadInfoBean> model) {
                if (model.getData().getItem() != null && model.getData().getItem().size() > 0) {
                    if (model.getData().getItem().get(0).getFile_type().equals("audio")) {
                        if (model.getData().getItem().get(0).getVolumn().size() > 0) {
                            UrlPath up = new UrlPath();
                            up.setUrlModel(model.getData().getItem());
                            up.setIs_collection(model.getData().getIs_collection());
                            up.setIs_praise(model.getData().getIs_praise());
                            up.setIs_collection(model.getData().getIs_collection());
                            AudioResourceBean res = new AudioResourceBean();
                            res.setResource_id(resource_id);
                            res.setResource_name(bean.getResource_name());
                            res.setResource_picture(bean.getResource_picture());
                            res.setGys_id(bean.getGys_id());
//                           showToast("陈宫");
                            launch(AudioPlayActivity.class, up, res);
                        }
                    }


                }
            }
        });

    }

    public void onImageHttp() {
        HttpAdapter.getService().getResources(resource_id, cgs_id).enqueue(new OnResponseListener<BaseResultModel<ReadInfoBean>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<ReadInfoBean> imageBeanBaseListModel) {
                if (imageBeanBaseListModel.getData().getItem() != null && imageBeanBaseListModel.getData().getItem().size() > 0) {
                    ReadInfoBean infoBean = imageBeanBaseListModel.getData();
                    infoBean.setResource_id(resource_id);
                    infoBean.setGys_id(gys_id);
                    launch(ScanImageActivity.class, infoBean);

                } else {
                    showToast("没有资源");
                }
            }

            @Override
            protected void onError(int code, String msg) {
                ReadInfoBean bean = UtilGson.getJson(mActivity, "ReadInfoBean.json", ReadInfoBean.class);

                if (bean.getItem() != null && bean.getItem().size() > 0) {
                    ReadInfoBean infoBean = bean;
                    infoBean.setResource_id(resource_id);
                    infoBean.setGys_id(gys_id);
                    launch(ScanImageActivity.class, infoBean);

                } else {
                    showToast("没有资源");
                }

            }
        });

    }


    String fileType;

    public void onClickdo() {
        startRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("resource_id", resource_id);
                bundle.putString("cys_id", cgs_id);
                bundle.putString("gys_id", gys_id);
                intent.putExtras(bundle);
                launch(PlayActivity.class, intent);

//                for (int i = 0; i < bean.getFile_type().size(); i++) {
//                    fileType += bean.getFile_type().get(i);
//                }
//                if (fileType.contains("电子书")) {
//                    onHttp();
//                } else if (fileType.contains("绘本")) {
//                    onImageHttp();
//                } else if (fileType.contains("知识解读")) {
//                    onAudioHttp();
//                } else if (fileType.contains("视频")) {
//                    Intent intent = new Intent();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("resource_id", resource_id);
//                    bundle.putString("cys_id", cgs_id);
//                    bundle.putString("gys_id", gys_id);
//                    intent.putExtras(bundle);
//                    launch(PlayActivity.class, intent);
//                }
            }
        });

        pinglunAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioResourceBean audioResourceBean = new AudioResourceBean();
                audioResourceBean.setGys_id(bean.getGys_id());
                audioResourceBean.setResource_id(bean.getResource_id());
                audioResourceBean.setResource_picture(bean.getResource_picture());
                launch(CommentActivity.class, audioResourceBean);
            }
        });

    }

    public void httpRequest() {

        if (resource_id != "") {
            HttpAdapter.getService().getResourceDetails(resource_id).enqueue(new OnResponseListener<BaseResultModel<ResDetailBean>>(this) {
                @Override
                protected void onSuccess(BaseResultModel<ResDetailBean> model) {
                    if (!(model.getData() + "").equals("{}")) {
                        gys_id = model.getData().getGys_id();
                        onInitView(model.getData());
                        mPlayBottomView.setResource_id(resource_id, model.getData().getGys_id());
                        mPlayBottomView.setDianZan(Integer.valueOf(model.getData().getIs_praise()));
                        mPlayBottomView.setCollect(Integer.valueOf(model.getData().getIs_collection()));

                    }
                }

                @Override
                protected void onError(int code, String msg) {
                    ResDetailBean bean = UtilGson.getJson(mActivity, "ResDetailBean.json", ResDetailBean.class);
                    gys_id = bean.getGys_id();
                    onInitView(bean);
                    mPlayBottomView.setResource_id(resource_id, bean.getGys_id());
                    mPlayBottomView.setDianZan(Integer.valueOf(bean.getIs_praise()));
                    mPlayBottomView.setCollect(Integer.valueOf(bean.getIs_collection()));


                }
            });
        }
    }

    public void onInitView(final ResDetailBean bean) {
        this.bean = bean;
        tvResourceName.setText(bean.getResource_name());
        tvResourceAuthor.setText("作者:" + nullToSting(bean.getAuthor()));
        tvResourceJianjie.setText("简介:" + nullToSting(bean.getDescription()));
        tvResourceJiage.setText("￥" + bean.getCharge_value() + bean.getCharge_unit());
        tvResourcePushtion.setText("出版社:" + bean.getPublisher());
        pinglun.setText("评论(" + bean.getComment_num() + ")");
        UtilGlide.loadImgCeterCrop(context, bean.getResource_picture(), ivSmall);
        UtilGlide.showImageViewBlur(context, R.mipmap.imagedaiti, bean.getResource_picture(), ivResourcePic);
        if (bean.getComment().size() > 0) {
            RvComAdapter rvComAdapter = new RvComAdapter.Builder<>(this, R.layout.comment_item, bean.getComment())
                    .setLayoutManagerType(RvComAdapter.LINEARLAYOUTMANAGER)
                    .into(rvPinglunInfo, new InitViewCallBack<ResDetailBean.CommentBean>() {
                        @Override
                        public void convert(RvViewHolder rvViewHolder, ResDetailBean.CommentBean commentBean, int i) {
                            UtilGlide.loadImgForIvHead(context, commentBean.getPhoto(), (ImageView) rvViewHolder.getView(R.id.iv_pinglun_head));
                            ((StarBar) rvViewHolder.getView(R.id.pinfen_starbar)).setStarMark(Float.parseFloat(commentBean.getScore()));
                            rvViewHolder.setText(R.id.tv_pinglun_user_name, nullToSting(commentBean.getName()));
                            rvViewHolder.setText(R.id.tv_pinglun_date, commentBean.getAddtime());
                            rvViewHolder.setText(R.id.tv_pinglun_info, commentBean.getContent());
                        }
                    });
        }
        rvPinglunInfo.setNestedScrollingEnabled(false);//禁止内部滑动
        rvTuijianInfo.setNestedScrollingEnabled(false);
        if (bean.getRecommend().size() > 0) {
            RvComAdapter rvComAdapter = new RvComAdapter.Builder<>(context, R.layout.book_info_item_rv_item, bean.getRecommend())
                    .setLayoutManagerType(RvComAdapter.LINEARLAYOUTMANAGER)
                    .setOrientation(LinearLayoutManager.VERTICAL)
                    .setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
                        @Override
                        public void onItemClick(View view, RvViewHolder rvViewHolder, int i, int i1) {
                            launch(ResourceDetailActivity.class, view.getTag());
//                            launch(ResourceDetailActivity.class,bean.getRecommend().get(i).getResource_id());
                        }
                    })
                    .into(rvTuijianInfo, new InitViewCallBack<ResDetailBean.RecommendBean>() {
                        @Override
                        public void convert(RvViewHolder holder, ResDetailBean.RecommendBean bookInfoBean, int i) {
                            holder.setText(R.id.tv_book_title, bookInfoBean.getResource_name());
                            holder.setText(R.id.tv_book_author, "作者:" + bookInfoBean.getAuthor());
                            holder.setText(R.id.tv_book_decription, bookInfoBean.getDescription());
                            holder.setText(R.id.tv_book_yuedu_num, "阅读量:" + nullToSting(bookInfoBean.getRead_num() + ""));
                            holder.setText(R.id.tv_book_collction_num, "收藏量:" + nullToSting(bookInfoBean.getCollection_num() + ""));
                            holder.getConvertView().setTag(bookInfoBean.getResource_id());
                            UtilGlide.loadImg(context, bookInfoBean.getResource_picture(), (ImageView) holder.getView(R.id.iv_book_info_pic));
                        }
                    });


        }


    }

}
