package com.taoxue.http;

import com.taoxue.ui.model.BaseBean;
import com.taoxue.ui.model.BaseItemListModel;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.BookInfoBean;
import com.taoxue.ui.model.BuyingResource;
import com.taoxue.ui.model.CgsHomeInfoBean;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.ClassfiBean;
import com.taoxue.ui.model.ComentModel;
import com.taoxue.ui.model.CommitPageModel;
import com.taoxue.ui.model.DrdataBean;
import com.taoxue.ui.model.FileUrl;
import com.taoxue.ui.model.FlagCodeBean;
import com.taoxue.ui.model.FragCollectionBean;
import com.taoxue.ui.model.GetDetailBean;
import com.taoxue.ui.model.GuanzhuLibBean;
import com.taoxue.ui.model.GysDrResultBean;
import com.taoxue.ui.model.GysDrdataBean;
import com.taoxue.ui.model.GysHomeInfoBean;
import com.taoxue.ui.model.GysResourceBean;
import com.taoxue.ui.model.ImageBean;
import com.taoxue.ui.model.LibInfoModel;
import com.taoxue.ui.model.LibrayInfoBean;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.model.PageModel;
import com.taoxue.ui.model.ProvinceBean;
import com.taoxue.ui.model.QryMyCollectionBean;
import com.taoxue.ui.model.ReadBean;
import com.taoxue.ui.model.ReadInfoBean;
import com.taoxue.ui.model.ReadJiLuBean;
import com.taoxue.ui.model.ReaderCodeModel;
import com.taoxue.ui.model.ResDetailBean;
import com.taoxue.ui.model.ResourceLibModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.ResourcePraiseBean;
import com.taoxue.ui.model.ResultModel;
import com.taoxue.ui.model.SearchDigitalResourcesBean;
import com.taoxue.ui.model.StateBean;
import com.taoxue.ui.model.TxtReadBean;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.model.VideoBean;
import com.taoxue.ui.model.YzmBean;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.search.bean.ResoureHotSearchKeyBean;
import com.taoxue.ui.module.search.bean.SearchSupplierBean;
import com.taoxue.utils.update.IsUpdateBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by CC on 2016/5/28.
 */

public interface HttpApis {

    /**
     * 测试
     *
     * @return
     */
    @GET("getCatalogType.do")
    Call<BasePageModel<BaseModel>> getCatalogType();

    /**
     * 短信验证
     *
     * @param mobile
     * @return
     */
    @GET("mt/h5/20/member/getSMS.do")
    Call<CheckSignModel> sendvalidate(@Query("mobile") String mobile);

    /**
     * 忘记密码中短信验证
     *
     * @param mobile
     * @return
     */
    @GET("mt/sendSMS.do")
    Call<BaseResultModel<YzmBean>> forgetPwSendSMS(@Query("mobile") String mobile);


    /**
     * 忘记密码后重新设置密码
     *
     * @param mobile
     * @return
     */
    @GET("mt/updforgetPassword.do")
    Call<BaseBean> updateforgetPw(@Query("mobile") String mobile, @Query("code") String code, @Query("credential") String credential, @Query("qrCredential") String qrCredential);

    /**
     * 修改密码
     *
     * @param mobile
     * @return
     */
    @GET("mt/updPassword.do")
    Call<BaseBean> modifyPwDo(@Query("credential") String credential, @Query("mobile") String mobile, @Query("newCredential") String newCredential, @Query("qrCredential") String qrCredential);


    /**
     * 注册
     *
     * @param mobile
     * @param name
     * @param credential
     * @param reader_card_id
     * @param cgs_id
     * @return
     */
    @GET("user/saveRegister.do")
    Call<CheckSignModel> getRegister(@Query("mobile") String mobile, @Query("name") String name, @Query("credential") String credential, @Query("reader_card_id") String reader_card_id, @Query("cgs_id") String cgs_id);

    /**
     * 登录
     *
     * @param identifier
     * @param credential
     * @return
     */
    @GET("mt/h5/20/member/login/check.do")
    Call<BaseResultModel<UserModel>> getLogin(@Query("identifier") String identifier, @Query("credential") String credential, @Query("identity_type") String identity_type);


    /**
     * 图书证登录
     *
     * @param identifier
     * @param credential
     * @return
     */
    @GET("mt/h5/20/member/login/check.do")
    Call<BaseResultModel<UserModel>> getLoginByCgsId(@Query("cgs_id") String cgs_id, @Query("identifier") String identifier, @Query("credential") String credential, @Query("identity_type") String identity_type);


    /**
     * 获取资源图片
     *
     * @return
     */
    @GET("mt/h5/20/resource/read.do")
    Call<BaseListModel<ImageBean>> getResImages(@Query("resource_id") String resource_id);

    /**
     * 获取资源txt
     *
     * @return
     */
    @GET("mt/h5/20/resource/read.do")
    Call<BaseListModel<TxtReadBean>> getResTxts(@Query("resource_id") String resource_id);

    /**
     * 获取资源txt
     *
     * @return
     */
    @GET("mt/h5/20/resource/read.do")
    Call<BaseResultModel<ReadInfoBean>> getResources(@Query("resource_id") String resource_id, @Query("cgs_id") String cgs_id);

    /**
     * 视频txt
     *
     * @return
     */
    @GET("mt/h5/20/resource/read.do")
    Call<BaseResultModel<VideoBean>> getVideo(@Query("resource_id") String resource_id, @Query("cgs_id") String cgs_id);

    /**
     * 获取分类详情
     *
     * @param type_flag
     * @param keyword
     * @return
     */
    @GET("searchDigitalResources.do")
    Call<BasePageModel<ResultModel>> getDetail(@Query("keyword") String keyword, @Query("type_flag") String type_flag, @Query("pageNo") String pageNo, @Query("pageSize") String pageSize);

    /**
     * 资源_库_搜索结果
     *
     * @param keyword
     * @return
     */
    @GET("mt/search/drdata.do")
    Call<BasePageModel<ResultModel>> drdata(@Query("keyword") String keyword, @Query("pageNo") String pageNo, @Query("pageSize") String pageSize);

    /**
     * 获取分类详情
     *
     * @param resource_id
     * @return
     */
    @GET("mt/h5/20/resource/getDetail.do")
    Call<BaseResultModel<ResourceModel>> getGys(
            @Query("resource_id") String resource_id,
            @Query("fr") String fr
    );


    /**
     * 获取资源详细接口及二维码扫描接口
     *
     * @param resource_id
     * @return
     */
    @GET("dr/mt/view.do")
    Call<BaseResultModel<ResourceModel>> getResourceDetail(@Query("resource_id") String resource_id, @Query("province") String province, @Query("city") String city);


    /**
     * 获取资源文件Url
     *
     * @param resource_id
     * @return
     */
    @GET("dr/mt/read.do")
    Call<BaseResultModel<FileUrl>> getFileUrl2(@Query("resource_id") String resource_id, @Query("user_id") String user_id);


    /**
     * 添加收藏
     *
     * @param gys_id
     * @param resource_id
     * @return
     */
    @GET("mt/h5/20/resource/saveOrDelUserCollection.do")
    Call<BaseResultModel<StateBean>> addOrCancelCollection(@Query("resource_id") String resource_id, @Query("gys_id") String gys_id);

    /**
     * 检测 mobile和 code
     *
     * @param mobile
     * @param code
     * @return
     */
    @GET("mt/h5/20/member/checkYzm.do")
    Call<CheckSignModel> checkSMSCode(@Query("mobile") String mobile, @Query("code") String code);

    /**
     * 注册
     *
     * @param mobile
     * @param
     * @return
     */
    @GET("mt/h5/20/member/saveRegister.do")
    Call<BaseResultModel<UserModel>> saveRegister(@Query("mobile") String mobile, @Query("credential") String credential);

    /**
     * 首页接口
     *
     * @return
     */
    @GET("mt/h5/20/resource/index.do")
    Call<BaseResultModel<ApiOneBean>> getHome1();

    /**
     * 我的收藏
     *
     * @return
     */
    @GET("mt/h5/20/member/qryMyCollection.do")
    Call<BasePageModel<FragCollectionBean>> collection(@Query("pageNo") int page, @Query("pageSize") int pageSize);

    /**
     * 我的收藏
     *
     * @return
     */
    @GET("mt/h5/20/member/qryMyCollection.do")
    Call<BasePageModel<QryMyCollectionBean>> collection2(@Query("pageNo") int page, @Query("pageSize") int pageSize);

    /**
     * 我的收藏
     *
     * @return
     */
    @GET("mt/h5/20/member/qryMyCollection.do?pageSize=10")
    Call<BasePageModel<QryMyCollectionBean>> collection3(@Query("pageNo") int page);

    /**
     * 播放记录
     *
     * @return
     */
    @GET("user/dr/read.do")
    Call<BasePageModel<ReadBean>> read(@Query("user_id") String userId, @Query("pageNo") int page, @Query("pageSize") int pageSize);

    /**
     * 查询用户基本信息
     *
     * @return
     */
    @GET("mt/user/info.do")
    Call<BaseResultModel<UserModel>> info();

    /**
     * 验证读者证
     *
     * @param reader_id
     * @return
     */
    @GET("user/register/getTempReaderInfo.do")
    Call<BaseResultModel<ReaderCodeModel>> testReaderCode(@Query("reader_id") String reader_id, @Query("reader_name") String reader_name);

    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoName(
            @Query("user_id") String reader_id,
            @Query("name") String str
    );

    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfo(
            @Query("user_id") String reader_id,
            @Query("sex") String sex,
            @Query("birth_year") String birth_year,
            @Query("hangye") String hangye,
            @Query("job") String job,
            @Query("education") String education
    );

    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoBirth(
            @Query("user_id") String reader_id,
            @Query("birth_year") String birth_year
    );

    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoHangye(
            @Query("user_id") String reader_id,
            @Query("hangye") String hangye
    );

    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoJob(
            @Query("user_id") String reader_id,
            @Query("job") String job
    );

    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoEducation(
            @Query("user_id") String reader_id,
            @Query("education") String education
    );

//    /**
//     * 上传头像
//     *
//     * @param userid
//     * @param file_fmimg
//     * @return
//     */
//    @Multipart
//    @POST("mt/user/uploadAndSavePhoto.do")
//    Call<BaseResultModel<BaseModel>> uploadAndSavePhoto(
//            @Query("user_id") String reader_id,
//            @Part MultipartBody.Part file_fmimg);

    /*
     * 分类页数据
     */
    @GET("mt/h5/20/other/qryPtClass.do")
    Call<BaseItemListModel<ClassfiBean>> queryClassfication();


    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("mt/user/uploadAndSavePhoto.do")
    Call<BaseResultModel> uploadAndSavePhoto(
            @Query("user_id") String reader_id,
            @Part("file\"; filename=\"image.png") RequestBody file
    );

    /**
     * 查询我的图书馆信息
     *
     * @param user_id
     * @return
     */
    @GET("mt/h5/20/member/qryMyReadCard.do")
    Call<BaseListModel<MyLibInfo>> queryMyLibInfo(@Query("user_id") String user_id);

    /**
     * 通过user_id绑定读者证
     *
     * @param user_id
     * @param cgs_id
     * @param reader_card_id
     * @return
     */
    @GET("mt/user/bindingReaderCardId.do")
    Call<CheckSignModel> bindReaderCardIdByUserId(
            @Query("user_id") String user_id,
            @Query("cgs_id") String cgs_id,
            @Query("reader_card_id") String reader_card_id,
            @Query("reader_name") String reader_name);

    /**
     * 查询评论内容 及评分
     *
     * @param resource_id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET("mt/h5/20/resource/qryResourceCommentAndScore.do")
    Call<CommitPageModel<PageModel<ComentModel>>> queryResourceComment(
            @Query("resource_id") String resource_id,
            @Query("pageSize") String pageSize,
            @Query("pageNo") String pageNo);

    /**
     * 添加评论内容
     *
     * @param resource_id
     * @param content
     * @param pid         上级id pid=0是对资源的评论，大于0是对评论的评论
     * @return
     */
    @GET("mt/h5/20/resource/saveResourceCommentAndScore.do")
    Call<CheckSignModel> addResourceComment(
            @Query("resource_id") String resource_id,
            @Query("content") String content,
            @Query("score") String score,
            @Query("pid") String pid
    );


    /**
     * 点赞
     *
     * @param resource_id
     * @param gys_id
     * @return
     */
    @GET("mt/h5/20/resource//saveOrDelResourcePraise.do")
    Call<BaseResultModel<StateBean>> giveThumb(
            @Query("resource_id") String resource_id,
            @Query("gys_id") String gys_id);

    /**
     * 点赞
     *
     * @param resource_id
     * @param user_id
     * @param gys_id
     * @return
     */
    @GET("dr/saveResourcePraise.do")
    Call<BaseResultModel<ResourcePraiseBean>> giveThumb2(
            @Query("resource_id") String resource_id,
            @Query("user_id") String user_id,
            @Query("gys_id") String gys_id);

    /**
     * 阅加  中的 阅读记录
     *
     * @return
     */
    @GET("mt/h5/subscription/read/record.do")
    Call<BaseListModel<ReadJiLuBean>> getReaderJiLus();


    /**
     * 阅加  中的 已购
     *
     * @return
     */
    @GET("mt/h5/subscription/pay/resource.do")
    Call<BasePageModel<BookInfoBean>> getYiGous();
    //mt/h5/subscription/collection/resource.do

    /**
     * 阅加  中的 收藏
     *
     * @return
     */
    @GET("mt/h5/subscription/collection/resource.do")
    Call<BasePageModel<BookInfoBean>> getCollections();

    /**
     * 阅加  中的 关注图书馆
     *
     * @return
     */
    @GET("mt/h5/subscription/attention/library.do")
    Call<BaseListModel<GuanzhuLibBean>> getGuanzhuLibs();


    /**
     * 阅加  中的 关注资源商
     *
     * @return
     */
    @GET("mt/h5/subscription/attention/supplier.do")
    Call<BaseListModel<GuanzhuLibBean>> getGuanzhuSuppliers();

    /**
     * 最新   资源详情
     *
     * @return
     */
    @GET("mt/h5/20/resource/getDetail.do")
    Call<BaseResultModel<ResDetailBean>> getResourceDetails(@Query("resource_id") String resource_id);

    /**
     * 添加收藏
     *
     * @param gys_id
     * @param user_id
     * @param resource_id
     * @return
     */
    @GET("user/dr/collection/save.do")
    Call<BaseResultModel<ResourcePraiseBean>> addCollection2(
            @Query("user_id") String user_id,
            @Query("resource_id") String resource_id,
            @Query("gys_id") String gys_id);

    /**
     * 根据所在城市查询对应的采购商（图书馆）
     *
     * @param city
     * @return
     */
    @GET("mt/city/cgs.do")
    Call<BaseModel> searchCgs(@Query("city") String city);

    /**
     * 资源库详情接口
     *
     * @return
     */
    @GET("mt/drdata/info.do")
    Call<BaseModel> searchInfo();

    /**
     * 手机端显示所有的图书馆：输入名称时，可进行搜索
     *
     * @param city
     * @return
     */
    @GET("mt/cgs/list.do")
    Call<SearchSupplierBean> searchList(@Query("city") String city);

    /**
     * 供应商已经采购的所有数字资源
     *
     * @return
     */
    @GET("mt/gys/list.do")
    Call<SearchSupplierBean> gysList(@Query("name") String name);


    /**
     * 资源库详情
     *
     * @return
     */
    @GET("mt/drdata/info.do")
    Call<BaseResultModel<ResourceLibModel>> searchResourceDeatail();

    /**
     * 资源库详情
     *
     * @return
     */
    @GET("mt/drdata/info.do")
    Call<BaseResultModel<LibInfoModel>> queryResourceLibDeatail(@Query("dr_data_id") String dr_data_id, @Query("pageNo") String pageNo, @Query("pageSize") String pageSize);

    /**
     * 资源搜索,热门搜索关键词
     *
     * @return
     */
    @GET("mt/commend/keyword/resource.do")
    Call<ResoureHotSearchKeyBean> resource();

    /**
     * 资源库 热门搜索
     *
     * @return
     */
    @GET("mt/commend/keyword/drdata.do")
    Call<ResoureHotSearchKeyBean> keywordDrdata();

    /**
     * 图书馆资源搜索结果
     *
     * @return
     */
    @GET("searchDigitalResources.do?pageSize=10")
    Call<SearchDigitalResourcesBean> searchDigitalResourcesCgs_id(@Query("pageNo") int page, @Query("keyword") String keyword, @Query("cgs_id") String cgs_id);

    /**
     * 供应商资源搜索结果
     *
     * @return
     */
    @GET("searchDigitalResources.do?pageSize=10")
    Call<SearchDigitalResourcesBean> searchDigitalResourcesGys_id(@Query("pageNo") int page, @Query("keyword") String keyword, @Query("gys_id") String gys_id);

    /**
     * 资源搜索结果
     *
     * @return
     */
    @GET("mt/h5/20/resource/resourceSearch.do?pageSize=7")
    Call<BasePageModel<SearchDigitalResourcesBean>> searchDigitalResources(
            @Query("pageNo") int page,
            @Query("typeFlag") String typeFlag,
            @Query("keyword") String keyword
    );

    /**
     * 分类结果
     *
     * @return
     */
    @GET("mt/h5/20/resource/qryResourceByTag.do?pageSize=10")
    Call<BasePageModel<GetDetailBean>> qryResourceByTag(
            @Query("pageNo") int page,
            @Query("tag") String keyword,
            @Query("activity") String activityName);

    /**
     * 资源库搜索结果
     *
     * @return
     */
    @GET("mt/search/drdata.do?pageSize=10")
    Call<DrdataBean> drdata(@Query("pageNo") int page, @Query("keyword") String keyword);

    /**
     * 供应商首页
     *
     * @return
     */
    @GET("mt/gys/info/index.do")
    Call<BaseResultModel<GysHomeInfoBean>> getGysHomeInfo(@Query("gys_id") String gys_id);

    /**
     * 供应商资源库
     *
     * @return
     */
    @GET("mt/gys/drdata.do?pageSize=10")
    Call<GysDrdataBean> getGysDrdata(@Query("pageNo") int page, @Query("gys_id") String gys_id);

    /**
     * 供应商全部资源
     *
     * @return
     */
    @GET("mt/gys/resource.do")
    Call<GysResourceBean> getGysResource(@Query("pageSize") int pageSize, @Query("pageNo") int page, @Query("gys_id") String gys_id);


    /**
     * 图书馆首页
     *
     * @return
     */
    @GET("mt/cgs/info/index.do")
    Call<BaseResultModel<CgsHomeInfoBean>> getCgsHomeInfo(@Query("cgs_id") String gys_id);

    /**
     * 图书馆资源库
     *
     * @return
     */
    @GET("mt/cgs/drdata/list.do")
    Call<GysDrdataBean> getCgsDrdata(@Query("pageSize") int pageSize, @Query("pageNo") int page, @Query("cgs_id") String gys_id);

    /**
     * 图书馆资源库
     *
     * @return
     */
    @GET("mt/cgs/drdata/list.do")
    Call<BasePageModel<GysDrResultBean>> getCgsDrdatafasdfasdf(@Query("pageSize") int pageSize, @Query("pageNo") int page, @Query("cgs_id") String gys_id);


    /**
     * 图书馆全部资源
     *
     * @return
     */
    @GET("mt/cgs/resource/list.do")
    Call<GysResourceBean> getCgsResource(@Query("pageSize") int pageSize, @Query("pageNo") int page, @Query("cgs_id") String gys_id);

    /**
     * 升级
     *
     * @return
     */
    @GET("mt/apkVersionUpdate.do?pageage_name=apk_version")
    Call<BaseResultModel<IsUpdateBean>> getApkVersionUpdate(@Query("version_num") int version_num);

    /**
     * 修改支付 顺序
     *
     * @return
     */
    @GET("mt/h5/20/member/updLibraryPayOrder.do")
    Call<BaseResultModel> updatePayOrder(@Query("order_string") String order_string);

    /**
     * 查询我的图书馆信息
     *
     * @param user_id
     * @return
     */
    @GET("mt/user/qryPayCgs.do")
    Call<BaseListModel<MyLibInfo>> queryReaderCardPayInfo(@Query("resource_id") String resource_id, @Query("user_id") String user_id);


    /**
     * 取消点赞
     *
     * @param user_id
     * @return
     */
    @GET("dr/delResourcePraise.do")
    Call<BaseResultModel> cancelGiveThumb(@Query("resource_id") String resource_id, @Query("user_id") String user_id);

    /**
     * 点赞/取消点赞 使用的是同一个接口
     *
     * @return
     */
    @GET("mt/h5/20/resource/saveOrDelResourcePraise.do")
    Call<BaseResultModel<FlagCodeBean>> saveOrDelResourcePraise(
            @Query("resource_id") String resource_id,
            @Query("gys_id") String gys_id
    );

    /**
     * 点赞/取消收藏 使用的是同一个接口
     *
     * @return
     */
    @GET("mt/h5/20/resource/saveOrDelUserCollection.do")
    Call<BaseResultModel<FlagCodeBean>> saveOrDelUserCollection(
            @Query("resource_id") String resource_id,
            @Query("gys_id") String gys_id
    );

    /**
     * 取消收藏
     *
     * @param user_id
     * @return
     */
    @GET("dr/delResourceCollection.do")
    Call<BaseResultModel> cancelCollection(@Query("resource_id") String resource_id, @Query("user_id") String user_id);

    /**
     * 取消点赞
     *
     * @param
     * @return
     */
    @GET("user/login/check.do")
    Call<BaseResultModel<UserModel>> weixinCheckLogin(@Query("identifier") String identifier, @Query("identity_type") String identity_type);


    /**
     * 微信注册
     *
     * @param
     * @return
     */
    @GET("mt/h5/20/member/saveWeiXin.do")
    Call<BaseResultModel<UserModel>> saveWeixinInfo(@Query("identifier") String identifier, @Query("nickname") String nickname, @Query("photo") String photo, @Query("sex") String sex);


    /**
     * 微信关联手机号
     *
     * @param
     * @return
     */
    @GET("user/bangdingMobile.do")
    Call<CheckSignModel> weixinRelationPhone(@Query("identifier") String identifier, @Query("mobile") String mobile, @Query("credential") String credential, @Query("cgs_id") String cgs_id, @Query("reader_card_id") String reader_card_id);

    /**
     * 查询省份
     *
     * @param
     * @return
     */
    @GET("mt/h5/20/member/qryAllLibraryProvince.do")
    Call<BaseListModel<ProvinceBean>> qryAllLibraryProvince();

    /**
     * 根据省份查询图书馆
     *
     * @param
     * @return
     */
    @GET("mt/h5/20/member/qryLibraryByProvince.do")
    Call<BaseListModel<LibrayInfoBean>> qryLibraryByProvince(@Query("province") String province);

    /**
     * 支付宝
     *
     * @param
     * @return
     */
    @GET("mt/resource/order.do")
    Call<BaseResultModel<String>> zhifubaoPay(@Query("user_id") String user_id, @Query("resource_id") String resource_id);


    /**
     * 关联读者证
     *
     * @param
     * @return
     */
    @GET("mt/h5/20/member/saveBindingReaderCardId.do")
    Call<CheckSignModel> saveBindingReaderCardId(@Query("reader_card_id") String reader_card_id, @Query("cgs_id") String cgs_id, @Query("credential") String credential);


    /**
     * 支付宝请求结果
     *
     * @param
     * @return
     */
    @GET("mt/sync/updOrderStatus.do")
    Call<BaseResultModel> zhifubaoPayResult(@Query("result") String result);

    /**
     * 已购买资源的接口
     *
     * @param
     * @return
     */
    @GET("mt/resource/userPay.do")
    Call<BasePageModel<BuyingResource>> userPay(@Query("user_id") String userId, @Query("pageNo") int page, @Query("pageSize") int pageSize);


}
