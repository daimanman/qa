package com.man.erpcenter.sales.client.solr;

import java.util.List;

public class SolrSearchResult<T> {

	public long start;
	
	public long rows;
	
	public long total;
	
	public List<T> datas;
	
	public long pages;
	
	/**
	 * 不带分页
	 */
	public T value;

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getRows() {
		return rows;
	}

	public void setRows(long rows) {
		this.rows = rows;
	}

	

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPages() {
		if(rows > 0){
			return  (total+rows-1)/rows;
		}
		return 0;
		
	}

	

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	
	
}
