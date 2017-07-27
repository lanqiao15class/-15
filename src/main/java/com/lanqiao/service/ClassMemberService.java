package com.lanqiao.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.dao.SysCourseMapper;
import com.lanqiao.dao.TLqclassMapper;
import com.lanqiao.model.SysCourse;
import com.lanqiao.model.TLqclass;
import com.lanqiao.util.CommonUtil;

@Service
public class ClassMemberService extends BaseService {
	@Resource
	DictService dictservice;

	@Resource
	TLqclassService lqservice;

	/**
	 * 查询某一个班级的信息.
	 * 
	 * @param classid
	 * @return
	 */
	public TLqclass getlqClass(int classid) {
		TLqclassMapper mapper = dao.getMapper(TLqclassMapper.class);
		TLqclass lqclass = mapper.selectByPrimaryKey(classid);
		return lqclass;
	}

	private String getCourseTypeName(String sid, List<Map<String, Object>> mplist) {
		String sret = "";
		if (sid == null)
			return sret;
		for (Map<String, Object> mp : mplist) {
			if (sid.equals(mp.get("value"))) {
				sret = mp.get("label").toString();
				break;
			}

		}
		return sret;

	}

	/**
	 * 只统计正常状态的学员人数.
	 * 
	 * @param classid
	 * @return
	 * @throws Exception
	 */
	public int getlqClassMemberCount(int classid) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lqClassId", classid);
		int nret = lqservice.getClassCurrentCount(paramMap);
		return nret;
	}

	/**
	 * 获取总课时
	 * 
	 * @param classid
	 * @return
	 * @throws Exception
	 */
	public SysCourse getcourse(int courseid) throws Exception {
		SysCourseMapper mapper = dao.getMapper(SysCourseMapper.class);
		SysCourse course = mapper.selectByPrimaryKey(courseid);
		return course;
	}

	/**
	 * 获取已上课时
	 * 
	 * @param classid
	 * @return
	 * @throws Exception
	 */
	public Integer selectsyllabusbyclassId(int classid) throws Exception {
		Integer i = (Integer) dao.selectOne("com.lanqiao.dao.SysCourseMapper.selectsyllabusbyclassId", classid);
		if (i == null) {
			i = 0;

		}
		return i;
	}

	/**
	 * 获取班级内学员的信息.
	 * 
	 * @param classid
	 * @return
	 * @throws Exception
	 */
	public List<Map> getlqClassMember(int classid) throws Exception {
		List<Map<String, Object>> mplist = dictservice.getDictByType(dictservice.DICT_COURSE_TYPE);
		// value,label

		List<Map> list = (List<Map>) dao.selectList("com.lanqiao.dao.TLqclassMapper.selectMemberInOneClass", classid);
		int i = 1;
		for (Map mp : list) {
			boolean isGray = true;

			// 课程名称.
			String courseid = mp.get("courseid").toString();
			String coursename = getCourseTypeName(courseid, mplist);
			mp.put("courseType", coursename);
			// 学生状态.
			int nstatus = (Integer) mp.get("statusid");
			String statusname = WebUtil.getStudentStatusName(nstatus);
			mp.put("statusName", statusname);

			// 报名费.
			int isavaiable = (Integer) mp.get("isavaiable");
			String signupFee = WebUtil.getSignFeeName(isavaiable);
			mp.put("availName", signupFee);

			// 实训费.
			int paidid = (Integer) mp.get("haspaid");
			String hasPaid = WebUtil.getPaydNameByid(paidid);
			mp.put("hasPaidName", hasPaid);

			// updateTime ;

			Date d = (Date) mp.get("updateTime");

			String updateTime = CommonUtil.formatDate(d);
			mp.put("updateTime", updateTime);

			mp.put("indexNo", i);

			int nowclassid = 0;
			int oldclassid = 0;
			Integer otemp = (Integer) mp.get("nowclassid");
			if (otemp != null)
				nowclassid = otemp.intValue();

			otemp = (Integer) mp.get("classid");
			if (otemp != null)
				oldclassid = otemp.intValue();

			if (oldclassid != nowclassid) {
				isGray = true;

			} else {
				if (nstatus == StuStatusEnum.NOSTARTCLASS.getValue() || nstatus == StuStatusEnum.BESTUDY.getValue()
						|| nstatus == StuStatusEnum.EndStudy.getValue()
						|| nstatus == StuStatusEnum.FindJobing.getValue() || nstatus == StuStatusEnum.WORKED.getValue()) {
					isGray = false;
				}

			}

			mp.put("isgray", isGray);

			i++;
		}

		return list;
	}

	public List<Map> selectMemberStudent(Map parm) throws Exception {

		List<Map> list = (List<Map>) dao.selectList("com.lanqiao.dao.TLqclassMapper.selectMemberStudent",
				parm.get("classid"));
		int i = 1;
		for (Map map : list) {
			//男  女 
			String sexname = "";
			String sex = (String) map.get("sex");
			if (StringUtils.isNotBlank(sex)) {
				if (StringUtils.equals(sex, "0")) {
					sexname = "男";
				} else if (StringUtils.equals(sex, "1")) {
					sexname = "女";
				}
			}
			map.put("sexname", sexname);
			boolean isGray = true;
			// 学生状态.
			int nstatus = (Integer) map.get("statusid");
			String statusname = WebUtil.getStudentStatusName(nstatus);
			map.put("statusName", statusname);
			Integer userid = (Integer) map.get("userid");
			parm.put("userid", userid);
			parm.put("userid1", userid);
			parm.put("userid2", userid);
			parm.put("userid3", userid);
			Map map2 = (HashMap) dao.selectOne("com.lanqiao.dao.TLqclassMapper.selectMemberStudentsign", parm);
			map2.put("indexNo", i);
			map.putAll(map2);
			i++;

			int nowclassid = 0;
			int oldclassid = 0;
			Integer otemp = (Integer) map.get("nowclassid");
			if (otemp != null)
				nowclassid = otemp.intValue();

			otemp = (Integer) map.get("classid");
			if (otemp != null)
				oldclassid = otemp.intValue();

			if (oldclassid != nowclassid) {
				isGray = true;

			} else {
				if (nstatus == StuStatusEnum.NOSTARTCLASS.getValue() || nstatus == StuStatusEnum.BESTUDY.getValue()
						|| nstatus == StuStatusEnum.EndStudy.getValue()
						|| nstatus == StuStatusEnum.FindJobing.getValue() || nstatus == StuStatusEnum.WORKED.getValue()) {
					isGray = false;
				}

			}
			map.put("isgray", isGray);
		}
		return list;

	}
}
