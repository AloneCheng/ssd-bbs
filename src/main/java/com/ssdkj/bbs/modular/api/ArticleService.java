package com.ssdkj.bbs.modular.api;

import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.vo.QueryArticleVo;
import com.ssdkj.bbs.modular.model.Article;

public interface ArticleService {
    public Response<Article> queryArticle(QueryArticleVo articleVo);

    public Response<PageList<Article>> queryArticles(Article article);

    public Response<Boolean> addArticle(Article article);

    public Response<Boolean> removeArticle(long articleId);

    public Response<Boolean> updateArticle(Article article);

    public Response<Boolean> putTop(QueryArticleVo articleVo);
}
