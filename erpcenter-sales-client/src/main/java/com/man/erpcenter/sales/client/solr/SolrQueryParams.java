package com.man.erpcenter.sales.client.solr;

import java.util.Map;

public class SolrQueryParams implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8423088634376919463L;

	public long start = 0;
	
	public long rows = 20;
	
	public String q = "*:*";
	
	public Map<String,Object> bizParams;

	
	public Map<String, Object> getBizParams() {
		return bizParams;
	}

	public void setBizParams(Map<String, Object> bizParams) {
		this.bizParams = bizParams;
	}

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

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}
	
	
}
