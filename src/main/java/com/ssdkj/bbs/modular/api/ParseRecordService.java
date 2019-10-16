package com.ssdkj.bbs.modular.api;

import com.ssdkj.bbs.common.dto.PageList;
import com.ssdkj.bbs.common.dto.Response;
import com.ssdkj.bbs.modular.model.ParseRecord;

public interface ParseRecordService {
    public Response<ParseRecord> queryParseRecord(ParseRecord parseRecord);

//    public Response<PageList<ParseRecord>> queryParseRecords(ParseRecord parseRecord);

    public Response<Boolean> addParseRecord(ParseRecord parseRecord);

    public Response<Boolean> removeParseRecord(long parseRecordId);

    public Response<Boolean> updateParseRecord(ParseRecord parseRecord);
}
