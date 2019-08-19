package com.taoxue.ui.module.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.common.base.basecommon.BaseAdapter.Base.RvViewHolder;
import com.common.base.basecommon.BaseAdapter.RvComAdapter;
import com.common.base.basecommon.BaseAdapter.listener.InitViewCallBack;
import com.common.base.basecommon.BaseAdapter.listener.OnItemAdapterClickListener;
import com.danxx.mdplayer.mdplayer.MDPlayer;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.adapter.LsvPlayRcommandAdapter;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.VideoBean;
import com.taoxue.ui.model.VolumnBean;
import com.taoxue.ui.module.search.BaseMyAdapter;
import com.taoxue.ui.module.yuejia.ResourceDetailActivity;
import com.taoxue.ui.view.StarBar;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilGson;
import com.taoxue.utils.UtilToast;
import com.taoxue.utils.UtilTools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taoxue.R.id.tv_content;

/**
 * Created by User on 2017/3/31.
 */

public class PlayActivity extends BaseActivity implements MDPlayer.OnNetChangeListener {

    @BindView(R.id.view_super_player)
    MDPlayer player;
    @BindView(R.id.full_screen)
    FrameLayout fullScreen;//当视频窗口全屏的时候videoview的容器

    @BindView(R.id.tv_super_player_complete)
    TextView tvSuperPlayerComplete;
    @BindView(R.id.ac_play_tv_resource_name)
    TextView mTvResourceName;
    @BindView(R.id.ac_play_pingfen_coun_tv)
    TextView mTvPinFen;
    @BindView(R.id.ac_play_star_bar)
    StarBar mStarBar;
    @BindView(R.id.video_screen)
    FrameLayout videoScreen;
    @BindView(R.id.ac_play_tv_title)
    TextView acPlayTvTitle;
    @BindView(R.id.ac_play_tv_comments)
    TextView acPlayTvComments;
    @BindView(R.id.ac_play_mine_recycleView)
    RecyclerView mineRecycleView;
    @BindView(R.id.ac_play_img_collet)
    ImageView mImgcollet;
    @BindView(R.id.ac_play_img_good)
    ImageView mImgGood;
    @BindView(R.id.ac_play_bottom_bar)
    com.taoxue.ui.view.PlayBottomView mPlayBottomView;
    @BindView(R.id.ac_play_RecommendForYouBean)
    ListView mLsvRFY;


    RvComAdapter rvCommonAdapter;
    String resource_id;
    String gys_id;
    String cgs_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_play);
        ButterKnife.bind(this);

        network();//得到intent中的数据
    }

    /**
     * 得到intent中的数据
     *
     * @return
     */
    private void network() {
        Bundle bundle = getIntent().getExtras();
        cgs_id = bundle.getString("cys_id");
        resource_id = bundle.getString("resource_id");
        gys_id = bundle.getString("gys_id");
        HttpAdapter.getService().getVideo(resource_id, cgs_id).enqueue(new OnResponseListener<BaseResultModel<VideoBean>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<VideoBean> model) {
                setData(model.getData());
                mPlayBottomView.setResource_id(resource_id, gys_id);
            }

            @Override
            protected void onError(int code, String msg) {
                VideoBean bean = UtilGson.getJson(mActivity, "VideoBean.json", VideoBean.class);
                setData(bean);
                mPlayBottomView.setResource_id(resource_id, gys_id);
            }
        });

    }

    private void setData(final VideoBean bean) {

//        //得到本地json文本内容
//        String playBean = UtilGson.getJson(mActivity, "playBean.json");
//        //转换为对象
//        final PlayActivityBean bean = UtilGson.JsonToObject(playBean, PlayActivityBean.class);

        mTvResourceName.setText(bean.getResource_name());
        mStarBar.setStarMark(Float.valueOf(bean.getAvg_score()));
        mStarBar.setChangMark(false);
        mTvPinFen.setText(bean.getAvg_score() + "");

        mPlayBottomView.setCollect(Integer.valueOf(bean.getIs_collection()));
        mPlayBottomView.setDianZan(Integer.valueOf(bean.getIs_praise()));
        if (bean.getItem().size() == 0) {
            UtilToast.showText("该资源没有视频");

        } else {
            setAdapter(bean.getItem().get(0).getVolumn());//横向 listview adapter 赋值
            initVideoView(bean.getItem().get(0).getVolumn().get(0));//播放第一个视频
        }
        BaseMyAdapter adapter = new LsvPlayRcommandAdapter(this, bean.getRecommend_forYou());
        mLsvRFY.setAdapter(adapter);
        mLsvRFY.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launch(ResourceDetailActivity.class, bean.getRecommend_forYou().get(position).getResource_id());
            }
        });
    }


    /**
     * 初始化数据
     */
    private void initData(ResourceModel bean) {
//        this.bean = bean;
        acPlayTvTitle.setText(Html.fromHtml(bean.getCatalog()));
        acPlayTvComments.setText("评论" + "(" + bean.getComment_num() + "条)");
        if (bean.getIs_collection().equals("1"))
            mImgcollet.setImageResource(R.mipmap.icon_collection_true);
        if (bean.getIs_praise().equals("1")) mImgGood.setImageResource(R.mipmap.icon_praise_true);
    }

    /**
     * 初始化播放器
     *
     * @param bean
     */
    private void initVideoView(VolumnBean bean) {

        initPlayer(bean.getResource_name(), UtilTools.getProxyUrl(bean.getUrl()));
    }

    @OnClick({
            R.id.ac_play_ll_comment,
            R.id.ac_play_img_good,
            R.id.ac_play_img_collet
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_play_ll_comment://评论
//                launch(CommentActivity.class, bean);
                break;
            case R.id.ac_play_img_good://点赞
//                HttpRequest.giveThumbs(user_id, resource_id, bean.getGys_id(), mImgGood);
                break;
            case R.id.ac_play_img_collet://收藏
//                HttpRequest.addCollection(user_id, resource_id, bean.getGys_id(), mImgcollet);
                break;
        }
    }

    private int playIndex = 0;

    /**
     * 横向方向的adapter
     */
    private void setAdapter(final List<VolumnBean> list) {
        rvCommonAdapter = new RvComAdapter.
                Builder<>(this, R.layout.item_ac_play_list_ji_shu, list)
                .setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
                    @Override
                    public void onItemClick(View view, RvViewHolder viewHolder, int i, int i1) {
                        playNext(list.get(i));
                        playIndex = i;

                    }
                })

                .setOrientation(OrientationHelper.HORIZONTAL)
                .into(mineRecycleView, new InitViewCallBack<VolumnBean>() {
                    @Override
                    public void convert(RvViewHolder holder, VolumnBean bean, int position) {
                        TextView tvContent = (TextView) holder.getView(tv_content);
                        if (position == playIndex) {
                            tvContent.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_ring_true));
                            tvContent.setTextColor(getResources().getColor(R.color.white));
                        } else {
                            tvContent.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_ring));
                            tvContent.setTextColor(getResources().getColor(R.color.gray));
                        }
                        holder.setText(tv_content, bean.getOrder() + "");
                    }
                });
    }

    /**
     * 播放下一个
     *
     * @param bean
     */
    private void playNext(VolumnBean bean) {
        if (player != null) {
            player.onDestroy();
        }
        initVideoView(bean);
    }

    /**
     * 初始化播放器
     */
    private void initPlayer(String title, String url) {
        LogUtils.i("initPlayer", "title " + title + "  url");
        rvCommonAdapter.notifyDataSetChanged();
        if (false) {
            player.setLive(true);//设置该地址是直播的地址
        }
        player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onPrepared(new MDPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                }).onComplete(new Runnable() {
            @Override
            public void run() {
                /**
                 * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                 */
            }
        }).onInfo(new MDPlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                /**
                 * 监听视频的相关信息。
                 */

            }
        }).onError(new MDPlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                /**
                 * 监听视频播放失败的回调
                 */

            }
        }).setTitle(title)//设置视频的titleName
                .play(url);//开始播放视频
        player.setScaleType(MDPlayer.SCALETYPE_FITXY);
    }


    @OnClick(value = {})
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onWifi() {
//        showToast("当前网络环境是WIFI");

    }

    @Override
    public void onMobile() {
        showToast("当前网络环境是手机网络");
        player.pause();
    }

    @Override
    public void onDisConnect() {
        showToast("网络链接断开");
        player.pause();
    }

    @Override
    public void onNoAvailable() {
        showToast("无网络链接");
        player.pause();
    }

    @Override
    public void onBackPressed() {
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            /**
             * 在activity中监听到横竖屏变化时调用播放器的监听方法来实现播放器大小切换
             */
            player.onConfigurationChanged(newConfig);
            // 切换为小屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fullScreen.setVisibility(View.GONE);
                fullScreen.removeAllViews();
                FrameLayout frameLayout = (FrameLayout) findViewById(com.danxx.mdplayer.R.id.video_screen);
                frameLayout.removeAllViews();
                ViewGroup last = (ViewGroup) player.getParent();//找到videoitemview的父类，然后remove
                if (last != null) {
                    last.removeView(player);
                }
                frameLayout.addView(player);
                int mShowFlags =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                fullScreen.setSystemUiVisibility(mShowFlags);
            } else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
                //切换为全屏
                ViewGroup viewGroup = (ViewGroup) player.getParent();
                if (viewGroup == null)
                    return;
                viewGroup.removeAllViews();
                fullScreen.addView(player);
                fullScreen.setVisibility(View.VISIBLE);
                int mHideFlags =
                        View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                fullScreen.setSystemUiVisibility(mHideFlags);
            }
        } else {
            fullScreen.setVisibility(View.GONE);
        }
    }


}
