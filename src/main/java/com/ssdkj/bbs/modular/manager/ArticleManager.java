package com.ssdkj.bbs.modular.manager;


import com.qiniu.common.QiniuException;
import com.ssdkj.bbs.common.constants.QiNiuVariable;
import com.ssdkj.bbs.core.util.QiniuUtils;
import com.ssdkj.bbs.core.util.StringUtils;
import com.ssdkj.bbs.modular.dao.ArticleMapper;
import com.ssdkj.bbs.modular.model.Article;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ArticleManager {
	private static Logger log = Logger.getLogger(ArticleManager.class);

	@Autowired
	private ArticleMapper articleMapper;

	@Transactional(rollbackFor = Exception.class)
	public int saveArticle(Article articleInfo) {
		int result = 0;
		try {
			Article article = articleMapper.selectByPrimaryKey(articleInfo.getArticleId());
			if (article == null) {
				// 保存
				result = articleMapper.insertSelective(articleInfo);
			} else {
				// 更新
				result = articleMapper.updateByPrimaryKeySelective(articleInfo);
			}
		} catch (Exception e) {
			log.error("DB saveArticle fail ", e);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateArticle(Article articleInfo) {
	    int result = 0;
	    try {
	        Article article = articleMapper.selectByPrimaryKey(articleInfo.getArticleId());
	        if (article != null) {
	            // 更新
	            result = articleMapper.updateByPrimaryKeySelective(articleInfo);
	        }
	    } catch (Exception e) {
	        log.error("DB updateArticle fail ", e);
	    }
	    return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public int removeArticle(long articleId) {
	    int result = 0;
	    try {
	        Article article = articleMapper.selectByPrimaryKey(articleId);
	        if (article != null) {
	            // 删除数据库文章
	            result = articleMapper.deleteByPrimaryKey(articleId);
				//删除七牛云图片
				if (StringUtils.isNotBlank(article.getArticleImgURL())){
					String picUrlResult = article.getArticleImgURL();
					String[] pics = picUrlResult.split("\\|");
					QiniuUtils.deletePics(pics);
				}
	        }
	    }catch (Exception e) {
			log.error("DB removeArticle fail ", e);
		}
	    return result;
	}
}
