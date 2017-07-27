package com.lanqiao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.common.SessionUser;
import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.dao.StudentMapper;
import com.lanqiao.dao.TClassStatusLogMapper;
import com.lanqiao.dao.TDataTagMapper;
import com.lanqiao.dao.TLqclassMapper;
import com.lanqiao.model.Student;
import com.lanqiao.model.TClassStatusLog;
import com.lanqiao.model.TDataTag;
import com.lanqiao.model.TLqclass;
import com.lanqiao.model.TStudentclassLog;
import com.lanqiao.util.CommonUtil;

@Service
public class TLqclassService extends BaseService {
	private final static Logger logger = LogManager.getLogger(TLqclassService.class);

	@Resource
	StudentStatusService studentStatusService;

	@Resource
	StudentclassLogService studentclassLogService;

	@Resource
	ClassStatusService classStatusService;

	/**	按班级状态搜索班级（分班newIntoClass、合班changeClass、转班mergeClass）	*/
	public List<Map<String, Object>> classForStuIntoClass(String type) throws Exception {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		List<Integer> list = new ArrayList<Integer>();//新分班可以搜索：未开课、授课中
		list.add(GlobalConstant.ClassStatusEnum.NOCLASSES.getValue());
		list.add(GlobalConstant.ClassStatusEnum.INTHELECTURE.getValue());
		if (type != null && type.equals("changeClass")) {//转班可以搜索：未开课、授课中、集训前结课、集训中
			list.add(GlobalConstant.ClassStatusEnum.BEFORETHETRAININGSESSION.getValue());
			list.add(GlobalConstant.ClassStatusEnum.INTHETRAINING.getValue());
		} else if (type != null && type.equals("mergeClass")) {//合班可以搜索：未开课、授课中、集训前结课、集训中
			list.add(GlobalConstant.ClassStatusEnum.BEFORETHETRAININGSESSION.getValue());
			list.add(GlobalConstant.ClassStatusEnum.INTHETRAINING.getValue());
		}

		return tLqclassMapper.classForStuIntoClass(list);
	}

	/** 根据id获取班级详情 */
	public TLqclass classDetail(Integer classId) {
		return (TLqclass) dao.selectOne("com.lanqiao.dao.TLqclassMapper.selectByPrimaryKey", classId);
	}

	/** 新增班级  */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addClass(TLqclass tLqclass, int loginuserid) throws Exception {
		//插入班级
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		tLqclassMapper.insertSelectiveReturnId(tLqclass);
		//插入班级状态表
		TClassStatusLogMapper tClassStatusLogMapper = dao.getMapper(TClassStatusLogMapper.class);
		TClassStatusLog lq = new TClassStatusLog();
		lq.setBeginTime(new Date());
		lq.setClassId(tLqclass.getLqClassId());
		lq.setInputtime(new Date());
		lq.setNewstatus(GlobalConstant.ClassStatusEnum.NOCLASSES.getValue());
		lq.setOldstatus(null);
		lq.setOperatorUserid(loginuserid);
		lq.setRemark("新建班级");
		tClassStatusLogMapper.insertSelective(lq);

	}

	/** 班级名称唯一性检验  */
	public int classNameUniqueCheck(Map<String, Object> map) throws Exception {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.classNameUniqueCheck(map);
	}

	/**
	 * 
	* @Description:根据班级id获取班级详情
	* @param studentId
	* @return 
	* @return List<Map<String,Object>> 
	* @author ZhouZhiHua
	* @createTime 2016年12月9日 下午5:10:39
	 */
	public Map<String, Object> getLqClassInfoList(int classId) {
		return (Map<String, Object>) dao.selectOne("com.lanqiao.dao.TLqclassMapper.getLqClassInfoList", classId);
	}

	/** 获取某个班级的可合班学员人数  */
	public int studentCountCanMerge(Map<String, Object> map) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.studentCountCanMerge(map);
	}

	/** 获取可合班学员的ids，参数:classId，指定的学员状态list */
	public List<Integer> getStuIdsCanMerge(Map<String, Object> map) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.getStuIdsCanMerge(map);
	}

	/** 获取可合班的班级详情列表 */
	public List<Map<String, Object>> getMergeClassData(List<Integer> list) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.getMergeClassData(list);
	}

	/**
	 * 我关注的班级列表
	 * **/
	public List<Map<String, Object>> selectMyFocusClassList(Map<String, Object> paramMap) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.selectMyFocusClassList(paramMap);
	}

	/**我关注的班级记录数**/
	public int getMyFocusClassCount(Map<String, Object> paramMap) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.getMyFocusClassCount(paramMap);
	}

	//全部班级列表
	public List<Map<String, Object>> selectAllClassList(Map<String, Object> paramMap) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.selectAllClassList(paramMap);
	}

	//全部班级记录数
	public int getAllClassCount(Map<String, Object> paramMap) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.getAllClassCount(paramMap);
	}

	//我管理的班级列表
	public List<Map<String, Object>> selectMyManageClassList(Map<String, Object> paramMap) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.selectMyManageClassList(paramMap);
	}

	//我管理的班级记录数
	public int getMyManageClassCount(Map<String, Object> paramMap) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.getMyManageClassCount(paramMap);
	}

	//获取班级的当前人数
	public int getClassCurrentCount(Map<String, Object> paramMap) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		StringBuffer sta = new StringBuffer();
		sta.append(GlobalConstant.StuStatusEnum.NOSTARTCLASS.getValue()).append(",");//未开课
		sta.append(GlobalConstant.StuStatusEnum.BESTUDY.getValue()).append(",");//在读
		sta.append(GlobalConstant.StuStatusEnum.EndStudy.getValue()).append(",");//结业
		sta.append(GlobalConstant.StuStatusEnum.FindJobing.getValue()).append(",");//结业求职中
		sta.append(GlobalConstant.StuStatusEnum.WORKED.getValue());//已就业
		paramMap.put("stuStatusParam", sta);
		return tLqclassMapper.getClassCurrentCount(paramMap);
	}

	//获取当前年份我关注的新开班数
	public int getNewClassCount() {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.getNewClassCount();
	}

	/**
	 * 
	* @Description:根据班级id修改班级信息
	* @param map 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年12月10日 下午3:14:55
	 */
	public void editLqClassInfo(Map<String, Object> map) {
		TLqclass lqclass = new TLqclass();
		lqclass.setLqClassId(Integer.parseInt(map.get("lqClassId").toString()));
		if (map.get("comTeacherId") != null) {
			lqclass.setComTeacherId(Integer.parseInt(map.get("comTeacherId").toString()));
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
		if (map.get("qqGroup") != null) {
			lqclass.setQqGroup(map.get("qqGroup").toString());
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
		if (map.get("cepTeacherId") != null && !"null".equals(map.get("cepTeacherId"))) {
			lqclass.setCepTeacherId(Integer.parseInt(map.get("cepTeacherId").toString()));
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
		if (map.get("brokersId") != null && !"null".equals(map.get("brokersId"))) {
			lqclass.setChrTeacherId(Integer.parseInt(map.get("brokersId").toString()));
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
		if (map.get("monitorId") != null) {
			lqclass.setMonitorId(Integer.parseInt(map.get("monitorId").toString()));
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
		if (map.get("typePre") != null && map.get("typeReal") != null) {
			lqclass.setTypePre(map.get("typePre").toString());
			lqclass.setTypeReal(map.get("typeReal").toString());
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
		if (map.get("courseType") != null) {
			lqclass.setCourseType(Integer.parseInt(map.get("courseType").toString()));
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
		if (map.get("expectSchoolEndtime") != null) {
			lqclass.setExpectSchoolEndtime(CommonUtil.dateStrToDate(map.get("expectSchoolEndtime").toString()));
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
		if (map.get("schoolEndtime") != null) {
			lqclass.setSchoolEndtime(CommonUtil.dateStrToDate(map.get("schoolEndtime").toString()));
			dao.update("com.lanqiao.dao.TLqclassMapper.updateByPrimaryKeySelective", lqclass);
		}
	}

	public List<Map<String, Object>> getLqClassStuListByClassId(int classId) {
		return (List<Map<String, Object>>) dao.selectList("com.lanqiao.dao.TLqclassMapper.getLqClassStuListByClassId",
				classId);
	}

	//执行
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean mergeClass(Map<String, Object> map, HttpServletResponse response, HttpServletRequest request)
			throws Exception {

		logger.info("###################{}时发生{}###################", new Date().toString(), "修改，合班");
		int newClassId = Integer.parseInt((String) map.get("newClassId"));//新班级id

		//1，根据新班级的状态获取对应班级下的学员正常状态
		TLqclass tLqclass = classDetail(newClassId);
		Integer newClassStatus = tLqclass.getStatus();//获取新班级的状态
		Integer classCourseType = tLqclass.getCourseType();//获取班级课程类别

		Integer newStatus = CommonUtil.getStudentNewStatus(newClassStatus);//获取对应班级下的学员正常状态

		//2，发生时间、操作人、新班级id、描述
		Date happentime = CommonUtil.dateStrToDateWithTime((String) map.get("happenTime"));
		SessionUser sUser = WebUtil.getLoginUser(request);
		Integer loginUserId = sUser.getUserId();
		String remark = (String) map.get("remark");

		String classIds = (String) map.get("ids");//班级ids
		if (classIds == null) {
			return false;//获取班级ids失败
		} else {
			String[] classIdArray = classIds.split(",");
			for (String string : classIdArray) {
				int oldClassId = Integer.parseInt(string);
				if (oldClassId == newClassId) {
					continue;//***如果是原班级，那么跳过下面的步骤***/
				}
				int finishcount = Integer.parseInt((String) map.get("class" + string));//获取每个班级对应的已上课程数
				//1.设置班级状态为关闭,设置班级状态变更表
				classStatusService.UpdateClassStatusOnlyCLass(oldClassId,
						GlobalConstant.ClassStatusEnum.SHUTDOWN.getValue(), loginUserId, happentime, finishcount,
						remark);

				//2，获取该班级下的所有可换班学员id
				Map<String, Object> mapGetStuIdList = new HashMap<String, Object>();

				List<Integer> listStatus = new ArrayList<>();//可以合班的学员状态
				listStatus.add(GlobalConstant.StuStatusEnum.NOSTARTCLASS.getValue());
				listStatus.add(GlobalConstant.StuStatusEnum.BESTUDY.getValue());
				listStatus.add(GlobalConstant.StuStatusEnum.EndStudy.getValue());
				listStatus.add(GlobalConstant.StuStatusEnum.FindJobing.getValue());
				mapGetStuIdList.put("classId", oldClassId);
				mapGetStuIdList.put("list", listStatus);

				//3，处理每个班级的学员变更【学员表，学员班级记录表、学员状态变更表】
				TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
				List<Integer> list = tLqclassMapper.getStuIdsCanMerge(mapGetStuIdList);
				if (list.size() > 0) {
					for (Integer stuId : list) {
						//1,关闭学员上一个 学员班级记录表
						Integer oldLogId = studentclassLogService.getLastLogByUserId(stuId);
						if (oldLogId != null) {
							TStudentclassLog oldLog = new TStudentclassLog();
							oldLog.setStudentclassLogId(oldLogId);
							oldLog.setExitTime(happentime);
							oldLog.setRemark(remark.trim());
							oldLog.setFinishcount(finishcount);
							studentclassLogService.updateByPrimaryKeySelective(oldLog);
						}
						//2，添加一条学员班级记录表
						TStudentclassLog newLog = new TStudentclassLog();
						newLog.setClassId(newClassId);
						newLog.setCreateTime(happentime);//入班时间
						newLog.setRemark(remark.trim());
						newLog.setUserId(stuId);
						newLog.setOperUserid(loginUserId);
						studentclassLogService.insertSelective(newLog);

						//3,学员表、学员状态记录表中的状态变化
						studentStatusService.UpdateStudentStatus(stuId, newStatus, happentime, remark, loginUserId);

						//4，学员的课程、班级、更新时间变化
						StudentMapper studentMapper = dao.getMapper(StudentMapper.class);
						Student student = new Student();
						student.setUserId(stuId);
						student.setUpdateTime(new Date());
						student.setCourseType(classCourseType + "");
						student.setLqClassId(newClassId);
						studentMapper.updateByPrimaryKeySelective(student);
					}
				}

			}
			return true;
		}

	}

	//取消关注
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void cancelFocusBatch(String lqClassIds, Integer userId) throws Exception {
		TDataTagMapper dataTagMapper = dao.getMapper(TDataTagMapper.class);
		TDataTag dataTag = new TDataTag();
		String userIds[] = lqClassIds.split(",");
		for (String s : userIds) {
			dataTag.setDatatype(2);//班级
			dataTag.setDataId(Integer.parseInt(s));
			dataTag.setTagtype(1);
			dataTag.setUserid(userId);
			int row = dataTagMapper.teaIfRemarkStu(dataTag);
			if (row > 0) {
				dataTagMapper.cancelFocus(dataTag);
			}
		}
	}

	//标记为我关注
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void remarkMyFocusBatch(String lqClassIds, Integer userId) throws Exception {
		TDataTagMapper dataTagMapper = dao.getMapper(TDataTagMapper.class);
		TDataTag dataTag = new TDataTag();
		String userIds[] = lqClassIds.split(",");
		for (String s : userIds) {
			dataTag.setDatatype(2);//班级
			dataTag.setDataId(Integer.parseInt(s));
			dataTag.setTagtype(1);
			dataTag.setUserid(userId);
			int row = dataTagMapper.teaIfRemarkStu(dataTag);
			if (row <= 0) {
				dataTagMapper.insertSelective(dataTag);//标记为我关注的	
			}

		}
	}

	//获取学员的当前所在班级信息
	public Map<String, Object> getMyClassInfo(Integer userId) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.getMyClassInfo(userId);
	}

	//根据学员userid获取其所在班级id
	public Integer selectClassIdByUserId(Integer userId) {
		TLqclassMapper tLqclassMapper = dao.getMapper(TLqclassMapper.class);
		return tLqclassMapper.selectClassIdByUserId(userId);
	}

	//根据班级获取已上课时数
	public Integer selectcourse_arrangementbyclassId(Integer classId) {
		Integer sum = (Integer) dao.selectOne("com.lanqiao.dao.TLqclassMapper.selectcoursearrangementbyclassId",
				classId);
		if (sum == null) {

			sum = 0;
		}
		return sum;
	}

	public Integer selectClassRealCount(Integer classId){
		return (Integer) dao.selectOne("com.lanqiao.dao.TLqclassMapper.selectClassRealCount",classId);
	}
}
