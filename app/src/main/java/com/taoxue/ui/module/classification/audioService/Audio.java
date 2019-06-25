package com.taoxue.ui.module.classification.audioService;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by User on 2017/8/28.
 */

public class Audio implements Parcelable {
    public Audio() {
    }
   public Audio(Parcel in){
       readFromParcel(in);
   }

    private String resource_name;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public static final Creator<Audio>CREATOR=new Creator<Audio>() {
        @Override
        public Audio createFromParcel(Parcel source) {
            return new Audio(source);
        }

        @Override
        public Audio[] newArray(int size) {
            return new Audio[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resource_name);
        dest.writeString(url);
    }
    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        resource_name = dest.readString();
        url = dest.readString();
    }

    @Override
    public String toString() {
        return "Audio{" +
                "resource_name='" + resource_name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
