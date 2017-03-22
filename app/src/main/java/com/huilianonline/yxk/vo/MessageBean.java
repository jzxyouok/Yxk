package com.huilianonline.yxk.vo;

import java.util.List;

/**
 * Created by admin on 2017/3/22.
 */
public class MessageBean {


    /**
     * PageCount : 4
     * RecordCount : 39
     * Code : 0
     * Msg : 返回成功
     * Data : [{"MsgID":1,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":2,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":3,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":4,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":5,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":6,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":7,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":8,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":9,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"},{"MsgID":10,"CreateTime":"2017-01-15","Intro":"您的订单201703210001支付成功"}]
     */

    private int PageCount;
    private int RecordCount;
    private int Code;
    private String Msg;
    /**
     * MsgID : 1
     * CreateTime : 2017-01-15
     * Intro : 您的订单201703210001支付成功
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
        private int MsgID;
        private String CreateTime;
        private String Intro;

        public int getMsgID() {
            return MsgID;
        }

        public void setMsgID(int MsgID) {
            this.MsgID = MsgID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getIntro() {
            return Intro;
        }

        public void setIntro(String Intro) {
            this.Intro = Intro;
        }
    }
}
