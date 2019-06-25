package com.taoxue.ui.model;

/**
 * Created by User on 2017/7/27.
 */

public class GysDrResultBean extends  BaseModel {
    /**
     * description : "小破孩"和"小丫"两个胖乎乎的卡通人物,造型简洁流畅,具有典型的中国风格.故事的取材和表现非常多样
     * gys_id : 402883a95ce25928015ce27375bd0002
     * gys_name : 澳通大连科技发展有限公司
     * id : 402883d25d73f0f8015d73f1f1070001
     * logo : http://117.71.57.47:10000/resource/uploadFiles/2017/07/24/1500888756757059711_150.jpg
     * name : 小破孩希系列
     */

    private String description;
    private String gys_id;
    private String gys_name;
    private String id;
    private String logo;
    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGys_id() {
        return gys_id;
    }

    public void setGys_id(String gys_id) {
        this.gys_id = gys_id;
    }

    public String getGys_name() {
        return gys_name;
    }

    public void setGys_name(String gys_name) {
        this.gys_name = gys_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GysDrResultBean{" +
                "description='" + description + '\'' +
                ", gys_id='" + gys_id + '\'' +
                ", gys_name='" + gys_name + '\'' +
                ", id='" + id + '\'' +
                ", logo='" + logo + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
