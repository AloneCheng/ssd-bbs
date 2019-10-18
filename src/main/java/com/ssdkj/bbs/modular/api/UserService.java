package com.ssdkj.bbs.modular.api;

import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.modular.model.User;

public interface UserService {
    public Response<User> queryUser(long userId);

    public Response<PageList<User>> queryUsers(User user);

    public Response<Boolean> addUser(User user);

    public Response<Boolean> removeUser(long userId);

    public Response<Boolean> updateUser(User user);

    public Response<User> auth(String code, String state);

}
