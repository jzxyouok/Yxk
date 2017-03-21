package com.huilianonline.yxk.global;

public class ConstantValues {

    public static final String BASE_URL = "http://yxkservice.huilianonline.com/";//测试
//    public static final String BASE_URL = "http://appapi.grassland.gov.cn";//正式

    public static final String GET_USERINFO_URL = BASE_URL + "account/GetUserInfo";//根据用户手机号码，获取安全key和userid

    public static final String UPDATE_USERINFO_URL = BASE_URL + "account/UpdateUserInfo";//第一次开机设置

    public static final String GET_PARENTLIST_URL = BASE_URL + "ProductClass/GetList";//读取全部大分类

    public static final String GET_MSG_LIST_URL = BASE_URL + "msg/GetMsgList";//消息相关


}
