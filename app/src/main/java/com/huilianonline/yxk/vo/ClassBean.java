package com.huilianonline.yxk.vo;

import java.util.List;

/**
 * 分类
 * Created by admin on 2017/3/21.
 */
public class ClassBean {


    /**
     * Code : 0
     * Msg : 返回成功
     * Data : [{"ClassID":3,"ClassName":"","Remark":"","Pic":"http://yxk.huilianonline.com/upload/class/3.jpg"},{"ClassID":5,"ClassName":"红","Remark":"","Pic":"http://yxk.huilianonline.com/upload/class/5.jpg"},{"ClassID":13,"ClassName":"自行车","Remark":"","Pic":"http://yxk.huilianonline.com/upload/class/13.jpg"}]
     */

    private int Code;
    private String Msg;
    /**
     * ClassID : 3
     * ClassName :
     * Remark :
     * Pic : http://yxk.huilianonline.com/upload/class/3.jpg
     */

    private List<DataBean> Data;

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
        private int ClassID;
        private String ClassName;
        private String Remark;
        private String Pic;

        public int getClassID() {
            return ClassID;
        }

        public void setClassID(int ClassID) {
            this.ClassID = ClassID;
        }

        public String getClassName() {
            return ClassName;
        }

        public void setClassName(String ClassName) {
            this.ClassName = ClassName;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getPic() {
            return Pic;
        }

        public void setPic(String Pic) {
            this.Pic = Pic;
        }
    }
}
