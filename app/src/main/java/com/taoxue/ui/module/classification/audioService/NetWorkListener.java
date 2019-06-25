package com.taoxue.ui.module.classification.audioService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/8/14.
 */

public class NetWorkListener {
    private Context context;
    private NetWorkConnectStateListener netWorkStateListener;
    private NetWorkReceiver netWorkReceiver;

    public NetWorkListener(Context context) {
        this.context = context;
        netWorkReceiver = new NetWorkReceiver();
    }

    public void begin(NetWorkConnectStateListener netWorkStateListener) {
        this.netWorkStateListener = netWorkStateListener;
        registerListener();
    }

    public void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(netWorkReceiver, filter);
    }

    /**
     * 停止screen状态监听
     */
    public void unregisterListener() {
        if (netWorkReceiver != null) {
            context.unregisterReceiver(netWorkReceiver);
        }
    }




    private class NetWorkReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
                if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
////
////                //获取所有网络连接的信息
////                Network[] networks = connMgr.getAllNetworks();
////                for (int i = 0; i <networks.length ; i++) {
////                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
////                    Log.d("cc", networkInfo.getTypeName() + " connect is " + networkInfo.isConnected() + "--c->" + networkInfo.isAvailable() + ")type-->" + networkInfo.getType());
////                }
//                Log.d("cccc", "onReceive: "+networks.length);

                    NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
                    if (activeNetwork != null) { // connected to the internet
                        if (activeNetwork.isConnected()) {
                            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                                Log.e("ccccccc", "当前WiFi连接可用 ");
                                netWorkStateListener.onWifi();

                            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                                Log.e("cccccc", "当前移动网络连接可用 ");
                                netWorkStateListener.onGPRS();
                            }
                        } else {
                            Log.e("cccccc", "当前没有网络连接，请确保你已经打开网络 ");
                            netWorkStateListener.onNoNet();
                        }
                    } else {   // not connected to the internet
                        Log.e("ccccccc", "当前没有网络连接，请确保你已经打开网络 ");
                        netWorkStateListener.onNoNet();
                    }
            }
        }
    }
}
