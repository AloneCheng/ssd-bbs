package com.ssdkj.bbs.modular.controller;


import com.alibaba.fastjson.JSON;
import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.vo.HttpResponse;
import com.ssdkj.bbs.core.util.SnowflakeIdWorker;
import com.ssdkj.bbs.modular.api.UserService;
import com.ssdkj.bbs.modular.model.User;
import com.ssdkj.bbs.modular.model.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/user")
@RestController
@Log4j
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${ssd-bbs.url}")
    private String baseUrl;

    @Value("${ssd-bbs.wechat.url}")
    private String wechatLoginUrl;

    @RequestMapping("/auth")
    @ResponseBody
    public void auth(String code, String state, HttpServletResponse response) {
        try {
            String url = "";
            Response<User> userResp = userService.auth(code, state);
            if (userResp.isOk() && userResp.getResult() != null) {
                url = baseUrl + "?userId=" + userResp.getResult().getUserId();
            } else {
                url =wechatLoginUrl;
            }
            log.info("授权处理后跳转地址: "+url);
            response.sendRedirect(url);
        } catch (IOException e) {
            log.error(e);
        }
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse addUser(User user) {
        log.info("Invoke userService.saveUser,input param: " + JSON.toJSONString(user));
//        user.setUserId(SnowflakeIdWorker.getID());
        Response<Boolean> response = userService.addUser(user);
        log.info("Invoke userService.saveUser,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse updateUser(User user) {
        log.info("Invoke userService.updateUser,input param: " + JSON.toJSONString(user));
        Response<Boolean> response = userService.updateUser(user);
        log.info("Invoke userService.updateUser,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse removeUser(long userId) {
        log.info("Invoke userService.removeUser,input param: " + JSON.toJSONString(userId));
        Response<Boolean> response = userService.removeUser(userId);
        log.info("Invoke userService.removeUser,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse queryUser(long userId) {
        log.info("Invoke userService.queryUser,input param: " + userId);
        Response<User> response = userService.queryUser(userId);
        log.info("Invoke userService.queryUser,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }

    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse queryUsers(User user) {
        log.info("Invoke userService.queryUsers,input param: " + JSON.toJSONString(user));
        Response<PageList<User>> response = userService.queryUsers(user);
        log.info("Invoke userService.queryUsers,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }
}
