package com.man.erpcenter.solrsearch.client;

public class SolrPhotoInfo implements java.io.Serializable {
	private static final long serialVersionUID = -8002407108965831214L;
	public long id;
	public long uid;
	public int totalnum;
	public int allowAccess;
	public int comment;
	public long createtime;
	public String desc;
	public String topicid;
	public String name;
	public String pre;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public int getTotalnum() {
		return totalnum;
	}
	public void setTotalnum(int totalnum) {
		this.totalnum = totalnum;
	}
	public int getAllowAccess() {
		return allowAccess;
	}
	public void setAllowAccess(int allowAccess) {
		this.allowAccess = allowAccess;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public long getCreatetime() {
		return createtime;
	}
	public void setCreatetime(long createtime) {
		this.createtime = createtime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTopicid() {
		return topicid;
	}
	public void setTopicid(String topicid) {
		this.topicid = topicid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPre() {
		return pre;
	}
	public void setPre(String pre) {
		this.pre = pre;
	}
	

}
