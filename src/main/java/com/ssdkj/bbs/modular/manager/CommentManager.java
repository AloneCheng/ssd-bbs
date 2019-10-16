package com.ssdkj.bbs.modular.manager;


import com.ssdkj.bbs.modular.dao.CommentMapper;
import com.ssdkj.bbs.modular.model.Comment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommentManager {
	private static Logger log = Logger.getLogger(CommentManager.class);

	@Autowired
	private CommentMapper commentMapper;

	@Transactional(rollbackFor = Exception.class)
	public int saveComment(Comment commentInfo) {
		int result = 0;
		try {
			Comment comment = commentMapper.selectByPrimaryKey(commentInfo.getCommentId());
			if (comment == null) {
				// 保存
				result = commentMapper.insertSelective(commentInfo);
			} else {
				// 更新
				result = commentMapper.updateByPrimaryKeySelective(commentInfo);
			}
		} catch (Exception e) {
			log.error("DB saveComment fail ", e);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateComment(Comment commentInfo) {
	    int result = 0;
	    try {
	        Comment comment = commentMapper.selectByPrimaryKey(commentInfo.getCommentId());
	        if (comment != null) {
	            // 更新
	            result = commentMapper.updateByPrimaryKeySelective(commentInfo);
	        }
	    } catch (Exception e) {
	        log.error("DB updateComment fail ", e);
	    }
	    return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public int removeComment(long commentId) {
	    int result = 0;
	    try {
	        Comment comment = commentMapper.selectByPrimaryKey(commentId);
	        if (comment != null) {
	            // 删除
	            result = commentMapper.deleteByPrimaryKey(commentId);
	        }
	    } catch (Exception e) {
	        log.error("DB removeComment fail ", e);
	    }
	    return result;
	}
}
