package com.lanqiao.dao;

import java.util.List;
import java.util.Map;

import com.lanqiao.model.TLqclass;

public interface TLqclassMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_lqclass
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int deleteByPrimaryKey(Integer lqClassId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_lqclass
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insert(TLqclass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_lqclass
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int insertSelective(TLqclass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_lqclass
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    TLqclass selectByPrimaryKey(Integer lqClassId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_lqclass
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKeySelective(TLqclass record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_lqclass
     *
     * @mbggenerated Sat Nov 19 14:42:38 CST 2016
     */
    int updateByPrimaryKey(TLqclass record);
    
    /** 获取用于学生入班的班级（只限制班级状态是未开课和授课中）*/
    List<Map<String,Object>> classForStuIntoClass(List<Integer> list);
    
   /** 获取该名称的班级数量 */
    int classNameUniqueCheck(Map<String, Object> map);
    
    /** 添加班级，返回主键 */
    int insertSelectiveReturnId(TLqclass tLqclass);
    
    /** 获取某个班级的可转班学员人数  */
    int studentCountCanMerge(Map<String,Object> map);
    
    /** 获取可转班学员的ids */
    List<Integer> getStuIdsCanMerge(Map<String,Object> map);
    
    /** 获取可合班的班级详情列表 */
    List<Map<String,Object>> getMergeClassData(List<Integer> list);
    
    //我关注的班级列表
	List<Map<String, Object>> selectMyFocusClassList(Map<String, Object> paramMap);
    
	//我关注的班级记录数
	int getMyFocusClassCount(Map<String, Object> paramMap);
     
	//全部班级列表
	List<Map<String, Object>> selectAllClassList(Map<String, Object> paramMap);
    
	//全部班级记录数
	int getAllClassCount(Map<String, Object> paramMap);
    
	//我管理的班级列表
	List<Map<String, Object>> selectMyManageClassList(Map<String, Object> paramMap);
	
    //我管理的班级记录数
	int getMyManageClassCount(Map<String, Object> paramMap);
	
	//班级的当前人数
	int getClassCurrentCount(Map<String, Object> paramMap);
	
	//当前年份的新开班数
	int getNewClassCount();
	
    //获取学员当前所在班级信息
	Map<String, Object> getMyClassInfo(Integer userId);
    
	//根据学员useid获取其所在班级id
	Integer selectClassIdByUserId(Integer userId);

	//根据班级Id获取当前班级的真实人数
    Integer selectClassRealCount(Integer classId);
}