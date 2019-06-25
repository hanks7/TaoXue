package com.taoxue.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.taoxue.R;

/**
 * Created by User on 2017/4/19.
 */

public class ListViewFootView extends LinearLayout {

    private final FootHolder footHolder;

    public ListViewFootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewFootView(Context context) {
        this(context, null);
    }

    public ListViewFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.footview, this);
        footHolder=   new FootHolder(view);
    }

    public FootHolder getFootHolder(){
        return footHolder;
    }
}
