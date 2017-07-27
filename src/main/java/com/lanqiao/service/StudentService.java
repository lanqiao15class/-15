package com.lanqiao.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.dao.LQUniversitiesMapper;
import com.lanqiao.dao.StudentMapper;
import com.lanqiao.dao.SysUserRoleMapper;
import com.lanqiao.dao.TDataTagMapper;
import com.lanqiao.dao.TIncomeLogMapper;
import com.lanqiao.dao.TStudentReceivableLogMapper;
import com.lanqiao.dao.TStudentRemissionLogMapper;
import com.lanqiao.dao.TStunoMapper;
import com.lanqiao.dao.UserMapper;
import com.lanqiao.model.LQUniversities;
import com.lanqiao.model.StuExportBean;
import com.lanqiao.model.Student;
import com.lanqiao.model.SysUserRole;
import com.lanqiao.model.TDataTag;
import com.lanqiao.model.TIncomeLog;
import com.lanqiao.model.TLqclass;
import com.lanqiao.model.TStudentReceivableLog;
import com.lanqiao.model.TStudentRemissionLog;
import com.lanqiao.model.TStudentclassLog;
import com.lanqiao.model.TStuno;
import com.lanqiao.model.User;
import com.lanqiao.util.CommonUtil;

@Service
public class StudentService extends BaseService {

	private final static Logger logger = LogManager.getLogger(StudentService.class);

	@Resource
	StudentStatusService studentStatusService;

	@Resource
	StudentclassLogService studentclassLogService;

	@Resource
	TLqclassService tLqclassService;

	// 学生审核状态和状态，用于抬头显示提示
	public Map<String, Object> statusForHead(int studentId) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.statusForHead(studentId);
	}

	// 获取意向学员列表(我的关注)-by罗玉琳
	public List<Map<String, Object>> selectYxStuList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.selectMyFocusYxStuList(paramMap);
	}

	/**
	 * 
	 * @description:添加意向学员（1，添加登录用户,2，添加学生）
	 * @param user
	 * @param student
	 * @return void
	 * @author:ZhuDiaoHua
	 * @2016年11月30日下午3:47:10
	 *
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addYxStudent(Map<String, Object> map) throws Exception {
		/**
		 * 先全部转化为String数据，再根据对象内的格式对应转化，统一处理：返回数据是否是空格，数据两端是否存在有空格
		 */
		// 1，user数据
		String sex = ((String) map.get("sex")).trim();// 性别
		String realName = ((String) map.get("realName")).trim();// 真实姓名
		String loginTel = ((String) map.get("loginTel")).trim();// 登录手机号（联系手机号）
		String qq = ((String) map.get("qq")).trim();// QQ号码
		String loginEmail = ((String) map.get("loginEmail")).trim();// 登录邮箱（联系邮箱）

		String idCard = ((String) map.get("idCard")).trim();// 身份证号码
		String nation = (String) map.get("nation");// 民族(特殊，前端不选择的话是null，不可以用trim方法)
		String birth = ((String) map.get("birth")).trim();// 生日（8位数字）

		// 2,处理user表
		User user = new User();

		user.setSex(sex);
		if (StringUtils.isNotEmpty(realName))
			user.setRealName(realName);

		if (StringUtils.isNoneEmpty(loginEmail))
			user.setLoginEmail(loginEmail);

		if (StringUtils.isNotEmpty(loginTel))
			user.setLoginTel(loginTel);

		if (StringUtils.isNotEmpty(qq))
			user.setQq(qq);

		if (StringUtils.isNotEmpty(idCard))
			user.setIdCard(idCard);

		if (StringUtils.isNotEmpty(nation))
			user.setNation(nation);

		if (StringUtils.isNotEmpty(birth)) {
			DateFormat fmt = new SimpleDateFormat("yyyyMMdd");
			user.setBirth(fmt.parse(birth));// 8位生日String转化为Date
		}

		// *******************另外，自动数据：【密码和salt，用户类型、创建时间、更新时间、联系邮箱、联系电话】******************
		String salt = CommonUtil.randomSalt();
		user.setSalt(salt);
		user.setPassword(CommonUtil.md5Pwd("123456", salt));
		user.setType("0");
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setEmail(loginEmail);
		user.setTel(loginTel);

		// 插入数据
		UserMapper userMapper = dao.getMapper(UserMapper.class);
		userMapper.zzxInsertSelective(user);
		int userId = user.getUserId();// 获取返回的主键，与学生表建立关联

		// 3，student数据
		String address = ((String) map.get("address")).trim();// 通讯地址
		String parent_info_name = ((String) map.get("parent_info_name")).trim();// 父母信息-父母姓名
		String parent_info_tel = ((String) map.get("parent_info_tel")).trim();// 父母信息-父母手机号
		String zzx_school_name = (String) map.get("zzx_school_name");// 所在院校code
		String schoolTypeCode = (String) map.get("schoolTypeCode");// 所在院校类型

		String schoolProvCode = (String) map.get("schoolProvCode");// 院校所在省份
		String schoolCityCode = (String) map.get("schoolCityCode");// 院校所在市
		String grade = ((String) map.get("grade")).trim();// XXXX届/级
		String schoolDuty = ((String) map.get("schoolDuty")).trim();// 担任的职务
		String major = ((String) map.get("major")).trim();// 所学专业

		String schoolSubname = ((String) map.get("schoolSubname")).trim();// 所在学院
		String schoolDormitory = ((String) map.get("schoolDormitory")).trim();// 所在宿舍号
		String courseType = ((String) map.get("courseType")).trim();// 所学课程类别
		// String protocolAgree =
		// ((String)map.get("protocolAgree")).trim();//是否同意协议
		String jobCityCodes = ((String) map.get("jobCityCodes")).trim();// 意向工作城市（一个用逗号分隔的城市code）
		String idcardFrontImg = ((String) map.get("frontImgName")).trim();// 身份证正面文件名
		String idcardBackImg = ((String) map.get("backImgName")).trim();// 身份证反面文件名

		// 4，处理student表
		Student student = new Student();

		if (StringUtils.isNotEmpty(address))
			student.setAddress(address);

		if (StringUtils.isNotEmpty(parent_info_name))
			student.setParentInfo(parent_info_name);

		if (StringUtils.isNotEmpty(parent_info_tel))
			student.setParentInfo(parent_info_tel);

		if (StringUtils.isNotEmpty(zzx_school_name))
			student.setUnivCode(Integer.parseInt(zzx_school_name));

		if (StringUtils.isNotEmpty(schoolTypeCode))
			student.setSchoolTypeCode(Integer.parseInt(schoolTypeCode));

		if (StringUtils.isNotEmpty(schoolProvCode))
			student.setSchoolProvCode(Integer.parseInt(schoolProvCode));

		if (StringUtils.isNotEmpty(schoolCityCode))
			student.setSchoolCityCode(Integer.parseInt(schoolCityCode));

		if (StringUtils.isNotEmpty(grade))
			student.setGrade(grade);

		if (StringUtils.isNotEmpty(schoolDuty))
			student.setSchoolDuty(schoolDuty);

		if (StringUtils.isNotEmpty(major))
			student.setMajor(major);

		if (StringUtils.isNotEmpty(schoolSubname))
			student.setSchoolSubname(schoolSubname);

		if (StringUtils.isNotEmpty(schoolDormitory))
			student.setSchoolDormitory(schoolDormitory);

		if (StringUtils.isNotEmpty(courseType))
			student.setCourseType(courseType);

		if (StringUtils.isNotEmpty(jobCityCodes))
			student.setJobCityCode(jobCityCodes);

		if (StringUtils.isNotEmpty(idcardFrontImg))
			student.setIdcardFrontImg(idcardFrontImg);

		if (StringUtils.isNotEmpty(idcardBackImg))
			student.setIdcardBackImg(idcardBackImg);

		// ***************************另外，自动数据【设置学员状态，匹配关联userId,创建时间,更新时间，审核状态、学员编号】*************************
		student.setStatus(StuStatusEnum.NOREGISTRATION.getValue());// 学员设置为未报名
		student.setAuditStatus(GlobalConstant.auditStatusEnum.NOREGIST.getValue());// 审核状态设置为去报名
		student.setUserId(userId);
		student.setCreateTime(new Date());
		student.setUpdateTime(new Date());

		// 插入数据student
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		studentMapper.insertSelective(student);

		// 5,添加学生权限（1221，陈老师说不必这个操作，所有学员默认权限）
		SysUserRole sysUserRole = new SysUserRole();
		sysUserRole.setUserId(userId + "");
		sysUserRole.setRoleId(GlobalConstant.STUDENTAUTHORITY);
		SysUserRoleMapper sysUserRoleMapper = dao.getMapper(SysUserRoleMapper.class);
		//		sysUserRoleMapper.insertSelective(sysUserRole);

	}

	public Student getStuInfoByUserId(int userId) {
		return (Student) dao.selectOne("com.lanqiao.dao.StudentMapper.selectByPrimaryKey", userId);
	}

	/**
	 * 
	 * @Description:根据学生id修改学生信息
	 * @param map
	 * @return void
	 * @author ZhouZhiHua
	 * @createTime 2016年12月1日 下午3:40:07
	 */
	public void updateStuInfo(Map<String, Object> map) throws Exception {
		Student student = new Student();
		student.setUserId(Integer.parseInt(map.get("userId").toString()));
		if (map.get("schoolTypeCode") != null) {
			student.setSchoolTypeCode(Integer.parseInt(map.get("schoolTypeCode").toString()));
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("courseTypeCode") != null) {
			student.setCourseType(map.get("courseTypeCode").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("isAvaiableCode") != null) {
			student.setIsAvaiable(Integer.parseInt(map.get("isAvaiableCode").toString()));
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("hasPaidCode") != null) {
			student.setHasPaid(Integer.parseInt(map.get("hasPaidCode").toString()));
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("schoolDuty") != null) {
			student.setSchoolDuty(map.get("schoolDuty").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("major") != null) {
			student.setMajor(map.get("major").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("grade") != null) {
			student.setGrade(map.get("grade").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("parentInfo") != null) {
			student.setParentInfo(map.get("parentInfo").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("parentTel") != null) {
			student.setParentTel(map.get("parentTel").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("jobCityCode") != null) {
			student.setJobCityCode(map.get("jobCityCode").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("schoolDormitory") != null) {
			student.setSchoolDormitory(map.get("schoolDormitory").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("address") != null) {
			student.setAddress(map.get("address").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("univCode") != null) {
			student.setUnivCode(Integer.parseInt(map.get("univCode").toString()));
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("schoolProvCode") != null && map.get("schoolCityCode") != null) {
			student.setSchoolProvCode(Integer.parseInt(map.get("schoolProvCode").toString()));
			student.setSchoolCityCode(Integer.parseInt(map.get("schoolCityCode").toString()));
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("idcardFrontImg") != null) {
			student.setIdcardFrontImg(map.get("idcardFrontImg").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("idcardBackImg") != null) {
			student.setIdcardBackImg(map.get("idcardBackImg").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
		if (map.get("schoolSubname") != null) {
			student.setSchoolSubname(map.get("schoolSubname").toString());
			dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
		}
	}

	// 获取我关注的意向学员列表总记录数-by罗玉琳
	public Integer getMyFocusYxStuCount(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.getMyFocusYxStuCount(paramMap);
	}

	// 标记为我关注的-by罗玉琳
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remarkMyFocusBatch(String stuUserIds, Integer userId) throws Exception {
		TDataTagMapper dataTagMapper = dao.getMapper(TDataTagMapper.class);
		TDataTag dataTag = new TDataTag();
		String userIds[] = stuUserIds.split(",");
		for (String s : userIds) {
			dataTag.setDatatype(3);
			dataTag.setDataId(Integer.parseInt(s));
			dataTag.setTagtype(1);
			dataTag.setUserid(userId);
			int row = dataTagMapper.teaIfRemarkStu(dataTag);
			if (row <= 0) {
				dataTagMapper.insertSelective(dataTag);// 标记为我关注的
			}

		}
	}

	// 取消关注-by罗玉琳
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void cancelFocusBatch(String stuUserIds, Integer userId) throws Exception {
		TDataTagMapper dataTagMapper = dao.getMapper(TDataTagMapper.class);
		TDataTag dataTag = new TDataTag();
		String userIds[] = stuUserIds.split(",");
		for (String s : userIds) {
			dataTag.setDatatype(3);
			dataTag.setDataId(Integer.parseInt(s));
			dataTag.setTagtype(1);
			dataTag.setUserid(userId);
			int row = dataTagMapper.teaIfRemarkStu(dataTag);
			if (row > 0) {
				dataTagMapper.cancelFocus(dataTag);
			}
		}
	}

	// 获取全部意向学员列表-by罗玉琳
	public List<Map<String, Object>> selectAllYxStuList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.selectAllYxStuList(paramMap);
	}

	// 获取全部意向学员列表总记录数-by罗玉琳
	public Integer getAllYxStuCount(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.getAllYxStuCount(paramMap);
	}

	/**
	 * 产生一个学员编号
	 * @param univcode
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public String createStuNO(int univcode) throws Exception {

		TStunoMapper tstunoMapper = dao.getMapper(TStunoMapper.class);
		LQUniversitiesMapper unmapper = dao.getMapper(LQUniversitiesMapper.class);

		String years = new java.text.SimpleDateFormat("yy").format(new java.util.Date());// 当前年

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("year", years);
		paramMap.put("courseType", univcode);
		TStuno stuno = tstunoMapper.selectByCouseTypeAndYear(paramMap);
		if (stuno == null) {
			TStuno sn = new TStuno();
			sn.setCourseType(new Integer(univcode));
			sn.setCourseTotal(1);
			sn.setYears(years);
			tstunoMapper.insert(sn);
			stuno = tstunoMapper.selectByCouseTypeAndYear(paramMap);
		}

		LQUniversities un = unmapper.selectByPrimaryKey((long) univcode);
		String bm = "NONE";
		if (un == null || StringUtils.isEmpty(un.getUnivPinyin())) {
			bm = "NONE";
		} else {
			bm = un.getUnivPinyin();
		}

		String result = bm + years + String.format("%03d", stuno.getCourseTotal());

		// 4，等待学生更新无误，可以更新编号表【修改学员总数,用于下次编号】
		Map<String, Object> paramMapZs = new HashMap<String, Object>();
		paramMapZs.put("courseType", univcode);
		paramMapZs.put("year", years);
		paramMapZs.put("courseTotal", stuno.getCourseTotal() + 1);
		tstunoMapper.updateByCouseTypeAndYear(paramMapZs);

		return result;
	}

	/**
	 * 
	 * @description:意向学员审核通过处理方法
	 * @param response
	 * @param map
	 * @return void
	 * @author:ZhuDiaoHua
	 * @2016年12月2日上午10:04:29
	 *
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void checkYxStudentPass(Map<String, Object> map) throws Exception {

		/********************************************* A，处理student开始 ************************************************/
		// 1,student数据【学员编号、审核状态、更新时间】
		String stuId = ((String) map.get("stuId")).trim();// 学员id
		// String isRightName =  ((String)map.get("isRightName")).trim();//身份证姓名是否相符【0是 1否】
		String isAvaiable = ((String) map.get("isAvaiable")).trim();// 是否缴纳报名费【0未缴纳 1已缴纳 2特批后缴 】

		String invTeacherId = ((String) map.get("ZSJL_teacher_name")).trim();// 招生经理
		String teacheridInspector = ((String) map.get("QYZJ_teacher_name")).trim();// 区域总监
		String teacheridAdvisor = ((String) map.get("KCGW_teacher_name")).trim();// 课程顾问
		Integer checkStatus = (Integer) map.get("checkStatus");// 审核状态
		//String courseType = (String) map.get("courseType");// 课程类别
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);

		/*
		 * ===========================================根据课程获取编号部分开始================
		 */
		Student stubean = studentMapper.selectByPrimaryKey(Integer.parseInt(stuId));
		if (stubean == null || stubean.getUnivCode() == null) {
			log.fatal("无学员院校信息， 无法设置学员编号");
			return;
		}

		String stuno = createStuNO(stubean.getUnivCode());

		/** =============================根据课程获取编号部分结束===========================*/

		// 2,设置学员信息
		Student student = new Student();
		student.setStuNo(stuno);// 学生获取设置编号
		student.setUserId(Integer.parseInt(stuId));// 用户id
		student.setInvTeacherId(Integer.parseInt(invTeacherId));// 招生经理
		student.setTeacheridAdvisor(Integer.parseInt(teacheridAdvisor));// 课程顾问
		student.setTeacheridInspector(Integer.parseInt(teacheridInspector));// 区域总监
		student.setIsAvaiable(Integer.parseInt(isAvaiable));// 报名费是否缴纳
		student.setHasPaid(GlobalConstant.StuPaidEnum.NOTPAY.getValue());//学费是否缴纳：未缴纳
		student.setAuditStatus(checkStatus);// 审核状态【1，通过】
		//		student.setStatus(StuStatusEnum.NOCLASS.getValue());// 12,未分班（这句不允许执行，会造成UpdateStudentStatus流程走不通，学员状态不能改）
		student.setUpdateTime(new Date());// 更新时间

		// 3，执行修改操作
		studentMapper.updateByPrimaryKeySelective(student);

		//5,添加学员状态流水,变为未分班状态
		studentStatusService.UpdateStudentStatus(Integer.parseInt(stuId),
				GlobalConstant.StuStatusEnum.NOCLASS.getValue(), new Date(), "学员审核通过，变为未分班状态",
				(Integer) map.get("operatorUserid"));

		/********************************************* A，处理student结束 ************************************************/

		/********************************************* B，处理报名费和减免费用开始 ************************************************/
		//1,是否有减免
		String hasFavour = (String) map.get("hasFavour");
		Long BMFJMmoney;//减免金额
		if (hasFavour != null && hasFavour.equals("0")) {//有减免
			BMFJMmoney = Long.parseLong((String) map.get("BMFJMmoney"));

			int JM_teacher_name = Integer.parseInt((String) map.get("JM_teacher_name"));//减免审批人
			String BMFJMremark = (String) map.get("BMFJMremark");//减免说明

			//创建减免记录
			TStudentRemissionLog log = new TStudentRemissionLog();
			log.setApproralUserid(JM_teacher_name);
			log.setCostType(GlobalConstant.PayGoalEnum.SIGNMONEY.getValue());
			if (map.get("BMFtime") != null && !((String) map.get("BMFtime")).trim().equals("")) {
				log.setCreateDate(CommonUtil.dateStrToDate((String) map.get("BMFtime")));//减免时间（交报名费那天）
			} else {
				log.setCreateDate(new Date());//减免时间（审核那天）
			}
			log.setOperUserid((Integer) map.get("operatorUserid"));//填写记录人
			log.setReduceMoney(BMFJMmoney);
			log.setRemarks(BMFJMremark);
			log.setRemissionUserid(Integer.parseInt(stuId));
			log.setInputtime(new Date());//记录时间

			//添加减免记录
			TStudentRemissionLogMapper tStudentRemissionLogMapper = dao.getMapper(TStudentRemissionLogMapper.class);
			tStudentRemissionLogMapper.insertSelective(log);

		} else {//无减免
			BMFJMmoney = new Long("0");
		}

		//2,是否是特批延迟缴纳（添加报名费费用表） 
		TIncomeLog BMFIncomeLog = new TIncomeLog();
		Long BMFHTmoney = Long.parseLong((String) map.get("BMFHTmoney"));//报名合同费用 
		BMFIncomeLog.setPayGoal(GlobalConstant.PayGoalEnum.SIGNMONEY.getValue());//支付类型【报名费】
		BMFIncomeLog.setUserId(Integer.parseInt(stuId));//学员id
		if (isAvaiable.equals(GlobalConstant.StuPaidEnum.SPECIALPAY.getValue() + "")) {//特批后缴
			BMFIncomeLog.setFavourMoney(BMFJMmoney);//报名费减免金额
			BMFIncomeLog.setStandardMoney(BMFHTmoney);//合同金额
			BMFIncomeLog.setSpecialAuditUserid(Integer.parseInt((String) map.get("TPHJ_teacher_name")));//特批后缴审批人
			BMFIncomeLog.setSpecialAuditDesc((String) map.get("TPHJremark"));//特批后缴说明

		} else {//已缴纳			********************需要添加回款表*****************
			BMFIncomeLog.setCurrentPayMoney(BMFHTmoney - BMFJMmoney);//已支付金额
			BMFIncomeLog.setFavourMoney(BMFJMmoney);//减免金额
			BMFIncomeLog.setLastPayTime(CommonUtil.dateStrToDate((String) map.get("BMFtime")));//最后一次支付时间
			BMFIncomeLog.setStandardMoney(BMFHTmoney);//合同金额
			BMFIncomeLog.setPayType(Integer.parseInt((String) map.get("payMethod")));//设置支付方式

			//创建回款表
			TStudentReceivableLogMapper tStudentReceivableLogMapper = dao.getMapper(TStudentReceivableLogMapper.class);
			TStudentReceivableLog log = new TStudentReceivableLog();
			log.setAgencyUserid(Integer.parseInt((String) map.get("JBR_teacher_name")));//经办人
			log.setCostType(GlobalConstant.PayGoalEnum.SIGNMONEY.getValue());//费用类型
			log.setCreateDate(CommonUtil.dateStrToDate((String) map.get("BMFtime"))); //支付日期
			log.setOperUserid((Integer) map.get("operatorUserid"));//记录人
			log.setPayWay(Integer.parseInt((String) map.get("payMethod")));//支付方式
			log.setReceivableMoney(new Long((String) map.get("BMFmoney")));//回款金额
			log.setReceivableUserid(Integer.parseInt(stuId));//学员id
			log.setRemarks("交报名费");//回款备注
			log.setInputtime(new Date());//记录时间

			//添加回款记录
			tStudentReceivableLogMapper.insertSelective(log);
		}
		//执行添加 
		TIncomeLogMapper tIncomeLogMapper = dao.getMapper(TIncomeLogMapper.class);
		tIncomeLogMapper.insertSelective(BMFIncomeLog);

		/********************************************* B，处理报名费开始 ************************************************/

		/********************************************* C，处理学费开始 ************************************************/

		//1,fee数据（学费费用表） 
		String HTmoney = ((String) map.get("HTmoney")).trim();//实训费用合同金额

		//2,设置fee //其余数据项 数据库默认或者未知
		TIncomeLog XFIncomeLog = new TIncomeLog();
		XFIncomeLog.setPayGoal(GlobalConstant.PayGoalEnum.LEARNMONEY.getValue());
		XFIncomeLog.setStandardMoney(Long.parseLong(HTmoney));
		XFIncomeLog.setUserId(Integer.parseInt(stuId));

		//3,执行添加 
		tIncomeLogMapper.insertSelective(XFIncomeLog);

		/********************************************* C，处理学费结束 ************************************************/

	}

	/**
	 * 
	 * @description:意向学员审核未通过处理方法
	 * @param map
	 *            <没有进行费用和其他任何信息的填写，所以只做学员信息更新>
	 * @throws Exception
	 * @return void
	 * @author:ZhuDiaoHua
	 * @2016年12月2日上午11:15:27
	 *
	 */
	public void checkYxStudentNotPass(Map<String, Object> map) throws Exception {
		// 学员更新
		String stuId = ((String) map.get("stuId")).trim();// 学员id
		String isAvaiable = ((String) map.get("isAvaiable")).trim();// 是否缴纳报名费【0未缴纳
																	// 1已缴纳
																	// 2特批后缴 】
		Integer checkStatus = (Integer) map.get("checkStatus");

		Student student = new Student();
		student.setUserId(Integer.parseInt(stuId));// 用户id
		student.setIsAvaiable(Integer.parseInt(isAvaiable));// 报名费是否缴纳
		student.setHasPaid(GlobalConstant.StuPaidEnum.NOTPAY.getValue());//学费是否缴纳：未缴纳
		student.setAuditStatus(checkStatus);// 审核状态
		student.setUpdateTime(new Date());// 更新时间

		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		studentMapper.updateByPrimaryKeySelective(student);
	}

	/**
	 * 
	 * @description:获取学员详情
	 * @param userId
	 * @return
	 * @return Student
	 * @author:ZhuDiaoHua
	 * @2016年12月3日下午1:21:53
	 *
	 */
	public Student selectByPrimaryKey(Integer userId) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.selectByPrimaryKey(userId);
	}

	/**
	 * 获取我关注的未分班学员列表 by-罗玉琳 2016-12-6
	 **/
	public List<Map<String, Object>> selectMyFocusNoClassStuList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.selectMyFocusNoClassStuList(paramMap);
	}

	/**
	 * 获取我关注的未分班学员记录数 by-罗玉琳 2016-12-6
	 **/
	public int getMyFocusNoClassStuCount(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.getMyFocusNoClassStuCount(paramMap);
	}

	/**
	 * 获取全部未分班学员列表 by-罗玉琳 2016-12-6
	 **/
	public List<Map<String, Object>> selectAllNoClassStuList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.selectAllNoClassStuList(paramMap);
	}

	/**
	 * 获取全部未分班学员记录数 by-罗玉琳 2016-12-6
	 **/
	public int getAllNoClassStuCount(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.getAllNoClassStuCount(paramMap);
	}

	/**
	 * 获取我关注的正式学员列表 by-罗玉琳 2016-12-7
	 **/
	public List<Map<String, Object>> selectMyFocusZsStuList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.selectMyFocusZsStuList(paramMap);
	}

	/**
	 * 获取我关注的正式学员记录数 by-罗玉琳 2016-12-7
	 **/
	public int getMyFocusZsStuCount(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.getMyFocusZsStuCount(paramMap);
	}

	/**
	 * 获取全部的正式学员列表 by-罗玉琳 2016-12-7
	 **/
	public List<Map<String, Object>> selectAllZsStuList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.selectAllZsStuList(paramMap);
	}

	/**
	 * 获取全部正式学员记录数 by-罗玉琳 2016-12-7
	 **/
	public int getAllZsStuCount(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.getAllZsStuCount(paramMap);
	}

	/**
	 * 获取我管理的正式学员列表 by-罗玉琳 2016-12-7
	 **/
	public List<Map<String, Object>> selectMyManageZsStuList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.selectMyManageZsStuList(paramMap);
	}

	/**
	 * 获取我管理的正式学员记录数 by-罗玉琳 2016-12-7
	 **/
	public int getMyManageZsStuCount(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		return studentMapper.getMyManageZsStuCount(paramMap);
	}

	/**
	 * 
	 * @description:执行学员入班操作
	 * @param map
	 * @param response
	 * @param request
	 * @return
	 * @throws Exception
	 * @return int[] 【第一个数是更改成功的数量，第二个是换班的列表里原来就属于该班级的人数】
	 * @author:ZhuDiaoHua
	 * @2016年12月8日下午9:03:45
	 *
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int[] studentIntoClass(Map<String, Object> map, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		logger.info("###################{}时发生{}###################", new Date().toString(), "修改，为n个学员加入班级");
		int newClassId = Integer.parseInt((String) map.get("newClassId"));// 新班级id

		// 1，根据新班级的状态获取对应班级下的学员正常状态
		TLqclass tLqclass = tLqclassService.classDetail(newClassId);
		Integer newClassStatus = tLqclass.getStatus();// 获取新班级的状态
		Integer classCourseType = tLqclass.getCourseType();// 获取班级课程类别

		Integer newStatus = CommonUtil.getStudentNewStatus(newClassStatus);// 获取对应班级下的学员正常状态

		// 2，发生时间、操作人、新班级id、描述
		Date happentime = CommonUtil.dateStrToDateWithTime((String) map.get("happentime"));
		SessionUser sUser = WebUtil.getLoginUser(request);
		Integer loginUserId = sUser.getUserId();
		String remark = (String) map.get("remark");

		// 2,循环处理每一个学员的信息变更和添加学员状态记录表
		String ids = (String) map.get("ids");// 学员id字符串
		if (ids != null) {
			Student student;
			String[] idStrings = ids.split(",");
			StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
			int count[] = { 0, 0 };
			for (String id : idStrings) {
				Integer stuOldLqClassId = studentMapper.selectByPrimaryKey(Integer.parseInt(id)).getLqClassId();
				if (stuOldLqClassId != null && stuOldLqClassId == newClassId) {
					count[0]++;//学员本来就在这个班级的数量
				} else {
					student = new Student();
					student.setUserId(Integer.parseInt(id));
					student.setLqClassId(newClassId);
					student.setCourseType(classCourseType + "");// 设置为班级的课程类别
					student.setUpdateTime(new Date());
					studentMapper.updateByPrimaryKeySelective(student);
					// 执行学员状态变化业务【改变学员状态、学员状态记录表更改与添加】
					studentStatusService.UpdateStudentStatus(Integer.parseInt(id), newStatus, happentime, remark,
							loginUserId);

					// 修改 学员班级记录表（顺序不能乱，在添加之前执行，不然会有错误）
					if (map.get("type") != null && !((String) map.get("type")).equals("newIntoClass")) {

						Integer oldLogId = studentclassLogService.getLastLogByUserId(Integer.parseInt(id));
						if (oldLogId != null) {
							TStudentclassLog oldLog = new TStudentclassLog();
							oldLog.setStudentclassLogId(oldLogId);
							oldLog.setExitTime(happentime);
							oldLog.setRemark(remark);//TODO 备注在新纪录还是旧记录？
							studentclassLogService.updateByPrimaryKeySelective(oldLog);
						}
					}

					// 添加 学员班级记录表
					TStudentclassLog newLog = new TStudentclassLog();
					newLog.setClassId(newClassId);
					newLog.setCreateTime(happentime);// 入班时间
					newLog.setRemark(remark.trim());
					newLog.setUserId(Integer.parseInt(id));
					newLog.setOperUserid(loginUserId);
					studentclassLogService.insertSelective(newLog);

					count[1]++;

				}
			}
			return count;
		}// 空ids
		return null;
	}

	/**
	 * 
	 * @description:获取在list内的id的学员列表
	 * @param list
	 * @return
	 * @return List<Map<String,Object>>
	 * @author:ZhuDiaoHua
	 * @2016年12月7日下午6:20:19
	 *
	 */
	public List<Map<String, Object>> getStudentByIds(String ids) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		List<Integer> list = new ArrayList<>();
		String[] idStrings = ids.split(",");
		for (String string : idStrings) {
			list.add(Integer.parseInt(string));
		}
		List<Map<String, Object>> studentlist = studentMapper.getStudentByIds(list);
		List<Map<String, Object>> returnList = new ArrayList<>();
		int index = 0;
		for (Map<String, Object> map : studentlist) {
			map.put("indexno", ++index);
			map.put("status", WebUtil.getStuStatusByValue((Integer) map.get("status")));// 状态码转化为文字
			returnList.add(map);
		}
		return returnList;
	}

	/**
	 * 未分班的学员导出 -- 我关注的.
	 * @param paramMap
	 * @return
	 */
	public List<StuExportBean> selectMyFocusNoClassExport(Map<String, Object> paramMap) {
		List<Map<String, Object>> ltemp = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.StudentMapper.selectMyFocusNoClassExport", paramMap);

		return UnionData(ltemp);
	}

	/**
	 * 未分班的学员导出 -- 全部
	 * @param paramMap
	 * @return
	 */
	public List<StuExportBean> selectAllNoClassExport(Map<String, Object> paramMap) {
		List<Map<String, Object>> ltemp = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.StudentMapper.selectAllNoClassExport", paramMap);
		return UnionData(ltemp);
	}

	/**
	 * 查询全部学生列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<StuExportBean> getAllStuExportList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		List<Map<String, Object>> ltemp = studentMapper.getAllStuExportList(paramMap);

		return UnionData(ltemp);
	}

	/**
	 * 查询我管理的学生列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<StuExportBean> getMyManagedStuExportList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		List<Map<String, Object>> ltemp = studentMapper.getMyManagedStuExportList(paramMap);

		return UnionData(ltemp);
	}

	/**
	 * 查询我关注的学生列表
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<StuExportBean> getMyFocusStuExportList(Map<String, Object> paramMap) {
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		List<Map<String, Object>> ltemp = studentMapper.getMyFocusStuExportList(paramMap);
		return UnionData(ltemp);
	}

	/**
	 * 整理数据, 将附加的信息添加到每条记录上,
	 * 
	 * @param list
	 * @return
	 */
	private List<StuExportBean> UnionData(List<Map<String, Object>> list) {
		List<StuExportBean> alret = new ArrayList<StuExportBean>(list.size());
		StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
		UserMapper umapper = dao.getMapper(UserMapper.class);
		int no = 0;
		for (Map<String, Object> mp : list) {
			StuExportBean exbean = new StuExportBean();
			exbean.setNo(no);
			Object otemp = mp.get("idCard");
			if (otemp != null)
				exbean.setIdCard(otemp.toString());

			otemp = mp.get("realName");
			if (otemp != null)
				exbean.setStuName(otemp.toString());
			otemp = mp.get("stuNo");
			if (otemp != null)
				exbean.setStuNo(otemp.toString());

			otemp = mp.get("univName");
			if (otemp != null)
				exbean.setUnivName(otemp.toString());
			User user = umapper.selectByPrimaryKey((Integer) mp.get("teacherid_inspector"));
			if (user != null) {
				String uName = user.getRealName();
				exbean.setInspectorTeaName(uName);
			}

			user = umapper.selectByPrimaryKey((Integer) mp.get("inv_teacher_id"));
			if (user != null) {
				String uName = user.getRealName();
				exbean.setInvTeaName(uName);
			}

			user = umapper.selectByPrimaryKey((Integer) mp.get("teacherid_advisor"));
			if (user != null) {
				String uName = user.getRealName();
				exbean.setAdvisorTeaName(uName);
			}
			int userid = (Integer) mp.get("user_id");
			Map<String, Object> one = getExportMoneyData(userid);
			if (one != null) {
				Object cur = one.get("current_pay_money");
				// System.out.println (cur.getClass().getName());
				exbean.setEntryFeeCurPayMoney((BigDecimal) one.get("current_pay_money"));
				exbean.setEntryFeeFactMoney((BigDecimal) one.get("shouldmoney"));
				exbean.setEntryFeeFavourMoney((BigDecimal) one.get("favour_money"));
				String sname = WebUtil.getSignFeeName((Integer) one.get("is_avaiable"));

				exbean.setEntryFeePayStatus(sname);
				Integer aguserid = (Integer) one.get("agency_userid");
				//System.err.println("agency_user:" + aguserid);
				if (aguserid != null) {
					user = umapper.selectByPrimaryKey(aguserid);
					if (user != null) {
						String uName = user.getRealName();
						exbean.setPayeeUserName(uName);
					}
				}

				exbean.setPayTime((Date) one.get("last_pay_time"));

			}

			System.err.println(exbean.toString());
			alret.add(exbean);
			no++;
		}
		return alret;
	}

	/**
	 * 得到导出报表部分需要的报名费数据项
	 * 
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getExportMoneyData(int userid) {
		Map<String, Object> one = (Map<String, Object>) dao.selectOne(
				"com.lanqiao.dao.StudentMapper.getExportMoneyData", userid);

		return one;
	}

	//统计当前年份已就业/在职/离职/求职中/开除/劝退/退学/延期结业/延期就业/休学重返/休学学员人数
	public int getStuCount(Map<String, Object> paramMap) {
		StudentMapper mapper = dao.getMapper(StudentMapper.class);
		return mapper.getStuCount(paramMap);
	}

	//统计当前年份在职/离职学员人数
	public int getInOrOutWorkCount(Map<String, Object> paramMap) {
		StudentMapper mapper = dao.getMapper(StudentMapper.class);
		return mapper.getInOrOutWorkCount(paramMap);
	}

	//我管理的就业学员人数
	public int getMyManageStuJobCount(Map<String, Object> paramMap) {
		StudentMapper mapper = dao.getMapper(StudentMapper.class);
		return mapper.getMyManageStuJobCount(paramMap);
	}

	//我管理的就业学员列表
	public List<Map<String, Object>> selectMyManageStuJobList(Map<String, Object> paramMap) {
		StudentMapper mapper = dao.getMapper(StudentMapper.class);
		return mapper.selectMyManageStuJobList(paramMap);
	}

	//全部的就业学员人数
	public int getAllStuJobCount(Map<String, Object> paramMap) {
		StudentMapper mapper = dao.getMapper(StudentMapper.class);
		return mapper.getAllStuJobCount(paramMap);
	}

	//全部的就业学员列表
	public List<Map<String, Object>> selectAllStuJobList(Map<String, Object> paramMap) {
		StudentMapper mapper = dao.getMapper(StudentMapper.class);
		return mapper.selectAllStuJobList(paramMap);
	}

	//我关注的就业学员人数
	public int getMyFocusStuJobCount(Map<String, Object> paramMap) {
		StudentMapper mapper = dao.getMapper(StudentMapper.class);
		return mapper.getMyFocusStuJobCount(paramMap);
	}

	//我关注的就业学员列表
	public List<Map<String, Object>> selectMyFocusStuJobList(Map<String, Object> paramMap) {
		StudentMapper mapper = dao.getMapper(StudentMapper.class);
		return mapper.selectMyFocusStuJobList(paramMap);
	}

	/**
	 * 
	* @Description:修改学员报名信息
	* @param user
	* @param student
	* @return
	* @throws Exception 
	* @return int 
	* @author ZhouZhiHua
	* @createTime 2016年12月16日 下午5:22:09
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void editSignUpInfo(User user, Student student) throws Exception {
		//1.
		dao.update("com.lanqiao.dao.UserMapper.updateByPrimaryKeySelective", user);
		//2.
		dao.update("com.lanqiao.dao.StudentMapper.updateByPrimaryKeySelective", student);
	}

}
