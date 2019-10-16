package com.ssdkj.bbs.modular.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.enums.BbsCenterEnum;
import com.ssdkj.bbs.modular.api.UserService;
import com.ssdkj.bbs.modular.dao.UserMapper;
import com.ssdkj.bbs.modular.dao.UserMapper;
import com.ssdkj.bbs.modular.manager.UserManager;
import com.ssdkj.bbs.modular.model.User;
import com.ssdkj.bbs.modular.model.UserExample;
import com.ssdkj.bbs.modular.model.User;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Log4j
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;


    @Autowired
    private UserManager manager;


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
        log.info("Input param,ActivityInfo: " + JSON.toJSONString(user));
        try {
            if (user == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.saveUser(user);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.Activity.saveActivityInfo", e);
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
}
