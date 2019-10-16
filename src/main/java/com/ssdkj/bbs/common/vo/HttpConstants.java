package com.ssdkj.bbs.common.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * http层静态常量
 * @author wangpeng24
 */
public class HttpConstants {

    public static final String TOKEN_HEADER_NAME = "X-AUTH-TOKEN";
    public static final String REDIS_TOKEN_PREFIX = "o2mtoken_";
    public static final String CHANNEL_CODE = "GZDJ";
    /** cookieName */
    public static final String COOKIE_TOKEN = "O2M_TOKEN";
    public static final Integer ADMIN_ID = -4;

    /**
     * 性别Map
     */
    public static Map<Short,String> genderMap;
    static {
        genderMap = new HashMap<>();
        genderMap.put((short)0,"未知");
        genderMap.put((short)1,"男");
        genderMap.put((short)2,"女");
    }
    
    /**
     * 产品类型Map
     */
    public static Map<Short,String> producttypeMap;
    static {
        producttypeMap = new HashMap<>();
        producttypeMap.put((short)0,"其他");
        producttypeMap.put((short)1,"儿科");
        producttypeMap.put((short)2,"妇科");
        producttypeMap.put((short)3,"男科");
        producttypeMap.put((short)4,"免疫");
        producttypeMap.put((short)5,"维生素");
        producttypeMap.put((short)6,"特医食品");
    }

    /**
     * 证件Map
     */
    public static Map<Integer,String> credentialTypeMap;
    static {
        credentialTypeMap = new HashMap<>();
        credentialTypeMap.put(1,"身份证");
        credentialTypeMap.put(2,"军官证");
    }

    /**
     * 收款方式
     */
    public static Map<Integer,String> gatheringMethodMap;
    static {
        gatheringMethodMap = new HashMap<>();
        gatheringMethodMap.put(1,"现金");
        gatheringMethodMap.put(2,"微信");
        gatheringMethodMap.put(3,"支付宝");
        gatheringMethodMap.put(4,"银行卡");
    }

    /**
     * 消费用途Map
     */
    public static Map<Integer, String> consumptionUsageMap;
    static {
        consumptionUsageMap = new HashMap<>();
        consumptionUsageMap.put(1,"网咖充值");
        consumptionUsageMap.put(2,"其它");
    }

    /**
     * 返利类型Map
     */
    public static Map<Integer, String> rebateTypeMap;
    static {
        rebateTypeMap = new HashMap<>();
        rebateTypeMap.put(1,"积分");
        rebateTypeMap.put(2,"优惠券");
        rebateTypeMap.put(3,"储值账户");
        rebateTypeMap.put(4,"现金红包");
//        rebateTypeMap.put(5,"工资");
    }

    /**
     * 销售渠道Map
     */
    public static Map<String, String> channelCodeMap;
    static {
        channelCodeMap = new HashMap<>();
        channelCodeMap.put("GZDJ","广州电竞网咖");
    }
}
