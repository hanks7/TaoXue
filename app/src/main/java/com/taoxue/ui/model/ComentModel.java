package com.taoxue.ui.model;

/**
 * Created by User on 2017/5/17.
 */

public class ComentModel extends BaseModel {
            private String id;
            private String resource_id;
            private String user_id;
            private String content;
            private String addtime;
            private String name;
            private String photo;
            private String pid;
            private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "ComentModel{" +
                "id='" + id + '\'' +
                ", resource_id='" + resource_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", content='" + content + '\'' +
                ", addtime='" + addtime + '\'' +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", pid='" + pid + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
