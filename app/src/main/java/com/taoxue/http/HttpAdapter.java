package com.taoxue.http;

import com.taoxue.app.TaoXueApplication;
import com.taoxue.http.gson.GsonConverterFactory;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilSystem;

import java.io.IOException;

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

    private static OkHttpClient client = new OkHttpClient();

    public static void init() {

        client = client.newBuilder()
                .addNetworkInterceptor(new HttpInterceptor())
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
                    .addQueryParameter("user_id", TaoXueApplication.get().getUserModel().getUser_id()==null||TaoXueApplication.get().getUserModel().getUser_id().equals("null") ? "" : TaoXueApplication.get().getUserModel().getUser_id())
                    .build();

            Request build = request.newBuilder()
                    // add common header
                    .url(httpUrl)
                    .build();
            Response response = chain.proceed(build);

//            if (!url.contains("api/User")) {
//                request = request.newBuilder().addHeader("clientId", "bTVvVFZCMVpTYTRnZFppbTlPTFlHOVBu").addHeader("tokenId", TaoXueApplication.get().getTokenId()).build();
//            }
            LogUtils.i("http_url", response.request().url());
            return response;
        }
    }

}
