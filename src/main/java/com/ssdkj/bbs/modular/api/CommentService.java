package com.ssdkj.bbs.modular.api;

import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.modular.model.Comment;
import com.ssdkj.bbs.modular.model.Comment;

public interface CommentService {

    public Response<Comment> queryComment(long  commentId);

    public Response<PageList<Comment>> queryComments(Comment comment);

    public Response<Boolean> addComment(Comment comment);

    public Response<Boolean> removeComment(long commentId);

    public Response<Boolean> updateComment(Comment comment);
}
