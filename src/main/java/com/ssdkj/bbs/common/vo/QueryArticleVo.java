package com.ssdkj.bbs.common.vo;

import com.ssdkj.bbs.modular.model.Article;
import lombok.Data;

/**
 * 分页查询对象基类
 * @date 2017/01/06 15:22
 */
@Data
public class QueryArticleVo extends Article{
    /**
     * 点赞人ID
     */
    private Long parseAuthorId;

    public Long getParseAuthorId() {
        return parseAuthorId;
    }

    public void setParseAuthorId(Long parseAuthorId) {
        this.parseAuthorId = parseAuthorId;
    }
}
