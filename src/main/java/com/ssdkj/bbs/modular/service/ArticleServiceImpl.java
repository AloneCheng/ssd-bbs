package com.ssdkj.bbs.modular.service;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssdkj.bbs.common.constants.QiNiuVariable;
import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.enums.BbsCenterEnum;
import com.ssdkj.bbs.common.vo.HttpResponse;
import com.ssdkj.bbs.common.vo.QueryArticleVo;
import com.ssdkj.bbs.core.util.QiniuUtils;
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

import java.util.ArrayList;
import java.util.List;

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
            //交易类型文章浏览次数+1
            if(article.getArticleType()!=1){
                articleMapper.addViewOne(articleVo);
            }
            if (articleVo.getParseAuthorId()!=null){
                ParseRecord parseRecord = new ParseRecord();
                parseRecord.setArticleId(articleVo.getArticleId());
                parseRecord.setParseAuthorId(articleVo.getParseAuthorId());
                Response<Boolean> isParse = parseRecordService.isParse(parseRecord);
                if (isParse.isOk() && isParse.getResult()==true){
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
    public Response<PageList<Article>> queryArticles(QueryArticleVo  seacher) {
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
                example.setOrderByClause("articlePutTop DESC,articleSequence desc,articleCreateTime DESC");
                Page<Article> articlePage = articleMapper.selectByExampleWithBLOBs(example);
                pageList = new PageList<Article>(articlePage.getTotal(), seacher.getPageSize(), seacher.getPageNum(), articlePage.getResult());
                log.info(JSON.toJSONString(pageList));
                log.info(pageList.getList()!=null && seacher.getParseAuthorId()!=null);
                if (pageList.getList()!=null && seacher.getParseAuthorId()!=null){
                    List<Long>  articleIds = new ArrayList<>();
                    for (Article article:articlePage.getResult()) {
                        articleIds.add(article.getArticleId());
                    }
                    Response<List<ParseRecord>> paresRecordsResp = parseRecordService.parseByArticleIds(seacher.getParseAuthorId(),articleIds);
                    log.info("parseRecordService.parseByArticleIds resp:"+JSON.toJSONString(paresRecordsResp));
                    if (paresRecordsResp.isOk() && paresRecordsResp.getResult()!=null){
                        List<ParseRecord> parseRecords = paresRecordsResp.getResult();
                        for (Article article:articlePage.getResult()) {
                            article.setIsParse(0);//默认是0
                            for (ParseRecord parseRecord:parseRecords) {
                                if (parseRecord.getArticleId()==article.getArticleId()){
                                    article.setIsParse(1);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("error.get.article.list", e);
                return Response.fail("error.get.article.list", e.getMessage());
            }
            return Response.ok(pageList);
        }
    }

    @Override
    public Response<Boolean> addArticle(Article article) {
        log.info("Input param,Article: " + JSON.toJSONString(article));
        try {
            if (article == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            if ("anonymous.jpg".equals(article.getHeadImgurl())){
                article.setHeadImgurl(QiNiuVariable.baseUrl+article.getHeadImgurl());
            }
            int reslut = manager.saveArticle(article);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.Activity.saveArticle", e);
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

    @Override
    public Response<Boolean> putTop(QueryArticleVo articleVo) {
        try {
            Article article = articleMapper.selectByPrimaryKey(articleVo.getArticleId());
            if( article!=null){
                Article articleUpdate = new Article();
                articleUpdate.setArticleId(articleVo.getArticleId());
                articleUpdate.setArticlePutTop(articleVo.getArticlePutTop());
                articleUpdate.setArticleSequence(articleVo.getArticleSequence());
                articleMapper.updateByPrimaryKeySelective(articleUpdate);
            }else {
                return Response.fail("查找不到当前文章");
            }
        }catch (Exception e){
            log.error("error.Article.putTop", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }
}
