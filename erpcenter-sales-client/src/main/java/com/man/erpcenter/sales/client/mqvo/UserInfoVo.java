package com.man.erpcenter.sales.client.mqvo;

import java.util.Map;

public class UserInfoVo implements java.io.Serializable {

	private static final long serialVersionUID = -7302498453714862259L;
	public String uid;
	public Map<String, Object> userInfoMap;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Map<String, Object> getUserInfoMap() {
		return userInfoMap;
	}

	public void setUserInfoMap(Map<String, Object> userInfoMap) {
		this.userInfoMap = userInfoMap;
	}

}
