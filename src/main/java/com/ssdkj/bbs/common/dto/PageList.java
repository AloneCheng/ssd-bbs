package com.ssdkj.bbs.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PageList<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public final static int DEFAULT_PAGE_SIZE = 10;

    /** 总数 */
	private long total;
	/** 每页条数 */
	private int pageSize;
	/** 页码 */
	private int pageNum;
	/** 查询结果 */
    private List<T> list;
    
    public PageList() {
		super();
		this.total = 0;
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.pageNum = 1;
		this.list = new ArrayList<T>();
	}

	public PageList(long total, List<T> list) {
        this.total = total;
        this.list = list;
        
        this.pageSize = list != null ? list.size() : DEFAULT_PAGE_SIZE;
		this.pageNum = 1;
    }
    
	public PageList(long total, int pageSize, int pageNum, List<T> list) {
		super();
		this.total = total;
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.list = list;
	}

	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
