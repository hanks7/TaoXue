package com.taoxue.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taoxue.ui.model.BaseModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * Created by liu on 15/5/29.
 * 解析Gson数据格式
 */
public class UtilGson {


    public static BaseModel fromJson(String gsonStr, Type type) {

        try {
            if (gsonStr == null) return null;
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.fromJson(gsonStr, type);
        } catch (Exception e) {
            e.printStackTrace();
            UtilToast.showText(e + "解析json错误,后台修改json字段,造成异常");
            return null;
        }

    }

    public static String toJson(Object obj) {
        try {
            if (obj == null) return null;
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            return gson.toJson(obj);
        } catch (Exception e) {
            UtilToast.showText(e + "");
            return null;
        }
    }

    /**
     * 得到json文件中的内容
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 将字符串转换为 对象
     * @param json
     * @param type
     * @return
     */
    public  static <T> T JsonToObject(String json, Class<T> type) {
        Gson gson =new Gson();
        return gson.fromJson(json, type);
    }
}
