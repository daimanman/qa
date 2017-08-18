package com.man.erpcenter.sales.client.po;

public class QmsgInfo implements java.io.Serializable {
	
	public static final long serialVersionUID = -970010355346462752L;

	public Long id;

	public String msgid;

	public String secret;

	public String pasterid;

	public String bmp;

	public String pubtime;

	public String modifytime;

	public String effect;

	public String type;

	public String uin;

	public String nickname;

	public String capacity;

	public String uid;

	public String htmlContent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid == null ? null : msgid.trim();
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret == null ? null : secret.trim();
	}

	public String getPasterid() {
		return pasterid;
	}

	public void setPasterid(String pasterid) {
		this.pasterid = pasterid == null ? null : pasterid.trim();
	}

	public String getBmp() {
		return bmp;
	}

	public void setBmp(String bmp) {
		this.bmp = bmp == null ? null : bmp.trim();
	}

	public String getPubtime() {
		return pubtime;
	}

	public void setPubtime(String pubtime) {
		this.pubtime = pubtime == null ? null : pubtime.trim();
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime == null ? null : modifytime.trim();
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect == null ? null : effect.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin == null ? null : uin.trim();
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname == null ? null : nickname.trim();
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity == null ? null : capacity.trim();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid == null ? null : uid.trim();
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent == null ? null : htmlContent.trim();
	}
}