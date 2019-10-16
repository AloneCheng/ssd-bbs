package com.ssdkj.bbs.modular.model;

import com.ssdkj.bbs.common.annotation.Skip;

import java.io.Serializable;

public class Article implements Serializable {

    private int isParse;

    public int getIsParse() {
        return isParse;
    }

    public void setIsParse(int isParse) {
        this.isParse = isParse;
    }

    @Skip
    private int pageNum;
    @Skip
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleId
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Long articleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleTag
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articleTag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleType
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articleType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleAuthorId
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Long articleAuthorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.nickName
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private String nickName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.headImgurl
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private String headImgurl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleCommentCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articleCommentCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleViewCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articleViewCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleParseCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articleParseCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articlePutTop
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articlePutTop;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleSequence
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articleSequence;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleCreateTime
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private String articleCreateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleUpdateTime
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private String articleUpdateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleCommentable
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articleCommentable;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleImgURL
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private String articleImgURL;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleStatus
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private Integer articleStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column article.articleContent
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    private String articleContent;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleId
     *
     * @return the value of article.articleId
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleId
     *
     * @param articleId the value for article.articleId
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleTag
     *
     * @return the value of article.articleTag
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticleTag() {
        return articleTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleTag
     *
     * @param articleTag the value for article.articleTag
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleTag(Integer articleTag) {
        this.articleTag = articleTag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleType
     *
     * @return the value of article.articleType
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticleType() {
        return articleType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleType
     *
     * @param articleType the value for article.articleType
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleAuthorId
     *
     * @return the value of article.articleAuthorId
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Long getArticleAuthorId() {
        return articleAuthorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleAuthorId
     *
     * @param articleAuthorId the value for article.articleAuthorId
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleAuthorId(Long articleAuthorId) {
        this.articleAuthorId = articleAuthorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.nickName
     *
     * @return the value of article.nickName
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.nickName
     *
     * @param nickName the value for article.nickName
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.headImgurl
     *
     * @return the value of article.headImgurl
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public String getHeadImgurl() {
        return headImgurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.headImgurl
     *
     * @param headImgurl the value for article.headImgurl
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setHeadImgurl(String headImgurl) {
        this.headImgurl = headImgurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleCommentCount
     *
     * @return the value of article.articleCommentCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticleCommentCount() {
        return articleCommentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleCommentCount
     *
     * @param articleCommentCount the value for article.articleCommentCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleCommentCount(Integer articleCommentCount) {
        this.articleCommentCount = articleCommentCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleViewCount
     *
     * @return the value of article.articleViewCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticleViewCount() {
        return articleViewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleViewCount
     *
     * @param articleViewCount the value for article.articleViewCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleViewCount(Integer articleViewCount) {
        this.articleViewCount = articleViewCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleParseCount
     *
     * @return the value of article.articleParseCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticleParseCount() {
        return articleParseCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleParseCount
     *
     * @param articleParseCount the value for article.articleParseCount
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleParseCount(Integer articleParseCount) {
        this.articleParseCount = articleParseCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articlePutTop
     *
     * @return the value of article.articlePutTop
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticlePutTop() {
        return articlePutTop;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articlePutTop
     *
     * @param articlePutTop the value for article.articlePutTop
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticlePutTop(Integer articlePutTop) {
        this.articlePutTop = articlePutTop;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleSequence
     *
     * @return the value of article.articleSequence
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticleSequence() {
        return articleSequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleSequence
     *
     * @param articleSequence the value for article.articleSequence
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleSequence(Integer articleSequence) {
        this.articleSequence = articleSequence;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleCreateTime
     *
     * @return the value of article.articleCreateTime
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public String getArticleCreateTime() {
        return articleCreateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleCreateTime
     *
     * @param articleCreateTime the value for article.articleCreateTime
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleCreateTime(String articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleUpdateTime
     *
     * @return the value of article.articleUpdateTime
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public String getArticleUpdateTime() {
        return articleUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleUpdateTime
     *
     * @param articleUpdateTime the value for article.articleUpdateTime
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleUpdateTime(String articleUpdateTime) {
        this.articleUpdateTime = articleUpdateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleCommentable
     *
     * @return the value of article.articleCommentable
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticleCommentable() {
        return articleCommentable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleCommentable
     *
     * @param articleCommentable the value for article.articleCommentable
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleCommentable(Integer articleCommentable) {
        this.articleCommentable = articleCommentable;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleImgURL
     *
     * @return the value of article.articleImgURL
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public String getArticleImgURL() {
        return articleImgURL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleImgURL
     *
     * @param articleImgURL the value for article.articleImgURL
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleImgURL(String articleImgURL) {
        this.articleImgURL = articleImgURL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleStatus
     *
     * @return the value of article.articleStatus
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public Integer getArticleStatus() {
        return articleStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleStatus
     *
     * @param articleStatus the value for article.articleStatus
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column article.articleContent
     *
     * @return the value of article.articleContent
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public String getArticleContent() {
        return articleContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column article.articleContent
     *
     * @param articleContent the value for article.articleContent
     *
     * @mbggenerated Thu Oct 10 10:23:40 CST 2019
     */
    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}