package com.ssdkj.bbs.modular.controller;


import com.alibaba.fastjson.JSON;
import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.vo.HttpResponse;
import com.ssdkj.bbs.common.vo.QueryArticleVo;
import com.ssdkj.bbs.core.util.SnowflakeIdWorker;
import com.ssdkj.bbs.modular.api.ArticleService;
import com.ssdkj.bbs.modular.model.Article;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/article")
@RestController
@Log4j
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse addArticle(@RequestBody Article article) {
        log.info("Invoke articleService.saveArticle,input param: " + JSON.toJSONString(article));
        Response<Boolean> response = articleService.addArticle(article);
        log.info("Invoke articleService.saveArticle,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }



    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse updateArticle(@RequestBody Article article) {
        log.info("Invoke articleService.updateArticle,input param: " + JSON.toJSONString(article));
        Response<Boolean> response = articleService.updateArticle(article);
        log.info("Invoke articleService.updateArticle,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse removeArticle(@RequestBody Article article) {
        log.info("Invoke articleService.removeArticle,input param: " + JSON.toJSONString(article.getArticleId()));
        Response<Boolean> response = articleService.removeArticle(article.getArticleId());
        log.info("Invoke articleService.removeArticle,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse queryArticle(@RequestBody QueryArticleVo articleVo) {
        log.info("Invoke articleService.queryArticle,input param: " + articleVo);
        Response<Article> response = articleService.queryArticle(articleVo);
        log.info("Invoke articleService.queryArticle,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }





    @RequestMapping(value = "/queryList", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse queryArticles(@RequestBody QueryArticleVo articleVo) {
        log.info("Invoke articleService.queryArticles,input param: " + JSON.toJSONString(articleVo));
        Response<PageList<Article>> response = articleService.queryArticles(articleVo);
        log.info("Invoke articleService.queryArticles,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }


    @RequestMapping(value = "/putTop", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse putTop(@RequestBody QueryArticleVo articleVo) {
        log.info("Invoke articleService.putTop,input param: " + articleVo);
        Response<Boolean> response = articleService.putTop(articleVo);
        log.info("Invoke articleService.putTop,resp: " + JSON.toJSONString(response));
        return HttpResponse.convert(response);
    }


}
