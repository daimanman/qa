package com.man.erpcenter.sales.client.po;

public class QuserInfoRetry implements java.io.Serializable {

	private static final long serialVersionUID = -3874657422176171427L;
	
	public long id;
	
	public long userinfoid;
	
	public String uid;
	
	public int flag;
	
	public int msgflag;
	
	public int emotflag;
	
	

	public int getMsgflag() {
		return msgflag;
	}

	public void setMsgflag(int msgflag) {
		this.msgflag = msgflag;
	}

	public int getEmotflag() {
		return emotflag;
	}

	public void setEmotflag(int emotflag) {
		this.emotflag = emotflag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserinfoid() {
		return userinfoid;
	}

	public void setUserinfoid(long userinfoid) {
		this.userinfoid = userinfoid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	

}
