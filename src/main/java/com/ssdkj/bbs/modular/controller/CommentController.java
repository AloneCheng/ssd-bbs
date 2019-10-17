package com.ssdkj.bbs.modular.controller;

import com.alibaba.fastjson.JSON;
import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.vo.HttpResponse;
import com.ssdkj.bbs.core.util.SnowflakeIdWorker;
import com.ssdkj.bbs.modular.api.ArticleService;
import com.ssdkj.bbs.modular.api.CommentService;
import com.ssdkj.bbs.modular.model.Comment;
import com.ssdkj.bbs.modular.model.Comment;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/comment")
@RestController
@Log4j
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse addComment(@RequestBody Comment comment) {
        log.info("Invoke commentService.saveComment,input param: " + JSON.toJSONString(comment));
//        comment.setCommentId(SnowflakeIdWorker.getID());
        Response<Boolean> response = commentService.addComment(comment);
        log.info("Invoke commentService.saveComment,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse updateComment(@RequestBody Comment comment) {
        log.info("Invoke commentService.updateComment,input param: " + JSON.toJSONString(comment));
        Response<Boolean> response = commentService.updateComment(comment);
        log.info("Invoke commentService.updateComment,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse removeComment(@RequestBody Comment comment) {
        log.info("Invoke commentService.removeComment,input param: " + JSON.toJSONString(comment.getCommentId()));
        Response<Boolean> response = commentService.removeComment(comment.getCommentId());
        log.info("Invoke commentService.removeComment,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse queryComment(@RequestBody Comment comment) {
        log.info("Invoke commentService.queryComment,input param: " + comment.getCommentId());
        Response<Comment> response = commentService.queryComment(comment.getCommentId());
        log.info("Invoke commentService.queryComment,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }


    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse queryComments(@RequestBody Comment comment) {
        log.info("Invoke commentService.queryComments,input param: " + JSON.toJSONString(comment));
        Response<PageList<Comment>> response = commentService.queryComments(comment);
        log.info("Invoke commentService.queryComments,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }

}