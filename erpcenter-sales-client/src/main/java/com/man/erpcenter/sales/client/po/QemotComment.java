package com.man.erpcenter.sales.client.po;

public class QemotComment implements java.io.Serializable {
	public static final long serialVersionUID = 1025269641673501519L;

	public Long id;

	public Long emotId;

	public String tid;

	public String uid;

	public String muid;

	public String mname;

	public String content;

	public String createtime2;

	public String createTime1;

	public String createTime;
	
	public String replyNum;

	public String getCreateTime1() {
		return createTime1;
	}

	public void setCreateTime1(String createTime1) {
		this.createTime1 = createTime1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmotId() {
		return emotId;
	}

	public void setEmotId(Long emotId) {
		this.emotId = emotId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid == null ? null : tid.trim();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid == null ? null : uid.trim();
	}

	public String getMuid() {
		return muid;
	}

	public void setMuid(String muid) {
		this.muid = muid == null ? null : muid.trim();
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname == null ? null : mname.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getCreatetime2() {
		return createtime2;
	}

	public void setCreatetime2(String createtime2) {
		this.createtime2 = createtime2;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getReplyNum() {
		return replyNum;
	}

	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum == null ? null : replyNum.trim();
	}
}