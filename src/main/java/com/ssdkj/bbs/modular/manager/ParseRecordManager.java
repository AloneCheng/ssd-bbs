package com.ssdkj.bbs.modular.manager;


import com.ssdkj.bbs.modular.dao.ParseRecordMapper;
import com.ssdkj.bbs.modular.model.ParseRecord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ParseRecordManager {
	private static Logger log = Logger.getLogger(ParseRecordManager.class);

	@Autowired
	private ParseRecordMapper parseRecordMapper;

	@Transactional(rollbackFor = Exception.class)
	public int saveParseRecord(ParseRecord parseRecordInfo) {
		int result = 0;
		try {
			ParseRecord parseRecord = parseRecordMapper.selectByPrimaryKey(parseRecordInfo.getParseId());
			if (parseRecord == null) {
				// 保存
				result = parseRecordMapper.insertSelective(parseRecordInfo);
			} else {
				// 更新
				result = parseRecordMapper.updateByPrimaryKeySelective(parseRecordInfo);
			}
		} catch (Exception e) {
			log.error("DB saveParseRecord fail ", e);
		}
		return result;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public int updateParseRecord(ParseRecord parseRecordInfo) {
	    int result = 0;
	    try {
	        ParseRecord parseRecord = parseRecordMapper.selectByPrimaryKey(parseRecordInfo.getParseId());
	        if (parseRecord != null) {
	            // 更新
	            result = parseRecordMapper.updateByPrimaryKeySelective(parseRecordInfo);
	        }
	    } catch (Exception e) {
	        log.error("DB updateParseRecord fail ", e);
	    }
	    return result;
	}

	@Transactional(rollbackFor = Exception.class)
	public int removeParseRecord(long parseRecordId) {
	    int result = 0;
	    try {
	        ParseRecord parseRecord = parseRecordMapper.selectByPrimaryKey(parseRecordId);
	        if (parseRecord != null) {
	            // 删除
	            result = parseRecordMapper.deleteByPrimaryKey(parseRecordId);
	        }
	    } catch (Exception e) {
	        log.error("DB removeParseRecord fail ", e);
	    }
	    return result;
	}
}
