package com.man.erpcenter.sales.client.mqvo;

import java.util.Map;

public class QemotInfoMqVo implements java.io.Serializable {

	private static final long serialVersionUID = 8296289371942539135L;

	public long querInfoId;

	public String uid;

	public Map<String, Object> qemotInfoMap;
	
	public Map<String,Object> photoInfoMap;
	
	//流言 NUM
	public int msgNum;

	//shuo num
	public int totalNum;
	
	//photo num
	public int photoNum;
	
	//imgNum
	public int imgNum;
	
	
	//表id
	public String  photoId;
	
	
	

	public int getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(int photoNum) {
		this.photoNum = photoNum;
	}

	public int getImgNum() {
		return imgNum;
	}

	public void setImgNum(int imgNum) {
		this.imgNum = imgNum;
	}

	public int getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public long getQuerInfoId() {
		return querInfoId;
	}

	public void setQuerInfoId(long querInfoId) {
		this.querInfoId = querInfoId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Map<String, Object> getQemotInfoMap() {
		return qemotInfoMap;
	}

	public void setQemotInfoMap(Map<String, Object> qemotInfoMap) {
		this.qemotInfoMap = qemotInfoMap;
	}

}
