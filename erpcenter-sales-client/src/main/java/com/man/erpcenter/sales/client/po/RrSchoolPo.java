package com.man.erpcenter.sales.client.po;

public class RrSchoolPo implements java.io.Serializable {
   
	private static final long serialVersionUID = 1443685430796063269L;

	private int provId;

    private String provName;

    private int univId;

    private String univName;

    public int getProvId() {
        return provId;
    }

    public void setProvId(int provId) {
        this.provId = provId;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName == null ? null : provName.trim();
    }

    public int getUnivId() {
        return univId;
    }

    public void setUnivId(int univId) {
        this.univId = univId;
    }

    public String getUnivName() {
        return univName;
    }

    public void setUnivName(String univName) {
        this.univName = univName == null ? null : univName.trim();
    }
}