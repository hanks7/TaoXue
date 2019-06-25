package com.taoxue.ui.model;

/**
 * Created by User on 2017/8/15.
 */

public class BuyingResource extends BaseModel{

    /**
     * description : 破孩动画具有浓郁的中国特色，风格多样，有些幽默搞笑，又有一定讽刺意味；有些温馨浪漫，崇尚温情；有些童气十足，老少皆宜。小破孩动画注重娱乐性、思想性、艺术性的结合，动画内容取材广泛，不局限于中国传统文化素材，也可以是现代的、国外的、即时的素材，各种题材又融入了许多现代、时尚的元素，更适广大观众的口味。
     * pay_time : 2017-08-11 14:57:21
     * pay_type : ZFB
     * resource_id : 402883d25d6334d1015d6341b9660004
     * resource_name : 小破孩-包
     * resource_picture : http://117.71.57.47:10000/resource/uploadFiles/2017/08/04/1501824773680068069_150.png
     * total_fee : 0.02
     */

    private String description;
    private String pay_time;
    private String pay_type;
    private String resource_id;
    private String resource_name;
    private String resource_picture;
    private String total_fee;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
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

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }
}
