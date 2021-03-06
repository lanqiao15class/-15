package com.lanqiao.model;

import java.math.BigDecimal;
import java.util.Date;

public class SysCourse {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.course_id
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private Integer courseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.course_name
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private String courseName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.type
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.standard_money
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private BigDecimal standardMoney;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.entryfee
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private BigDecimal entryfee;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.lq_courseType
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private Integer lqCoursetype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.create_time
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.user_id
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_course.total_class
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    private Integer totalClass;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.course_id
     *
     * @return the value of sys_course.course_id
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.course_id
     *
     * @param courseId the value for sys_course.course_id
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.course_name
     *
     * @return the value of sys_course.course_name
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.course_name
     *
     * @param courseName the value for sys_course.course_name
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.type
     *
     * @return the value of sys_course.type
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.type
     *
     * @param type the value for sys_course.type
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.standard_money
     *
     * @return the value of sys_course.standard_money
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public BigDecimal getStandardMoney() {
        return standardMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.standard_money
     *
     * @param standardMoney the value for sys_course.standard_money
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setStandardMoney(BigDecimal standardMoney) {
        this.standardMoney = standardMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.entryfee
     *
     * @return the value of sys_course.entryfee
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public BigDecimal getEntryfee() {
        return entryfee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.entryfee
     *
     * @param entryfee the value for sys_course.entryfee
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setEntryfee(BigDecimal entryfee) {
        this.entryfee = entryfee;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.lq_courseType
     *
     * @return the value of sys_course.lq_courseType
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public Integer getLqCoursetype() {
        return lqCoursetype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.lq_courseType
     *
     * @param lqCoursetype the value for sys_course.lq_courseType
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setLqCoursetype(Integer lqCoursetype) {
        this.lqCoursetype = lqCoursetype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.create_time
     *
     * @return the value of sys_course.create_time
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.create_time
     *
     * @param createTime the value for sys_course.create_time
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.user_id
     *
     * @return the value of sys_course.user_id
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.user_id
     *
     * @param userId the value for sys_course.user_id
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_course.total_class
     *
     * @return the value of sys_course.total_class
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public Integer getTotalClass() {
        return totalClass;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_course.total_class
     *
     * @param totalClass the value for sys_course.total_class
     *
     * @mbggenerated Tue Jun 27 14:28:36 CST 2017
     */
    public void setTotalClass(Integer totalClass) {
        this.totalClass = totalClass;
    }
}