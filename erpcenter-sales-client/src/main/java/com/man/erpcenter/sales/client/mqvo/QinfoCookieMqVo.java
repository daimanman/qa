package com.man.erpcenter.sales.client.mqvo;

import java.util.List;
import java.util.Map;

public class QinfoCookieMqVo implements java.io.Serializable {

	private static final long serialVersionUID = 4090842421785742585L;

	public List<String> uids;

	public Map<String, Map<String, Object>> paramsMap;

	public Map<String, Map<String, String>> cookiesMap;

	public List<String> getUids() {
		return uids;
	}

	public void setUids(List<String> uids) {
		this.uids = uids;
	}

	public Map<String, Map<String, Object>> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Map<String, Object>> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public Map<String, Map<String, String>> getCookiesMap() {
		return cookiesMap;
	}

	public void setCookiesMap(Map<String, Map<String, String>> cookiesMap) {
		this.cookiesMap = cookiesMap;
	}

}
