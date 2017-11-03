package com.man.erpcenter.sales.client.po;

import java.util.Date;

public class RrUserPo  implements java.io.Serializable {
	
	private static final long serialVersionUID = 1746496617348962826L;

	private long id;

    private String name;

    private int gender;

    private int popValue;

    private String logoImg;

    private int schoolId;

    private long rid;

    private Date birthdate;

    private String hp;

    private String hpCode;

    private String hc;

    private String hcCode;

    private String fp;

    private String fpCode;

    private String fc;

    private String fcCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getPopValue() {
        return popValue;
    }

    public void setPopValue(int popValue) {
        this.popValue = popValue;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public void setLogoImg(String logoImg) {
        this.logoImg = logoImg == null ? null : logoImg.trim();
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp == null ? null : hp.trim();
    }

    public String getHpCode() {
        return hpCode;
    }

    public void setHpCode(String hpCode) {
        this.hpCode = hpCode == null ? null : hpCode.trim();
    }

    public String getHc() {
        return hc;
    }

    public void setHc(String hc) {
        this.hc = hc == null ? null : hc.trim();
    }

    public String getHcCode() {
        return hcCode;
    }

    public void setHcCode(String hcCode) {
        this.hcCode = hcCode == null ? null : hcCode.trim();
    }

    public String getFp() {
        return fp;
    }

    public void setFp(String fp) {
        this.fp = fp == null ? null : fp.trim();
    }

    public String getFpCode() {
        return fpCode;
    }

    public void setFpCode(String fpCode) {
        this.fpCode = fpCode == null ? null : fpCode.trim();
    }

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc == null ? null : fc.trim();
    }

    public String getFcCode() {
        return fcCode;
    }

    public void setFcCode(String fcCode) {
        this.fcCode = fcCode == null ? null : fcCode.trim();
    }
}