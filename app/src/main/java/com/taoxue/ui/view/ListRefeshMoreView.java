package com.taoxue.ui.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.taoxue.R;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.module.search.BaseMyAdapter;
import com.taoxue.ui.view.refresh.MyRefreshLayout;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.taoxue.R.id.empty_view;

/**
 * Created by hanks7 on 2018/2/25.
 */

public class ListRefeshMoreView<Data extends Serializable> extends LinearLayout implements LoadMoreListView.OnLoadMoreListViewListener {
    @BindView(R.id.listView)
    LoadMoreListView lsv;

    @BindView(R.id.refreshLayout)
    MyRefreshLayout refreshLayout;

    @BindView(empty_view)
    EmptyView mEmptyView;

    private int page = 1;
    private boolean isHasMore;//是否还有更多
    private BaseMyAdapter adapter;

    NetworkInterface mListener;

    public ListRefeshMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListRefeshMoreView(Context context) {
        this(context, null);
    }

    public ListRefeshMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_list_more_view, this);
        ButterKnife.bind(this, view);
    }




    /**
     * 初始化下拉刷新控件
     */
    public void initListView(BaseMyAdapter adapter, final NetworkInterface listener) {
        this.adapter = adapter;
        this.mListener = listener;

        lsv.setOnLoadMoreListViewListener(this);
        lsv.setAdapter(adapter);
        lsv.activateMoreRefresh();

        refreshLayout.disableWhenHorizontalMove(true);
        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, lsv, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                lsv.activateMoreRefresh();
                page = 1;
                mListener.http(page);
            }
        });
        lsv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onItemClick(parent, view, position, id);
            }
        });

        page = 1;
        mListener.http(page);
    }

    boolean success;

    /**
     * 网络请求正确的情况下的业务
     * @param pageModel
     */
    public void onSuccess(BasePageModel<Data> pageModel) {
        refreshLayout.refreshComplete();//停止刷新
        mEmptyView.setHasdata();//错误提示页消失
        List<Data> contentList = pageModel.getPage().getResult();
        isHasMore = pageModel.getPage().isHasNext();
        if (isHasMore) {
            lsv.doneMore();
        } else {
            lsv.noMoreData("没有更多了");
        }
        if (page > 1) {
            adapter.addAllData(contentList);
        } else {
            if (contentList.size() == 0) {
                mEmptyView.setNodata();
            }else if(!success){
                success=true;
            }
            adapter.updateList(contentList);
        }
    }

    /**
     * 网络请求错误的情况下
     */
    public void onFailure() {
        refreshLayout.refreshComplete();
        lsv.noMoreData();

        if (page > 1) {
            page--;
        }

        if (!success) {//页面上还没有数据并且是网络错误
            mEmptyView.setErrordata(new Runnable() {
                @Override
                public void run() {
                    page = 1;
                    mListener.http(1);
                }
            });
        }



    }

    /**
     * 上拉加载更多
     * 有更多才操作
     */
    @Override
    public void onLoadMore() {
        if (isHasMore) {
            page++;
            mListener.http(page);
        }
    }

    /**
     * 对外接口
     */
    public interface NetworkInterface {
        void http(int page);//网络请求
        void onItemClick(AdapterView<?> parent, View view, int position, long id);//listview的item的onclick方法
    }

    /**
     * 设置有数据
     */
    public void setErrorPic(int mipmapID, String str) {
        mEmptyView.setErrorPic(mipmapID, str);
    }

    /**
     * 自动更新
     */
    public void setAutoRefresh() {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshLayout.autoRefresh();
            }
        });
    }


}


