package com.taoxue.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taoxue.R;


/**
 * 动态刷新和加载数据ListView
 * @author Alpamayo
 *
 */
public class LoadMoreListView extends ListView implements OnScrollListener {
    /**
     * 监听器
     * 监听控件的刷新或者加载更多事件
     * 所有的条目事件都会有一个偏移量，也就是position应该减1才是你适配器中的条目
     * @author RobinTang
     *
     */
    public interface OnLoadMoreListViewListener {
        /**
         * 加载更多
         * @return 数据请求是否成功
         */
        public void onLoadMore();

    }
    /**
     * 状态控件（StatusView，列表头上和底端的）的状态枚举
     * @author RobinTang
     *
     */
    enum RefreshStatus {
        normal, nodata, refreshing
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initThis(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initThis(context);
    }

    public LoadMoreListView(Context context) {
        super(context);
        mContext = context;
        initThis(context);
    }
    Context mContext;
    RefreshStatus refreshStatus = RefreshStatus.nodata;
    LinearLayout footerLayout;
    ProgressBar _f_progressbar;
    TextView _f_textview;

    boolean whetherToClick = false;

    private void initThis(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerLayout = (LinearLayout)inflater.inflate(R.layout.x_list_view_footer, null);
        _f_progressbar = (ProgressBar)footerLayout.findViewById(R.id.x_list_view_footer_progress_bar);
        _f_textview = (TextView)footerLayout.findViewById(R.id.x_list_view_footer_text);
        this.addFooterView(footerLayout, null, false);
        this.footerLayout.setVisibility(GONE);
        this.setOnScrollListener(this);
        this.footerLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(whetherToClick){
                    if(onLoadMoreListViewListener != null){
                        activateMoreRefresh();
                        whetherToClick = false;
                        onLoadMoreListViewListener.onLoadMore();
                    }
                }
            }
        });
    }

    @Override
    public void onScroll(AbsListView l, int t, int oldl, int count) {
        if ((t + oldl) == count){
            //没有更多数据或者正在刷新状态
            if(this.refreshStatus == RefreshStatus.nodata || this.refreshStatus == RefreshStatus.refreshing){
                return;
            }
            //只有在正常状态下才会执行刷新数据
            if(this.refreshStatus == RefreshStatus.normal){
                doMore();
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView arg0, int arg1) {

    }


    private OnLoadMoreListViewListener onLoadMoreListViewListener;


    public void setOnLoadMoreListViewListener(OnLoadMoreListViewListener onLoadMoreListViewListener) {
        this.onLoadMoreListViewListener = onLoadMoreListViewListener;
    }

    /**
     * 开始加载更多
     */
    private void doMore(){
        if(this.onLoadMoreListViewListener != null){
            this.refreshStatus = RefreshStatus.refreshing;
            onLoadMoreListViewListener.onLoadMore();
        }
    }

    /**
     * 加载更多完成之后调用
     */
    public void doneMore() {
        this.refreshStatus = RefreshStatus.normal;
        footerLayout.setVisibility(VISIBLE);
    }

    /**
     * 激活当前自动加载更多功能
     */
    public void activateMoreRefresh(){
        this.refreshStatus = RefreshStatus.normal;
        footerLayout.setVisibility(VISIBLE);
        _f_progressbar.setVisibility(VISIBLE);
        _f_textview.setText("数据加载中...");
    }
    /**
     * 激活当前自动加载更多功能
     */
    public void activateMoreRefresh(String text){
        this.refreshStatus = RefreshStatus.normal;
        footerLayout.setVisibility(VISIBLE);
        _f_progressbar.setVisibility(VISIBLE);
        _f_textview.setText(text);
    }

    /**
     * 激活手动点击加载更多功能
     */
    public void activateClickMoreRefresh(String text){
        this.whetherToClick = true;
        this.refreshStatus = RefreshStatus.nodata;
        footerLayout.setVisibility(VISIBLE);
        _f_progressbar.setVisibility(GONE);
        _f_textview.setText(text);
    }
    /**
     * 无更多数据
     * @param text
     */
    public void noMoreData(String text) {
        this.refreshStatus = RefreshStatus.nodata;
        _f_progressbar.setVisibility(GONE);
        _f_textview.setText(text);
    }

    /**
     * 无更多数据
     */
    public void noMoreData() {
        this.refreshStatus = RefreshStatus.nodata;
        _f_progressbar.setVisibility(GONE);
        _f_textview.setText(mContext.getResources().getString(R.string.x_list_view_footer_text_no_data_default_text));
    }
}
