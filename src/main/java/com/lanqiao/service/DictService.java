package com.lanqiao.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lanqiao.dao.DictMapper;

@Service
public class DictService extends BaseService {

	/** 学员状态 */
	public static final String DICT_STUDENT_STATUS = "stu_status";//学员状态
	/** 培训方向 */
	public static final String DICT_COURSE_TYPE = "lq_courseType";//培训方向
	/** 学校类别 */
	public static final String DICT_SCHOOL_TYPE = "school_type";//学校类别
	/** 客户类别 */
	public static final String DICT_CUSTOMER_TYPE = "customer_type";//客户类别
	/** 蓝桥班级类型（一级） */
	public static final String DICT_CLASSTYPE_PRE = "lq_classType1";//蓝桥班级类型（一级）
	/** 蓝桥班级类型（二级） */
	public static final String DICT_CLASSTYPE_REAR = "lq_classType2";//蓝桥班级类型（二级）
	/** 学历 */
	public static final String DICT_USER_EDUCATION = "education";//学历
	/** 班级状态 */
	public static final String DICT_CLASS_STATUS = "classStatus";//班级状态
	/** 学员审核状态 */
	public static final String DICT_AUDIT_STATUS = "stu_auditstatus";//学员审核状态
	/** 是否缴纳了报名费 */
	public static final String DICT_IS_AVAIABLE = "is_avaiable";//是否缴纳了报名费
	/** 是否缴纳了学费 */
	public static final String DICT_STU_HASPAID = "stu_hasPaid";//是否缴纳了学费
	/** 支付方式 */
	public static final String DICT_PAY_TYPE = "pay_type";//支付方式
	/** 就业意向地址 */
	public static final String DICT_JOB_JOBCITYCODE = "job_jobCityCode";//就业意向地址
	/** 性别 */
	public static final String DICT_SEX = "sex";//性别
	/** 公司规模  */
	public static final String DICT_COCOMP_SCALE = "cocompScale";//公司规模
	/** 公司类型 */
	public static final String DICT_COCOMP_TYPE = "cocompType";//公司类型
	/** 创建班级目的 */
	public static final String DICT_CLASS_GOAL = "class_goal";//创建班级目的
	/** 岗位类型 */
	public static final String DICT_POSITION_TYPE = "inviteType";//岗位类型

	//eg:list = dictService.getDictByType(DictService.DICT_COURSE_TYPE);

	public String getLabelByValAndType(Map<String, Object> param) {
		DictMapper dictMapper = dao.getMapper(DictMapper.class);
		return dictMapper.getLabelByValAndType(param);
	}

	/*
	 * *****************************************
	 * **************    通用       ******************
	 * *****************************************
	 * 根据类型获取对应类型的:	value  label
	 */
	public List<Map<String, Object>> getDictByType(String type) throws Exception {
		DictMapper dictMapper = dao.getMapper(DictMapper.class);
		return dictMapper.selectDictByType(type);
	}

	/** 根据类型和父级编号获取字典列表 */
	public List<Map<String, Object>> getDictByParentAndType(String type, String parentId) throws Exception {
		DictMapper dictMapper = dao.getMapper(DictMapper.class);
		return dictMapper.getDictByParentAndType(type, parentId);
	}

	public String getIdByValAndType(Map<String, Object> param) {
		DictMapper dictMapper = dao.getMapper(DictMapper.class);
		return dictMapper.getIdByValAndType(param);
	}

	/** 获取班级类型，根据一级二级的value */
	public String getClassType(Map<String, Object> map) {
		DictMapper dictMapper = dao.getMapper(DictMapper.class);
		return dictMapper.getClassType(map);
	}

	/*
	 * 查找课程名称  sys_cource
	 */
	public List<Map<String, Object>> getSysCourse() throws Exception {
		List<Map<String, Object>> courses = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.SysCourseMapper.selectCourse", "");
		return courses;
	}
}
