package com.ssdkj.bbs.core.util;

import com.alibaba.fastjson.JSONObject;

public class WeChatUtil {
    public static final String APPID = "wx9f7fddfaffb3de79";
    //公众号的appsecret
    public static final String APPSECRET = "f514f4a12cd26fe58243b8e0f110298e";
    //获取网页授权accessToken的接口
    public static final String GET_WEB_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //获取用户信息的接口
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


    /**
     * 获取网页授权的AccessToken凭据
     * @return
     */
    public static JSONObject getWebAccessToken(String code) {
        String result = null;
        try {
            result = HttpClientUtils.doGet(GET_WEB_ACCESSTOKEN_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = JSONObject.parseObject(result);
        return json;
    }
    /**
     * 获取用户信息
     *
     */
    public static JSONObject getUserInfo(String accessToken,String openId) {
        String result = null;
        try {
            result = HttpClientUtils.doGet(GET_USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID",openId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = JSONObject.parseObject(result);
        return json;
    }

}
