package com.ssdkj.bbs.modular.manager;


import com.ssdkj.bbs.modular.dao.UserMapper;
import com.ssdkj.bbs.modular.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserManager {
	private static Logger log = Logger.getLogger(UserManager.class);

	@Autowired
	private UserMapper userMapper;

	@Transactional(rollbackFor = Exception.class)
	public int saveUser(User userInfo) {
		int result = 0;
		try {
			User user = userMapper.selectByPrimaryKey(userInfo.getUserId());
			if (user == null) {
				// 保存
				result = userMapper.insertSelective(userInfo);
			} else {
				// 更新
				result = userMapper.updateByPrimaryKeySelective(userInfo);
			}
		} catch (Exception e) {
			log.error("DB saveUser fail ", e);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateUser(User userInfo) {
	    int result = 0;
	    try {
	        User user = userMapper.selectByPrimaryKey(userInfo.getUserId());
	        if (user != null) {
	            // 更新
	            result = userMapper.updateByPrimaryKeySelective(userInfo);
	        }
	    } catch (Exception e) {
	        log.error("DB updateUser fail ", e);
	    }
	    return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public int removeUser(long userId) {
	    int result = 0;
	    try {
	        User user = userMapper.selectByPrimaryKey(userId);
	        if (user != null) {
	            // 删除
	            result = userMapper.deleteByPrimaryKey(userId);
	        }
	    } catch (Exception e) {
	        log.error("DB removeUser fail ", e);
	    }
	    return result;
	}
}
