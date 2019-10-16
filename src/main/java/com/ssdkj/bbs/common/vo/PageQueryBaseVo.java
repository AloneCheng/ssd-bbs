package com.ssdkj.bbs.common.vo;

import lombok.Data;

/**
 * 分页查询对象基类
 * @date 2017/01/06 15:22
 */
@Data
public class PageQueryBaseVo extends BaseVo{
    /**
     * 当前页码
     */
    private Integer pageNum;
    /**
     * 每页记录条数
     */
    private Integer pageSize;
}
