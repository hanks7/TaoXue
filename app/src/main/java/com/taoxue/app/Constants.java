package com.taoxue.app;

import android.os.Environment;

import java.util.ArrayList;

/**
 * Created by hanks7 on 2017/4/1.
 */

public class Constants {

    /**
     * 是否上线  为 true 为测试状态  false 为上线状态
     */
    public final static Boolean IS_TEST = true;
    /**
     * 是否关闭打印日志
     */
    public final static Boolean IS_LOG = IS_TEST;
    /**
     * 网络请求的ip端口号
     *
     */
//    public final static String FORMAL_URL = "http://117.71.57.47:11000/frontend/";//测试环境地址
//    public final static String FORMAL_URL = "http://192.168.3.82:12000/frontend/";//测试环境地址
        public final static String FORMAL_URL = "http://www.hopshine.net:11000/DRIS_frontend_V1.0.1/";//正式环境地址
    /**
     * 外部html的外部地址
     */
    public final static String HTML_IP_PORT="http://117.71.57.47:11000/dulianti/";
    /**
     * 图书馆首页_html;
     */
    public final static String WEB_LIBRARY_URL = HTML_IP_PORT+"src/library/libHome.html?__hbt=1517563285671";
    /**
     * 资源供应商首页_html
     */
    public final static String WEB_SUPPLIER_URL = HTML_IP_PORT+"src/supplier/digitalresourceservice.html?__hbt=1517536641008";

    /**
     * 关联读者证
     */
    public final static String CONTACT_READER_CARD = HTML_IP_PORT+"src/member/contactReaderCard.html";
    /**
     * 首页_宝宝静听_html
     */
    public final static String WEB_BAOBAOJINTING_URL = HTML_IP_PORT+"src/home/baobaojingtin.html?__hbt=1517649871226";
    /**
     * 首页_读绘本_html
     */
    public final static String WEB_DUHUIBEN_URL = HTML_IP_PORT+"src/home/duhuiben.html?__hbt=1517897233598";
    /**
     * 首页_少年博览_html
     */
    public final static String WEB_DSHAO_NIAN_BO_LAN_URL = HTML_IP_PORT+"src/home/shaonianbolan.html";
    /**
     * 首页_都在看_html
     */
    public final static String WEB_DOUZAIKAN_URL = HTML_IP_PORT+"src/home/douzaikan.html";
    /**
     * 首页_超实惠_html
     */
    public final static String  WEB_CHAOSHIHUI_URL= HTML_IP_PORT+"src/home/chaoshihui.html";
    /**
     * 首页_有好书_html
     */
    public final static String WEB_YOUHAOSHU_URL = HTML_IP_PORT+"src/home/youhaoshu.html";
    /**
     * 首页_最爱听_html
     */
    public final static String WEB_ZUIAITING_URL = HTML_IP_PORT+"src/home/zuiaiting.html";
    /**
     * 首页_豆蔻年华_html
     */
    public final static String WEB_DONGKOUNIANHUA_URL = HTML_IP_PORT+"src/home/doukounianhua.html";
    /**
     * 设置页_帮助_html
     */
    public final static String WEB_HELPANDFK_URL = HTML_IP_PORT+"src/member/helpAndFk.html";
    /**
     * 首页_图书馆根据资源库查看所有数值资源_html
     */
    public final static String WEB_LIB2_FLLIST_URL = HTML_IP_PORT+"src/library/flList.html";
    /**
     * 首页_根据资源下轮播图跳转的列表页_html 这个不在用了
     */
    @Deprecated
    public final static String WEB_LIB_FLLIST_URL = HTML_IP_PORT+"src/classfication/flList.html";
    /**
     * 首页_根据首页资源库查所有资源_html
     */
    public final static String WEB_SUPPLIER_FLLIST_URL = HTML_IP_PORT+"src/supplier/flList.html";

    /**
     *app标记
     */
    public final static String APP_INDEX = "taoxue";
    /**
     * 测试图片
     */
    public final static String TEST_PIC_URL = "http://pic.people.com.cn/NMediaFile/2018/0201/MAIN201802010512000578401907852.png";
    /**
     * apk安装名称
     */
    public final static String apkName = APP_INDEX + ".apk";
    /**
     * 下载路径
     */
    public static String apkURL = "";
    /**
     * imageload缓存路径
     * 可读写的缓存路径
     * /storage/emulated/0/ebookcar/
     */
    public static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_INDEX + "/";

    /**
     * 轮播图图片
     */
    public static ArrayList<String> textUrlList() {

        ArrayList<String> list = new ArrayList<>();

        list.add("https://i3.mifile.cn/a4/xmad_14964017903819_apNQP.jpg");

        list.add("https://i3.mifile.cn/a4/xmad_14965733100139_wcKQo.jpg");

        list.add("https://i3.mifile.cn/a4/xmad_14962268945869_klEsv.jpg");

        list.add("https://i3.mifile.cn/a4/xmad_14952089770715_otDpj.jpg");


        return list;

    }


}
