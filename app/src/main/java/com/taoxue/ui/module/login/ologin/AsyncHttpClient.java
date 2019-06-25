package com.taoxue.ui.module.login.ologin;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 2017/9/11.
 */

public class AsyncHttpClient {
    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public  void httpGet(final String url, final HttpResponseHandler responseHandler){
        AsyncTask<String,String,String>task=new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                String result=doGet(url);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s!=null){
                    responseHandler.onSuccess(s);
                }else{
                    responseHandler.onError(s);
                }
            }
        };
        task.execute();

    }
    /**
     * Get请求，获得返回数据
     *
     * @param urlStr
     * @return
     * @throws Exception
     */
    public  String doGet(String urlStr) {
        URL url;
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[128];

                while ((len = is.read(buf)) != -1) {
                    baos.write(buf, 0, len);
                }
                baos.flush();
                return baos.toString();
            } else {
                throw new RuntimeException(" responseCode is not 200 ... ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
            }
            conn.disconnect();
        }

        return null;

    }

}
