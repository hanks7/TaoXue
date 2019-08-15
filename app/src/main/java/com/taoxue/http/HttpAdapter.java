package com.taoxue.http;

import com.taoxue.app.BaseApplication;
import com.taoxue.http.gson.GsonConverterFactory;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilSystem;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static com.taoxue.app.Constants.FORMAL_URL;

/**
 * Created by CC on 2016/5/28.
 */
public class HttpAdapter {


    private static HttpApis service;

    private static OkHttpClient client;

    public static void init() {

        client = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new HttpInterceptor())
                //处理多BaseUrl,添加应用拦截器
                .readTimeout(1, TimeUnit.SECONDS)
                .connectTimeout(1, TimeUnit.SECONDS)
                .addInterceptor
                        (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FORMAL_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        service = retrofit.create(HttpApis.class);
    }


    public static HttpApis getService() {
        if (service == null) {
            init();
        }
        return service;
    }

    public static class HttpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            HttpUrl httpUrl = request.url()
                    .newBuilder()
                    .addQueryParameter("IMEI", UtilSystem.getIMSI())
                    .addQueryParameter("user_id", BaseApplication.get().getUserModel().getUser_id() == null || BaseApplication.get().getUserModel().getUser_id().equals("null") ? "" : BaseApplication.get().getUserModel().getUser_id())
                    .build();

            Request build = request.newBuilder()
                    // add common header
                    .url(httpUrl)
                    .build();
            Response response = chain.proceed(build);

//            if (!url.contains("api/User")) {
//                request = request.newBuilder().addHeader("clientId", "bTVvVFZCMVpTYTRnZFppbTlPTFlHOVBu").addHeader("tokenId", BaseApplication.get().getTokenId()).build();
//            }
            LogUtils.i("http_url", response.request().url());
            return response;
        }
    }

}
