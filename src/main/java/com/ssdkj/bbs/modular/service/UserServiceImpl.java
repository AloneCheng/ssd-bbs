package com.ssdkj.bbs.modular.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.enums.BbsCenterEnum;
import com.ssdkj.bbs.core.util.StringUtils;
import com.ssdkj.bbs.core.util.WeChatUtil;
import com.ssdkj.bbs.modular.api.UserService;
import com.ssdkj.bbs.modular.dao.UserMapper;
import com.ssdkj.bbs.modular.dao.UserMapper;
import com.ssdkj.bbs.modular.manager.UserManager;
import com.ssdkj.bbs.modular.model.User;
import com.ssdkj.bbs.modular.model.UserExample;
import com.ssdkj.bbs.modular.model.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Log4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;


    @Autowired
    private UserManager manager;

    @Value("${ssd-bbs.admin.OpenId}")
    private String adminOpenId;

    @Override
    public Response<User> queryUser(long userId) {
        User user = null;
        try {
            user = userMapper.selectByPrimaryKey(userId);
        } catch (Exception e) {
            log.error("select UserById error", e);
            return Response.fail("error.get.User.byId", e.getMessage());
        }
        return Response.ok(user);
    }

    @Override
    public Response<PageList<User>> queryUsers(User seacher) {
        {
            PageList<User> pageList = null;
            try {
                log.info("Input param,UserSeacher: " + JSON.toJSONString(seacher));
                if (seacher == null) {
                    return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
                }
                //默认值
                if (seacher.getPageNum() < 1) {
                    seacher.setPageNum(1);
                }
                if (seacher.getPageSize() < 1) {
                    seacher.setPageSize(15);
                }
                if (seacher.getPageSize() > 0) {
                    PageHelper.startPage(seacher.getPageNum(), seacher.getPageSize());
                }

                UserExample example = new UserExample();
                UserExample.Criteria cia = example.createCriteria();

//                if (seacher.getArticleId() != null) {
//                    cia.andArticleIdEqualTo(seacher.getArticleId());
//                }
                example.setOrderByClause("userCreateTime DESC");
                Page<User> userPage = userMapper.selectByExample(example);
                pageList = new PageList<User>(userPage.getTotal(), seacher.getPageSize(), seacher.getPageNum(), userPage.getResult());
            } catch (Exception e) {
                log.error("error.get.user.list", e);
                return Response.fail("error.get.user.list", e.getMessage());
            }
            return Response.ok(pageList);
        }
    }

    @Override
    public Response<Boolean> addUser(User user) {
        log.info("Input param,User: " + JSON.toJSONString(user));
        try {
            if (user == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.saveUser(user);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.Activity.saveUser", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }

    @Override
    public Response<Boolean> removeUser(long userId) {
        log.info("Input param,User: " + userId);
        try {
            if (userId <= 0 ) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.removeUser(userId);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.User.removeUser", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }

    @Override
    public Response<Boolean> updateUser(User user) {
        log.info("Input param,User: " + JSON.toJSONString(user));
        try {
            if (user == null || user.getUserId() == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.updateUser(user);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.User.updateUser", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }


    public Response<User> auth(String code,String state) {
        log.info("code:  "+code+"\tstate:"+state);
        try {
            Map<String,Object> map = new HashMap<>();
            if(code!=null) {
                //1.通过code来换取access_token
                JSONObject json = WeChatUtil.getWebAccessToken(code);
                //获取网页授权access_token凭据
                String webAccessToken = json.getString("access_token");
                if (StringUtils.isBlank(webAccessToken)) {
                    throw new RuntimeException("用户授权,授权失败");
                }
                //获取用户openid
                String openid = json.getString("openid");
                //2.通过access_token和openid拉取用户信息
                JSONObject userInfo = WeChatUtil.getUserInfo(webAccessToken, openid);
                //获取json对象中的键值对集合
                Set<Map.Entry<String, Object>> entries = userInfo.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    //把键值对作为属性设置到mmap中
                    map.put(entry.getKey(),entry.getValue());
                }
                log.info("用户信息:  "+map);
                User user = null;
                UserExample example = new UserExample();
                UserExample.Criteria cia = example.createCriteria();
                cia.andOpenIdEqualTo(openid);
                Page<User> respUser = userMapper.selectByExample(example);
                log.info(JSON.toJSONString(respUser));
                if (respUser.getResult().size()<1){
                    log.info("-------不存在,先注册------------");
                    //不存在,先注册
                    user = new User();
                    user.setOpenId(map.get("openid").toString());
                    user.setUserSex(map.get("sex")==null?-1:(Integer)map.get("sex"));
                    user.setNickName(map.get("nickname").toString());
                    user.setHeadImgurl(map.get("headimgurl").toString());
                    if (user.getOpenId().equals(adminOpenId)){
                        user.setUserRole("1");
                    }else {
                        user.setUserRole("2");
                    }
                    int result = userMapper.insert(user);
                    if (result<0){
                        return Response.fail("注册失败!");
                    }
                }else {
                    log.info("-------已存在,直接登录------------");
                    user = respUser.getResult().get(0);
                }
                return Response.ok(user);
            }
        }catch (Exception e){
            log.error(e);
        }
        return null;
    }
}
