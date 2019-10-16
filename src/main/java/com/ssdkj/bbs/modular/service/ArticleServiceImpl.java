package com.ssdkj.bbs.modular.service;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.enums.BbsCenterEnum;
import com.ssdkj.bbs.common.vo.QueryArticleVo;
import com.ssdkj.bbs.core.util.StringUtils;
import com.ssdkj.bbs.modular.api.ArticleService;
import com.ssdkj.bbs.modular.api.ParseRecordService;
import com.ssdkj.bbs.modular.dao.ArticleMapper;
import com.ssdkj.bbs.modular.manager.ArticleManager;
import com.ssdkj.bbs.modular.model.Article;
import com.ssdkj.bbs.modular.model.ArticleExample;
import com.ssdkj.bbs.modular.model.ParseRecord;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    private ArticleManager manager;

    @Autowired
    private ParseRecordService parseRecordService;
    @Override
    public Response<Article> queryArticle(QueryArticleVo articleVo) {
        Article article = null;
        try {
            article = articleMapper.selectByPrimaryKey(articleVo.getArticleId());
            if (articleVo.getParseAuthorId()!=null){
                ParseRecord parseRecord = new ParseRecord();
                parseRecord.setArticleId(articleVo.getArticleId());
                parseRecord.setParseAuthorId(articleVo.getParseAuthorId());
                Response<ParseRecord>  parseRecordResponse = parseRecordService.queryParseRecord(parseRecord);
                if (parseRecordResponse.isOk() && parseRecordResponse.getResult()!=null){
                    article.setIsParse(1);
                }else {
                    article.setIsParse(0);
                }
            }
        } catch (Exception e) {
            log.error("select ArticleById error", e);
            return Response.fail("error.get.Article.byId", e.getMessage());
        }
        return Response.ok(article);
    }

    @Override
    public Response<PageList<Article>> queryArticles(Article seacher) {
        {
            PageList<Article> pageList = null;
            try {
                log.info("Input param,ArticleSeacher: " + JSON.toJSONString(seacher));
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
                ArticleExample example = new ArticleExample();
                ArticleExample.Criteria cia = example.createCriteria();
                if (seacher.getArticleType() != null) {
                    cia.andArticleTypeEqualTo(seacher.getArticleType());
                }
                if (seacher.getArticleTag() != null) {
                    cia.andArticleTagEqualTo(seacher.getArticleTag());
                }
                example.setOrderByClause("articleCreateTime DESC");
                Page<Article> articlePage = articleMapper.selectByExampleWithBLOBs(example);
                pageList = new PageList<Article>(articlePage.getTotal(), seacher.getPageSize(), seacher.getPageNum(), articlePage.getResult());
            } catch (Exception e) {
                log.error("error.get.article.list", e);
                return Response.fail("error.get.article.list", e.getMessage());
            }
            return Response.ok(pageList);
        }
    }

    @Override
    public Response<Boolean> addArticle(Article article) {
        log.info("Input param,ActivityInfo: " + JSON.toJSONString(article));
        try {
            if (article == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.saveArticle(article);
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
    public Response<Boolean> removeArticle(long articleId) {
        log.info("Input param,Article: " + articleId);
        try {
            if (articleId <= 0 ) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.removeArticle(articleId);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.Article.removeArticle", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }

    @Override
    public Response<Boolean> updateArticle(Article article) {
        log.info("Input param,Article: " + JSON.toJSONString(article));
        try {
            if (article == null || article.getArticleId() == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.updateArticle(article);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.Article.updateArticle", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }
}
