package com.ssdkj.bbs.modular.service;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.enums.BbsCenterEnum;
import com.ssdkj.bbs.modular.api.CommentService;
import com.ssdkj.bbs.modular.dao.ArticleMapper;
import com.ssdkj.bbs.modular.dao.CommentMapper;
import com.ssdkj.bbs.modular.manager.CommentManager;
import com.ssdkj.bbs.modular.model.Article;
import com.ssdkj.bbs.modular.model.Comment;
import com.ssdkj.bbs.modular.model.CommentExample;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleMapper articleMapper;


    @Autowired
    private CommentManager manager;


    @Override
    public Response<Comment> queryComment(long commentId) {
        Comment comment = null;
        try {
            comment = commentMapper.selectByPrimaryKey(commentId);
        } catch (Exception e) {
            log.error("select CommentById error", e);
            return Response.fail("error.get.Comment.byId", e.getMessage());
        }
        return Response.ok(comment);
    }

    @Override
    public Response<PageList<Comment>> queryComments(Comment seacher) {
        {
            PageList<Comment> pageList = null;
            try {
                log.info("Input param,CommentSeacher: " + JSON.toJSONString(seacher));
                //TODO 查询文章的所有评论就会把浏览量加1
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

                CommentExample example = new CommentExample();
                CommentExample.Criteria cia = example.createCriteria();

                if (seacher.getArticleId() != null) {
                    cia.andArticleIdEqualTo(seacher.getArticleId());
                }
                example.setOrderByClause("commentCreateTime DESC");
                Page<Comment> commentPage = commentMapper.selectByExample(example);
                pageList = new PageList<Comment>(commentPage.getTotal(), seacher.getPageSize(), seacher.getPageNum(), commentPage.getResult());
            } catch (Exception e) {
                log.error("error.get.comment.list", e);
                return Response.fail("error.get.comment.list", e.getMessage());
            }
            return Response.ok(pageList);
        }
    }

    @Override
    public Response<Boolean> addComment(Comment comment) {
        log.info("Input param,Comment: " + JSON.toJSONString(comment));
        try {
            if (comment == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.saveComment(comment);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
            //文章评论次数+1
            Article article = new Article();
            article.setArticleId(comment.getArticleId());
            articleMapper.addCommentOne(article);
        } catch (Exception e) {
            log.error("error.Activity.saveComment", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }

    @Override
    public Response<Boolean> removeComment(long commentId) {
        log.info("Input param,Comment: " + commentId);
        try {
            if (commentId <= 0 ) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.removeComment(commentId);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.Comment.removeComment", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }

    @Override
    public Response<Boolean> updateComment(Comment comment) {
        log.info("Input param,Comment: " + JSON.toJSONString(comment));
        try {
            if (comment == null || comment.getCommentId() == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.updateComment(comment);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.Comment.updateComment", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }
}
