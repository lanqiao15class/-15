package com.lanqiao.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.dao.StudentMapper;
import com.lanqiao.dao.TStudentStatusLogMapper;
import com.lanqiao.model.Student;
import com.lanqiao.model.TStudentStatusLog;
import com.lanqiao.model.TStudentclassLog;

@Service
public class StudentStatusService extends BaseService {
	@Resource
	StudentclassLogService studentclassLogService;

	/**
	 * 将学生的状态变化为另外一个状态
	 * 
	 * @param userid
	 *            : 学生id
	 * @param statuscode
	 *            : 新的状态码
	 * @param happentime
	 *            : 发生日期
	 * @param remark
	 *            : 备注,
	 * @param loginuserid
	 *            : 当前登录者id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean UpdateStudentStatus(int userid, int statuscode, Date happentime, String remark, int loginuserid)
			throws Exception {
		return UpdateStudentStatus(userid, statuscode, happentime, remark, new HashMap(), loginuserid);
	}

	/**
	 * 按id 查询学生信息.
	 * 
	 * @param userid
	 * @return
	 */
	public Student getStudentByid(int userid) {
		StudentMapper mapper = (StudentMapper) dao.getMapper(StudentMapper.class);
		Student sret = mapper.selectByPrimaryKey(userid);
		return sret;
	}

	/**
	 * 修改学生的状态.
	 * 
	 * @param userid
	 *            : 学生ID
	 * @param statuscode
	 *            : 新的状态码 remark: 备注信息. happentime :发生变化的时间.
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean UpdateStudentStatus(int userid, int statuscode, Date happentime, String remark, HashMap otherdata,
			int loginuserid) throws Exception {
		boolean bret = false;
		HashMap mp = new HashMap();
		mp.put("userid", userid);
		mp.put("status", statuscode);

		TStudentStatusLogMapper mapper = dao.getMapper(TStudentStatusLogMapper.class);
		StudentMapper studentmapper = dao.getMapper(StudentMapper.class);
		Student oldst = studentmapper.selectByPrimaryKey(userid);
		if (oldst.getStatus() == statuscode) {
			System.out.println("当前学生" + userid + "已经处于状态:" + statuscode + ", 拒绝再次修改同样的状态.");
			return true;
		}

		// 修改上一次的截止时间
		TStudentStatusLog log = (TStudentStatusLog) dao.selectOne(
				"com.lanqiao.dao.TStudentStatusLogMapper.selectPrevious_Log", mp);
		if (log != null) {
			log.setEndtTime(happentime);
			mapper.updateByPrimaryKeySelective(log);
		}

		TStudentStatusLog newlog = new TStudentStatusLog();
		newlog.setBeginTime(happentime);
		if (otherdata != null) {
			Date backtime = (Date) otherdata.get("otherdate");
			if (backtime != null)
				newlog.setBacktime(backtime);
			Integer finishcount = (Integer) otherdata.get("finishcount");
			if (finishcount != null)
				newlog.setFinishCount(finishcount);
		}

		newlog.setInputtime(new Date());
		newlog.setNewstatus(statuscode);
		newlog.setOldstatus(0);
		if (log != null)
			newlog.setOldstatus(log.getNewstatus());

		newlog.setOperatorUserid(loginuserid);
		newlog.setRemark(remark);
		newlog.setLqclassid(oldst.getLqClassId());

		newlog.setUserId(userid);
		mapper.insertSelective(newlog);

		Student st = new Student();
		st.setUserId(userid);
		st.setStatus(statuscode);

		// 修改学生表的结业时间
		if (otherdata != null) {
			Date endstudytime = (Date) otherdata.get("endstudytime");
			if (endstudytime != null && statuscode == StuStatusEnum.FindJobing.getValue()) {
				st.setEndStudytime(endstudytime);
			}
		}

		if (statuscode == StuStatusEnum.FindJobing.getValue()) {
			// 计算就服务开始时间, 就业服务截止时间.
			// 就业服务开始时间+90天，若跨年，就业服务开始时间+105天

			Calendar ca = Calendar.getInstance();
			ca.setTime(happentime);
			int year = ca.get(Calendar.YEAR);
			ca.add(Calendar.DATE, 90);//
			int newyear = ca.get(Calendar.YEAR);
			System.out.println(year + ":" + newyear);
			if (newyear != year) {
				ca.setTime(happentime);
				ca.add(Calendar.DATE, 105);
			}

			Date jobendtime = ca.getTime();

			st.setJobserviceEndtime(jobendtime);
			st.setJobserviceStarttime(happentime);
		}

		// 计算结业时间.
		if (statuscode == StuStatusEnum.EndStudy.getValue()) {
			st.setEndStudytime(happentime);
		}

		// 计算开始培训时间.
		if (statuscode == StuStatusEnum.BESTUDY.getValue()) {
			System.out.println("st.getBeginStudytime()=" + st.getBeginStudytime());
			if (st.getBeginStudytime() == null)
				st.setBeginStudytime(happentime);
			else if (oldst.getBeginStudytime().after(happentime)) {
				st.setBeginStudytime(happentime);
			}
		}
		//学生退出班级操作, 一些状态变化会引起学生退出班级的行为 . 
		if (statuscode == StuStatusEnum.EXPEL.getValue() || statuscode == StuStatusEnum.QUANTUI.getValue()
				|| statuscode == StuStatusEnum.LEAVESCHOLL.getValue() || statuscode == StuStatusEnum.XIUXUE.getValue()
				|| statuscode == StuStatusEnum.DELAYGRADUATE.getValue()
				|| statuscode == StuStatusEnum.DELAYWORK.getValue())

		{
			Integer oldLogId = studentclassLogService.getLastLogByUserId(userid);
			if (oldLogId != null) {
				TStudentclassLog oldLog = new TStudentclassLog();
				oldLog.setStudentclassLogId(oldLogId);
				oldLog.setExitTime(happentime);
				oldLog.setRemark(remark);
				studentclassLogService.updateByPrimaryKeySelective(oldLog);
			}

			//st.setLqClassId(0); // 清除当前所在班级标记.
		}

		int nval = studentmapper.updateByPrimaryKeySelective(st);

		bret = (nval == 1) ? true : false;

		return bret;
	}

	/**
	 * 
	 * @Description:根据学员id获取学员的状态列表
	 * @param userId
	 * @return
	 * @return List<Map<String,Object>>
	 * @author ZhouZhiHua
	 * @createTime 2016年12月7日 上午10:13:00
	 */
	public List<Map<String, Object>> getStuStatusLogByUserId(Integer userId) {
		return (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.TStudentStatusLogMapper.getStuStatusLogByUserId", userId);
	}

}
