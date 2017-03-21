package com.huilianonline.yxk.vo;

/**
 * Created by admin on 2017/3/16.
 */
public class RequestBean {

    private String tel;

    private String bankCode;

    private String realName;

    private String papersCode;

    private String IMEI;

    private String SIM;

    private String systemCode;

    private String parentClassID;

    private String userID;

    private String page;

    private String pageSize;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getParentClassID() {
        return parentClassID;
    }

    public void setParentClassID(String parentClassID) {
        this.parentClassID = parentClassID;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPapersCode() {
        return papersCode;
    }

    public void setPapersCode(String papersCode) {
        this.papersCode = papersCode;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getSIM() {
        return SIM;
    }

    public void setSIM(String SIM) {
        this.SIM = SIM;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
