package com.lanqiao.dao;


import com.lanqiao.model.TDataTag;

public interface TDataTagMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_data_tag
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int deleteByPrimaryKey(Integer nnid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_data_tag
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insert(TDataTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_data_tag
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insertSelective(TDataTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_data_tag
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    TDataTag selectByPrimaryKey(Integer nnid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_data_tag
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeySelective(TDataTag record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_data_tag
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKey(TDataTag record);
    //取消关注-by 罗玉琳
	void cancelFocus(TDataTag dataTag);
    //判断老师是否标记了某学员-by罗玉琳
	int teaIfRemarkStu(TDataTag dataTag);
}