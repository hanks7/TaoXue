package com.taoxue.ui.module.classification.vpFragment;

        import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilTools;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by User on 2017/6/7.
 */

public abstract class BaseVpFragment<T> extends Fragment {

    protected View mainView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            getActiArguments(getArguments().getSerializable(getKey1()));
        }
    }
    protected abstract void getActiArguments(Serializable arguments);


    public void launch(Class clazz,Object obj){
        Bundle bundle=new Bundle();
        if (obj instanceof Serializable) {
            bundle.putSerializable(getKey1(clazz),(Serializable) obj);
        }else{
            newThrow();
        }
        UtilIntent.intentDIYLeftToRight(getActivity(),clazz,bundle);
    }
    private void newThrow(){
        throw  new IllegalArgumentException("argument must is String Or Serializable," +
                "if argument is int or float or double or boolean or double or byte " +
                "you can cast argument to  String");
    }

    public String getKey1(Class clazz){
        return UtilTools.getActivityKey1(clazz);
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayout() <= 0) {
            mainView = createMainView();
        } else {
            mainView = inflater.inflate(getLayout(), container, false);
        }
        if (mainView == null)
            throw new NullPointerException("hey,you forget set a view for " + getClass().getName());
        ButterKnife.bind(this, mainView);
        return mainView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInit(view);
    }

    /**
     * 数据初始化
     */
    protected abstract void onInit(View view);

    protected View createMainView() {
        return null;
    }

    @LayoutRes
    protected abstract int getLayout();


    public void setArgumentsObj(Object obj) {
        Bundle bundle = new Bundle();
        if (obj instanceof Serializable) {
            bundle.putSerializable(getKey1(), (Serializable) obj);
        }else{
            throw  new IllegalArgumentException("argument must is String Or Serializable," +
                    "if argument is int or float or double or boolean or double or byte " +
                    "you can cast argument to  String");
        }
        setArguments(bundle);
    }


    public String getKey1(){
        return UtilTools.getActivityKey1(getClass());
    };


}
