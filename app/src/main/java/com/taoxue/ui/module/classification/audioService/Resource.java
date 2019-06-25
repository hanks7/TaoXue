package com.taoxue.ui.module.classification.audioService;

import android.os.Parcel;
import android.os.Parcelable;

import com.taoxue.ui.model.BaseModel;

/**
 * Created by User on 2017/8/28.
 */

public class Resource implements Parcelable {
    private String resource_id;
    private String resource_name;
    private String resource_picture;
    private String gys_id;

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getResource_picture() {
        return resource_picture;
    }

    public void setResource_picture(String resource_picture) {
        this.resource_picture = resource_picture;
    }

    public Resource() {
    }
    public Resource(Parcel in) {
      readFromParcel(in);
    }

    public static final Creator<Resource>CREATOR=new Creator<Resource>() {
        @Override
        public Resource createFromParcel(Parcel source) {
            return new Resource(source);
        }

        @Override
        public Resource[] newArray(int size) {
            return new Resource[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        resource_id=dest.readString();
        resource_name = dest.readString();
        resource_picture=dest.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resource_name);
        dest.writeString(resource_id);
        dest.writeString(resource_picture);
    }
}
