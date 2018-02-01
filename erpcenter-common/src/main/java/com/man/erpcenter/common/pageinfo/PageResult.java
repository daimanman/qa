package com.man.erpcenter.common.pageinfo;

import java.util.List;

/**
 * 返回给前端的业务数据
 * @author daixm
 *
 * @param <T>
 */
public class PageResult<T> implements java.io.Serializable {

	private static final long serialVersionUID = 3047699764260304489L;

	/**
	 * 当前页
	 */
	public int page;
	
	/**
	 * 每页大小
	 */
	public int pageSize;
	
	/**
	 * 总记录数
	 */
	public int total;
	
	/**
	 * 分页时查询的结果集
	 */
	public List<T> datas;
	
	/**
	 * 不带分页时的数据
	 */
	public T value;
	
	/**
	 * 成功与否标志 预留 0 成功 非0 失败
	 */
	public int code;
	
	/**
	 * 提示信息
	 */
	public String msg;
	
	/**
	 * 根据业务需要,返回前端页面的参数
	 */
	public Object  params;
	
	/**
	 * 是否还有下一页 预留 手机端可能会用到
	 */
	public  boolean hasNext;
	

	public boolean isHasNext() {
		//如果当前页为最后一页 则说明没有下一页了
		if(page >= getTotalPage() ){
			setHasNext(false);
		}else{
			setHasNext(true);
		}
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}
	
	/**
	 * 计算总页数
	 * @return
	 */
	public int getTotalPage(){
		if(pageSize == 0){
			pageSize = 15;
		}
		int totalPage = ( getTotal()+getPageSize()-1)/getPageSize();
		return totalPage;
	}

	public Object getParams() {
		return params;
	}

	public void setParams(Object params) {
		this.params = params;
	}
	
	
	
	
}
