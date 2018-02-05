package com.man.erpcenter.elasticsearch.basequery;

/**
 * 单个查询参数类型
 * 
 * @author daixm
 *
 */
public class QueryItem implements java.io.Serializable {

	private static final long serialVersionUID = -996909197003826137L;
	/**
	 * 字段名称
	 */
	public String field;

	/**
	 * 搜索字段值
	 */
	public Object value;

	/**
	 * 默认为in操作
	 */
	public String matchType = QueryTypeEnum.IN.getType();
	
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getMatchType() {
		return matchType;
	}

	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}

	/**
	 * 判断是否需要加入到过滤条件中
	 * 
	 * @return
	 */
	public boolean isNeedSearch() {
		return (field != null && !field.trim().equals("")) && 
			(value != null && !value.toString().trim().equals(""));
	}
	
}
