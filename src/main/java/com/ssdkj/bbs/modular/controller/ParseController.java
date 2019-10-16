package com.ssdkj.bbs.modular.controller;


import com.alibaba.fastjson.JSON;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.vo.HttpResponse;
import com.ssdkj.bbs.common.vo.QueryArticleVo;
import com.ssdkj.bbs.core.util.SnowflakeIdWorker;
import com.ssdkj.bbs.modular.api.ArticleService;
import com.ssdkj.bbs.modular.api.ParseRecordService;
import com.ssdkj.bbs.modular.model.Article;
import com.ssdkj.bbs.modular.model.ParseRecord;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/parse")
@RestController
@Log4j
public class ParseController {


    @Autowired
    private ParseRecordService parseRecordService;
    @Autowired
    private ArticleService articleService;




    @RequestMapping(value = "/count", method = RequestMethod.POST)
    @ResponseBody
    public HttpResponse parseArticle(@RequestBody ParseRecord parseRecord) {
        log.info("Invoke articleService.parseArticle,input param: " + JSON.toJSONString(parseRecord));
        //1.点赞记录表操作
        Article article = new Article();
        article.setArticleId(parseRecord.getArticleId());
        QueryArticleVo articleVo = new QueryArticleVo();
        articleVo.setArticleId(parseRecord.getArticleId());
        Response<Article> articleResponse = articleService.queryArticle(articleVo);
        if (!articleResponse.isOk() || articleResponse.getResult()==null){
            return HttpResponse.fail(null,null,"未查询到需要点赞的文章!");
        }
        Response<ParseRecord> recordResponse = parseRecordService.queryParseRecord(parseRecord);
        Integer parseCount = null;
        if (recordResponse.isOk()) {
            if (recordResponse.getResult() != null) {
                //有记录说明是取消点赞
                parseRecordService.removeParseRecord(recordResponse.getResult().getParseId());
                parseCount = articleResponse.getResult().getArticleParseCount() - 1 < 0 ? 0 : articleResponse.getResult().getArticleParseCount() - 1;
            } else {
                //没有就是点赞,添加记录
                parseRecord.setParseId(SnowflakeIdWorker.getID());
                parseRecordService.addParseRecord(parseRecord);
                parseCount = articleResponse.getResult().getArticleParseCount() + 1;
            }
        }else {
            return HttpResponse.convert(recordResponse);
        }
        article.setArticleParseCount(parseCount);
        //2.更新文章表的点赞数量
        Response<Boolean> response = articleService.updateArticle(article);
        log.info("Invoke articleService.parseArticle,resp: " + JSON.toJSONString(response));
        return HttpResponse.ok(parseCount);
    }


}

