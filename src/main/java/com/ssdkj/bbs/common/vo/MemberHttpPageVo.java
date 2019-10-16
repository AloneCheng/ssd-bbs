package com.ssdkj.bbs.common.vo;

import lombok.Data;

/**
 * member-http分页数据对象
 * @date 2017/01/04 17:08
 */
@Data
public class MemberHttpPageVo<T> extends BaseVo{
    private T data;
    private long totals;
    private Integer currentPage;
    private Integer pageSize;
}
