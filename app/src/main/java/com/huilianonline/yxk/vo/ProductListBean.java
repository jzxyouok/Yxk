package com.huilianonline.yxk.vo;

import java.util.List;

/**
 * Created by admin on 2017/3/22.
 */
public class ProductListBean {


    /**
     * PageCount : 2
     * RecordCount : 17
     * Code : 0
     * Msg : 返回成功
     * Data : [{"ProductId":1,"ProductName":"海尔电视机","ProductName2":"海尔电视机","Pic":"http://yxk.huilianonline.com.jpg","SalePrice":1500,"Price":3000,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"","StorNum":0},{"ProductId":3,"ProductName":"海尔电视机15寸白色","ProductName2":"海尔电视机15寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0},{"ProductId":4,"ProductName":"海尔电视机35寸白色","ProductName2":"海尔电视机35寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0},{"ProductId":5,"ProductName":"海尔电视机45寸白色","ProductName2":"海尔电视机45寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0},{"ProductId":6,"ProductName":"海尔电视机55寸白色","ProductName2":"海尔电视机55寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0},{"ProductId":7,"ProductName":"海尔电视机65寸白色","ProductName2":"海尔电视机65寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0},{"ProductId":8,"ProductName":"海尔电视机75寸白色","ProductName2":"海尔电视机75寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0},{"ProductId":9,"ProductName":"海尔电视机85寸白色","ProductName2":"海尔电视机85寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0},{"ProductId":10,"ProductName":"海尔电视机1寸白色","ProductName2":"海尔电视机1寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0},{"ProductId":11,"ProductName":"海尔电视机2寸白色","ProductName2":"海尔电视机2寸白色","Pic":"http://yxk.huilianonline.com/upload/product/2/a90a3e74-ab0c-41ac-833f-d756fcb186c2.jpg","SalePrice":1500,"Price":1800,"ClassId":2,"ProductClassName":"电视机","LimitNum":-1,"LimitTime":"2017-03-02 00:00:00","StorNum":0}]
     */

    private int PageCount;
    private int RecordCount;
    private int Code;
    private String Msg;
    /**
     * ProductId : 1
     * ProductName : 海尔电视机
     * ProductName2 : 海尔电视机
     * Pic : http://yxk.huilianonline.com.jpg
     * SalePrice : 1500
     * Price : 3000
     * ClassId : 2
     * ProductClassName : 电视机
     * LimitNum : -1
     * LimitTime :
     * StorNum : 0
     */

    private List<DataBean> Data;

    public int getPageCount() {
        return PageCount;
    }

    public void setPageCount(int PageCount) {
        this.PageCount = PageCount;
    }

    public int getRecordCount() {
        return RecordCount;
    }

    public void setRecordCount(int RecordCount) {
        this.RecordCount = RecordCount;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private int ProductId;
        private String ProductName;
        private String ProductName2;
        private String Pic;
        private int SalePrice;
        private int Price;
        private int ClassId;
        private String ProductClassName;
        private int LimitNum;
        private String LimitTime;
        private int StorNum;
        private String CurTime;

        public String getCurTime() {
            return CurTime;
        }

        public void setCurTime(String curTime) {
            CurTime = curTime;
        }

        public int getProductId() {
            return ProductId;
        }

        public void setProductId(int ProductId) {
            this.ProductId = ProductId;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public String getProductName2() {
            return ProductName2;
        }

        public void setProductName2(String ProductName2) {
            this.ProductName2 = ProductName2;
        }

        public String getPic() {
            return Pic;
        }

        public void setPic(String Pic) {
            this.Pic = Pic;
        }

        public int getSalePrice() {
            return SalePrice;
        }

        public void setSalePrice(int SalePrice) {
            this.SalePrice = SalePrice;
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int Price) {
            this.Price = Price;
        }

        public int getClassId() {
            return ClassId;
        }

        public void setClassId(int ClassId) {
            this.ClassId = ClassId;
        }

        public String getProductClassName() {
            return ProductClassName;
        }

        public void setProductClassName(String ProductClassName) {
            this.ProductClassName = ProductClassName;
        }

        public int getLimitNum() {
            return LimitNum;
        }

        public void setLimitNum(int LimitNum) {
            this.LimitNum = LimitNum;
        }

        public String getLimitTime() {
            return LimitTime;
        }

        public void setLimitTime(String LimitTime) {
            this.LimitTime = LimitTime;
        }

        public int getStorNum() {
            return StorNum;
        }

        public void setStorNum(int StorNum) {
            this.StorNum = StorNum;
        }
    }
}
