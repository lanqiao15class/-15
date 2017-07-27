package com.lanqiao.dto;

import com.lanqiao.model.TeacherCourseArrangement;

/**
 * Created by wei62_000 on 2017/7/13.
 */
public class CourseArrangementDTO extends TeacherCourseArrangement {

    private String className;   //班级名称
    private String teaName; //上课老师
    private String typeStr;  //课时状态

    public CourseArrangementDTO(){
        int i = super.getCaType();
        if(i==0){
            this.typeStr="未上课";
        }else if(i==1){
            this.typeStr="上课中";
        }else if(i==2){
            this.typeStr="以上课";
        }else {
            this.typeStr="取消";
        }
    }
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }
}
