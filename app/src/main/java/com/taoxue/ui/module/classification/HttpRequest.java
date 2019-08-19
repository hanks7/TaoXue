package com.taoxue.ui.module.classification;

import android.widget.ImageView;

import com.taoxue.R;
import com.taoxue.app.DialogInterface;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.model.ReaderCodeModel;
import com.taoxue.ui.model.ResourceLibModel;
import com.taoxue.ui.model.ResourcePraiseBean;
import com.taoxue.ui.model.StateBean;
import com.taoxue.ui.model.UserModel;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilToast;

import java.util.List;

import retrofit2.Call;

/**
 * Created by User on 2017/6/9.
 */

public class HttpRequest {
    //点击后收藏功能
    public static void addOrCancelCollection(String resource_id, String gys_id, final ImageView iv, final StateCallBack callBack) {
        Call<BaseResultModel<StateBean>> call = HttpAdapter.getService().addOrCancelCollection(resource_id, gys_id);
        call.enqueue(new OnResponseNoDialogListener<BaseResultModel<StateBean>>() {
            @Override
            protected void onSuccess(BaseResultModel<StateBean> model) {
                if (model.getCode() == 1) {
                    if (model.getData().getFlag_code().equals("1")) {//收藏成功
                        iv.setImageResource(R.mipmap.icon_collection_true);
                    } else if (model.getData().getFlag_code().equals("0")) {//收藏失败
                        iv.setImageResource(R.mipmap.uncollection);
                    }
                    callBack.onSuccess(model.getData());
                } else {
                    UtilToast.showText(model.getMsg());
                }
            }
        });
    }

    //点击后点赞
    public static void giveThumbs(String resource_id, String gys_id, final ImageView iv, final StateCallBack callBack) {
        HttpAdapter.getService().giveThumb(resource_id, gys_id).enqueue(new OnResponseNoDialogListener<BaseResultModel<StateBean>>() {
            @Override
            protected void onSuccess(BaseResultModel<StateBean> model) {
                if (model.getCode() == 1) {
                    if (model.getData().getFlag_code().equals("1")) {//点赞成功
                        iv.setImageResource(R.mipmap.icon_praise_true);
                    } else if (model.getData().getFlag_code().equals("0")) {//取消点赞
                        iv.setImageResource(R.mipmap.give_thumb);
                    }
                    callBack.onSuccess(model.getData());
                } else {
                    UtilToast.showText(model.getMsg());
                }
            }
        });
    }

    /**
     * 点赞
     *
     * @param user_id
     * @param resource_id
     * @param gys_id
     * @param iv
     */
    public static void giveThumbs(String user_id, String resource_id, String gys_id, final ImageView iv) {
        HttpAdapter.getService().giveThumb2(resource_id, user_id, gys_id).enqueue(new OnResponseNoDialogListener<BaseResultModel<ResourcePraiseBean>>() {
            @Override
            protected void onSuccess(BaseResultModel<ResourcePraiseBean> model) {
                switch (model.getData().getFlag_code()) {
                    case 1:
                        iv.setImageResource(R.mipmap.icon_praise_true);
                        break;
                    case 0:
                        iv.setImageResource(R.mipmap.icon_praise_false);
                        break;
                    default:
                        UtilToast.showText(model.getMsg());
                        break;
                }
            }
        });
    }

    /**
     * 收藏
     *
     * @param user_id
     * @param resource_id
     * @param gys_id
     * @param iv
     */
    public static void addCollection(String user_id, String resource_id, String gys_id, final ImageView iv) {
        HttpAdapter.getService().addCollection2(user_id, resource_id, gys_id).enqueue(new OnResponseNoDialogListener<BaseResultModel<ResourcePraiseBean>>() {
            @Override
            protected void onSuccess(BaseResultModel<ResourcePraiseBean> model) {
                switch (model.getData().getFlag_code()) {
                    case 1:
                        iv.setImageResource(R.mipmap.icon_collection_true);
                        break;
                    case 0:
                        iv.setImageResource(R.mipmap.icon_collection_false);
                        break;
                    default:
                        UtilToast.showText(model.getMsg());
                        break;
                }
            }
        });
    }


    //添加评论内容

    public static void addCommitContent(String resource_id, String comment, String score, final RequestCallBack callBack) {
        Call<CheckSignModel> call = HttpAdapter.getService().addResourceComment(resource_id, comment, score, "0");
        call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
            @Override
            protected void onSuccess(CheckSignModel checkSignModel) {
                if (checkSignModel.getCode() == 1) {
                    callBack.onSuccess(checkSignModel.getMsg());
                } else if (checkSignModel.getCode() == 400) {
                    if (null != checkSignModel.getMsg() && checkSignModel.getMsg().equals("已经评论")) {
                        callBack.onRequested(checkSignModel.getMsg());
                    } else {
                        callBack.onRequested(checkSignModel.getMsg());
                    }
                }
            }
        });
    }

    public interface RequestSuccessCallBack {
        void onSuccess(String msg);//表示请求成功
    }

    public interface StateCallBack {
        void onSuccess(StateBean msg);//表示请求成功
    }


    public interface RequestCallBack {
        void onSuccess(String msg);//表示请求成功

        void onRequested(String msg); //表示已经请求了

        void onUnSuccess(String msg);//表示评论失败

    }

    public interface CheckResultCallBack {
        void onSuccess();//表示请求成功
    }

    public interface RequestResultCallBack {
        void onSuccess(String msg, BaseModel baseModel);//表示请求成功

        void onFailure(String msg);//表示失败
    }

    public interface RequestMyLibCallBack {
        void onSuccess(List<MyLibInfo> myLibInfos);//表示请求成功

        void onUnSuccess(String msg);//表示失败
    }

    //查询我的图书馆信息
    public static void queryMyLibInfo(String user_id, final RequestMyLibCallBack callBack) {
        Call<BaseListModel<MyLibInfo>> call = HttpAdapter.getService().queryMyLibInfo(user_id);
        call.enqueue(new OnResponseNoDialogListener<BaseListModel<MyLibInfo>>() {
            @Override
            protected void onSuccess(BaseListModel<MyLibInfo> myLibInfoBaseListModel) {
                if (myLibInfoBaseListModel.getCode() == 1 && null != myLibInfoBaseListModel.getItem() && !"[]".equals(myLibInfoBaseListModel.getItem() + "") && myLibInfoBaseListModel.getItem().size() > 0) {
                    callBack.onSuccess(myLibInfoBaseListModel.getItem());
                } else {
                    callBack.onUnSuccess(myLibInfoBaseListModel.getMsg());
                }
            }
        });
    }

    //查询当前资源支付信息
    public static void queryReadCardPayInfo(String user_id, String resource_id, final RequestMyLibCallBack callBack) {
        Call<BaseListModel<MyLibInfo>> call = HttpAdapter.getService().queryReaderCardPayInfo(resource_id, user_id);
        call.enqueue(new OnResponseNoDialogListener<BaseListModel<MyLibInfo>>() {
            @Override
            protected void onSuccess(BaseListModel<MyLibInfo> myLibInfoBaseListModel) {
                if (myLibInfoBaseListModel.getCode() == 1 && null != myLibInfoBaseListModel.getItem() && !"[]".equals(myLibInfoBaseListModel.getItem() + "") && myLibInfoBaseListModel.getItem().size() > 0) {
                    callBack.onSuccess(myLibInfoBaseListModel.getItem());
                } else {
                    callBack.onUnSuccess(myLibInfoBaseListModel.getMsg());
                }
            }
        });
    }


    public interface RequestBaseModelCallBack {
        void onSuccess(BaseModel baseModel);//表示请求成功

        void onUnSuccess(String msg);//表示失败
    }

    //发送手机验证码
    public static void sendMobileCode(String phone, DialogInterface context, final RequestResultCallBack callBack) {
        Call<CheckSignModel> call = HttpAdapter.getService().sendvalidate(phone);
        call.enqueue(new OnResponseListener<CheckSignModel>(context) {
            @Override
            protected void onSuccess(CheckSignModel baseResultModel) {
                if (baseResultModel.getCode() == 1) {
                    callBack.onSuccess("", baseResultModel);
                } else {
                    callBack.onFailure(baseResultModel.getMsg());
                }
            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onFailure(msg);
            }


        });


    }

    //查询资源库详情
    public static void queryResourceLibDetail(BaseActivity context, final RequestBaseModelCallBack callBack) {
        Call<BaseResultModel<ResourceLibModel>> call = HttpAdapter.getService().searchResourceDeatail();
        call.enqueue(new OnResponseListener<BaseResultModel<ResourceLibModel>>(context) {
            @Override
            protected void onSuccess(BaseResultModel<ResourceLibModel> model) {
                if (model.getCode() == 1) {
                    callBack.onSuccess(model.getData());
                } else {
                    callBack.onUnSuccess(model.getMsg());
                }
            }
        });

    }


    public static void checkWeixinLogin(final String oppenid, final String access_token, final RequestBaseModelCallBack callBack) {
        final Call<BaseResultModel<UserModel>> call = HttpAdapter.getService().weixinCheckLogin(oppenid, "weixin");
        call.enqueue(new OnResponseNoDialogListener<BaseResultModel<UserModel>>() {
            @Override
            protected void onSuccess(BaseResultModel<UserModel> userModelBaseResultModel) {
                if (userModelBaseResultModel.getCode() == 1) {//已登录过
                    callBack.onSuccess(userModelBaseResultModel.getData());
                } else {//未登录过，从微信中获取用户信息
                    LogUtils.D("access_token---->" + access_token);
                    callBack.onUnSuccess(userModelBaseResultModel.getMsg());
                }
            }

            @Override
            protected void onFailure(String msg) {
                LogUtils.D("huqucuowu");
                callBack.onUnSuccess(msg);
            }
        });
    }

    //检查验证码接口
    public static void UserRegister(String mobile, String pw, final RequestBaseModelCallBack callBack) {
        HttpAdapter.getService().saveRegister(mobile, pw).enqueue(new OnResponseNoDialogListener<BaseResultModel<UserModel>>() {
            @Override
            protected void onSuccess(BaseResultModel<UserModel> checkSignModel) {
                if (checkSignModel.getCode() == 1) {
                    callBack.onSuccess(checkSignModel.getData());
                } else {
                    UtilToast.showText(checkSignModel.getMsg());
                }
            }
        });

    }


    //检查验证码接口
    public static void checkMESCode(String mobile, String code, final CheckResultCallBack callBack) {
        HttpAdapter.getService().checkSMSCode(mobile, code).enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
            @Override
            protected void onSuccess(CheckSignModel checkSignModel) {
                if (checkSignModel.getCode() == 1) {
                    callBack.onSuccess();
                } else {
                    UtilToast.showText(checkSignModel.getMsg());
                }
            }
        });

    }

    /**
     * 绑定读者证的信息
     *
     * @param
     */
    public static void initReaderCodeByUser(BaseActivity context, String reader_id, String reader_name) {
        Call<BaseResultModel<ReaderCodeModel>> call = HttpAdapter.getService().testReaderCode(reader_id, reader_name);
        call.enqueue(new OnResponseListener<BaseResultModel<ReaderCodeModel>>(context) {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {


            }
        });
    }
}
