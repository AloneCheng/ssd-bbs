package com.ssdkj.bbs.modular.service;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.common.enums.BbsCenterEnum;
import com.ssdkj.bbs.core.util.StringUtils;
import com.ssdkj.bbs.modular.api.ParseRecordService;
import com.ssdkj.bbs.modular.dao.ParseRecordMapper;
import com.ssdkj.bbs.modular.manager.ParseRecordManager;
import com.ssdkj.bbs.modular.model.ParseRecord;
import com.ssdkj.bbs.modular.model.ParseRecordExample;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class ParseRecordServiceImpl implements ParseRecordService {

    @Autowired
    ParseRecordMapper parseRecordMapper;

    @Autowired
    private ParseRecordManager manager;


    @Override
    public Response<ParseRecord> queryParseRecord(ParseRecord parseRecord) {
        if (parseRecord.getArticleId()==null || parseRecord.getParseAuthorId()==null) {
            return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
        }
        Page<ParseRecord> parseRecordPage = null;
        //默认值
        if (parseRecord.getPageNum() < 1) {
            parseRecord.setPageNum(1);
        }
        if (parseRecord.getPageSize() < 1) {
            parseRecord.setPageSize(15);
        }
        if (parseRecord.getPageSize() > 0) {
            PageHelper.startPage(parseRecord.getPageNum(), parseRecord.getPageSize());
        }
        try {
            ParseRecordExample example = new ParseRecordExample();
            ParseRecordExample.Criteria cia = example.createCriteria();
            if (parseRecord.getArticleId()!=null) {
                cia.andArticleIdEqualTo(parseRecord.getArticleId());
            }
            if (parseRecord.getParseAuthorId()!=null) {
                cia.andParseAuthorIdEqualTo(parseRecord.getParseAuthorId());
            }
            parseRecordPage = parseRecordMapper.selectByExample(example);
        } catch (Exception e) {
            log.error("select ParseRecordById error", e);
            return Response.fail("error.get.ParseRecord.byId", e.getMessage());
        }
        log.info("--------"+parseRecordPage);
        return Response.ok(parseRecordPage.getTotal()==0?null:parseRecordPage.getResult().get(0));
    }


    @Override
    public Response<Boolean> addParseRecord(ParseRecord parseRecord) {
        log.info("Input param,ParseRecord: " + JSON.toJSONString(parseRecord));
        try {
            if (parseRecord == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.saveParseRecord(parseRecord);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.ParseRecord.saveParseRecord", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }

    @Override
    public Response<Boolean> removeParseRecord(long parseRecordId) {
        log.info("Input param,ParseRecord: " + parseRecordId);
        try {
            if (parseRecordId <= 0 ) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.removeParseRecord(parseRecordId);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.ParseRecord.removeParseRecord", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }

    @Override
    public Response<Boolean> updateParseRecord(ParseRecord parseRecord) {
        log.info("Input param,ParseRecord: " + JSON.toJSONString(parseRecord));
        try {
            if (parseRecord == null || parseRecord.getParseId() == null) {
                return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
            }
            int reslut = manager.updateParseRecord(parseRecord);
            if (reslut < 1) {
                return Response.fail(BbsCenterEnum.insert_memberAttr_error.getCode(), BbsCenterEnum.insert_memberAttr_error.getMessage());
            }
        } catch (Exception e) {
            log.error("error.ParseRecord.updateParseRecord", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
        return Response.ok(true);
    }

    @Override
    public Response<List<ParseRecord>> parseByArticleIds(Long parseAuthorId,List<Long> articleIds) {
        try {
            ParseRecordExample example = new ParseRecordExample();
            ParseRecordExample.Criteria cia = example.createCriteria();
            if (articleIds!=null && articleIds.size()>0) {
                cia.andArticleIdIn(articleIds);
            }else {
                return Response.fail("批量查询是否点赞,文章ID不能为空");
            }
            if (parseAuthorId!=null) {
                cia.andParseAuthorIdEqualTo(parseAuthorId);
            }else {
                return Response.fail("批量查询是否点赞,点赞人ID不能为空");
            }
            Page<ParseRecord> pageResp= parseRecordMapper.selectByExample(example);
            return Response.ok(pageResp.getResult());
        }catch (Exception e){
            log.error("error.ParseRecord.isParse", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
    }

    @Override
    public Response<Boolean> isParse(ParseRecord parseRecord) {
        try {
            ParseRecordExample example = new ParseRecordExample();
            ParseRecordExample.Criteria cia = example.createCriteria();
            if (parseRecord.getArticleId()!=null) {
                cia.andArticleIdEqualTo(parseRecord.getArticleId());
            }
            if (parseRecord.getParseAuthorId()!=null) {
                cia.andParseAuthorIdEqualTo(parseRecord.getParseAuthorId());
            }
            int result = parseRecordMapper.countByExample(example);
            if (result>0){
                return Response.ok(true);
            }else {
                return Response.ok(false);
            }
        }catch (Exception e){
            log.error("error.ParseRecord.isParse", e);
            return Response.fail(BbsCenterEnum.exception_error.getCode(), BbsCenterEnum.exception_error.getMessage());
        }
    }
}
