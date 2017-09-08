package com.man.erpcenter.solrsearch.client;

public class SolrImgInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 585370691538178285L;

	public long id;
	
	public long uid;
	
	public long photoId;
	
	public String url;
	
	public String cameratype;
	
	public String poiname;
	
	public String uploadtime;
	
	

	public String getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}

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

	public long getPhotoId() {
		return photoId;
	}

	public void setPhotoId(long photoId) {
		this.photoId = photoId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCameratype() {
		return cameratype;
	}

	public void setCameratype(String cameratype) {
		this.cameratype = cameratype;
	}

	public String getPoiname() {
		return poiname;
	}

	public void setPoiname(String poiname) {
		this.poiname = poiname;
	}
	
	public String getMurl(){
		if(url != null && url.length() > 0){
			return url.replace("/b/","/m/");
		}
		return url;
	}
	
	
}
