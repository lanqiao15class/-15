package com.lanqiao.model;

public class SysUserRole {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.nnid
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    private Integer nnid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.user_id
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user_role.role_id
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    private String roleId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.nnid
     *
     * @return the value of sys_user_role.nnid
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    public Integer getNnid() {
        return nnid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.nnid
     *
     * @param nnid the value for sys_user_role.nnid
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    public void setNnid(Integer nnid) {
        this.nnid = nnid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.user_id
     *
     * @return the value of sys_user_role.user_id
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.user_id
     *
     * @param userId the value for sys_user_role.user_id
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user_role.role_id
     *
     * @return the value of sys_user_role.role_id
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user_role.role_id
     *
     * @param roleId the value for sys_user_role.role_id
     *
     * @mbggenerated Fri Dec 09 17:16:58 CST 2016
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}