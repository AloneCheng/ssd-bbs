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

//    @Override
//    public Response<PageList<ParseRecord>> queryParseRecords(ParseRecord seacher) {
//        {
//            PageList<ParseRecord> pageList = null;
//            try {
//                log.info("Input param,ParseRecordSeacher: " + JSON.toJSONString(seacher));
//                if (seacher == null) {
//                    return Response.fail(BbsCenterEnum.request_params_null.getCode(), BbsCenterEnum.request_params_null.getMessage());
//                }
//                //默认值
//                if (seacher.getPageNum() < 1) {
//                    seacher.setPageNum(1);
//                }
//                if (seacher.getPageSize() < 1) {
//                    seacher.setPageSize(15);
//                }
//                if (seacher.getPageSize() > 0) {
//                    PageHelper.startPage(seacher.getPageNum(), seacher.getPageSize());
//                }
//                ParseRecordExample example = new ParseRecordExample();
//                ParseRecordExample.Criteria cia = example.createCriteria();
//                if (seacher.getParseRecordType() != null) {
//                    cia.andParseRecordTypeEqualTo(seacher.getParseRecordType());
//                }
//                example.setOrderByClause("parseRecordCreateTime DESC");
//                Page<ParseRecord> parseRecordPage = parseRecordMapper.selectByExample(example);
//                pageList = new PageList<ParseRecord>(parseRecordPage.getTotal(), seacher.getPageSize(), seacher.getPageNum(), parseRecordPage.getResult());
//            } catch (Exception e) {
//                log.error("error.get.parseRecord.list", e);
//                return Response.fail("error.get.parseRecord.list", e.getMessage());
//            }
//            return Response.ok(pageList);
//        }
//    }

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
}
