package com.taoxue.ui.model;

import java.io.Serializable;

/**
 * @author 侯建军
 * @data on 2018/2/8 11:15
 * @org www.hopshine.com
 * @function 请填写
 * @email 474664736@qq.com
 */

public class QryMyCollectionBean implements Serializable{




    /**
     * collection_num : 2
     * description : 内向性格研究领域的专家马蒂·奥尔森·兰妮博士（Marti Olsen Laney, Psy.D.）继《内向者优势》后的又一力作。她将多年的临川研究经验与自己身为一个内向者的成长经历作为蓝本，为我们总结出了培养好一个内向性格孩子的种种建议。书中更是用不少篇幅向我们解释了内向、外向这两种不同的性格气质形成的原因，科学生动、简明易懂，十分值得一听。
     * resource_id : 402883d2615a382c01615a7c8dbe0158
     * resource_name : 樊登读书会-内向还在的潜在优势
     * resource_picture : http://117.71.57.47:11000/manager/upload/402883d2615a3aff01615a7c64ab0001.jpg
     * visit_num : 32
     */
    private String description;
    private String resource_id;
    private String resource_name;
    private String resource_picture;
    private int visit_num;
    private int collection_num;
    /**
     * collection_num : 9
     * read_num : 23
     */

    private String read_num;

    public int getCollection_num() {
        return collection_num;
    }

    public void setCollection_num(int collection_num) {
        this.collection_num = collection_num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getVisit_num() {
        return visit_num;
    }

    public void setVisit_num(int visit_num) {
        this.visit_num = visit_num;
    }


    public String getRead_num() {
        return read_num;
    }

    public void setRead_num(String read_num) {
        this.read_num = read_num;
    }
}
