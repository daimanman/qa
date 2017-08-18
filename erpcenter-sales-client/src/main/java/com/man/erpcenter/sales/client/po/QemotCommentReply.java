package com.man.erpcenter.sales.client.po;

public class QemotCommentReply implements java.io.Serializable {
	
	public static final long serialVersionUID = -7900529168750310934L;

	public Long id;

    public Long commentId;

    public String uid;

    public String content;

    public String createTime0;

    public String createTime2;

    public String createTime3;

    public String name;

    public String sourceName;

    public String tid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCreateTime0() {
        return createTime0;
    }

    public void setCreateTime0(String createTime0) {
        this.createTime0 = createTime0 == null ? null : createTime0.trim();
    }

    public String getCreateTime2() {
        return createTime2;
    }

    public void setCreateTime2(String createTime2) {
        this.createTime2 = createTime2 == null ? null : createTime2.trim();
    }

    public String getCreateTime3() {
        return createTime3;
    }

    public void setCreateTime3(String createTime3) {
        this.createTime3 = createTime3 == null ? null : createTime3.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName == null ? null : sourceName.trim();
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }
}