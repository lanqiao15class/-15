package com.lanqiao.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.common.WebUtil;
import com.lanqiao.constant.GlobalConstant.ClassStatusEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.constant.GlobalConstant.jobStatusEnum;
import com.lanqiao.dao.StudentMapper;
import com.lanqiao.dao.TJobDetailMapper;
import com.lanqiao.model.Student;
import com.lanqiao.model.TJobDetail;
import com.lanqiao.model.TStudentclassLog;

@Service
public class JobService extends BaseService
{
	
	@Resource 
	DictService dictservice;
	
	@Resource
	StudentStatusService statusservice;
	
	
	@Resource
	StudentclassLogService studentclassLogService;

	@Resource
	ClassStatusService classervice;
	
	
	/**
	 * 批量检查多个学生的状态. 
	 * @param stulist
	 * @return
	 */
	public List<Map> getBatchUserJobStatus(List<Integer> stulist)
	{
		 List<Map>  ret = null;
		HashMap mp = new HashMap();
		mp.put("stulist", stulist);
		
		ret = (List<Map>  ) dao.selectList(
				"com.lanqiao.dao.TJobDetailMapper.getStudentJobStatus", mp);

		return ret;
	}
	
	
	/**
	 * 查询某一班级未就业的人数.
	 * @param classid
	 * @return
	 */
	
	
	public List<Student> findWorkedStudentList(int classid)
	{
		int ncount = 0;
		HashMap mp = new HashMap();
		mp.put("classid", classid);
		List al = WebUtil.getNormalStuStatusId();
		 
		  
		mp.put("statuslist", al);
		
		 List<Student>l  = ( List<Student>) dao.selectList(
				"com.lanqiao.dao.TLqclassMapper.selectListInClass100Worked", mp);

		return l;
	}
	
	
	private String getCourseTypeName(String sid, List<Map<String,Object>> mplist)
	{
		String sret="";
		if(sid ==null) return sret;
		for(Map<String,Object> mp : mplist)
		{
			if(sid.equals(mp.get("value")))
			{
				sret = mp.get("label").toString();
				break;
			}
			
			
		}
		return sret;
		
	}
	
	
	/**
	 * 保存就业信息.
	 * @param job
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void SaveJobDetail(TJobDetail job, int loginuserid) throws Exception 
	{
		TJobDetailMapper mapper = dao.getMapper(TJobDetailMapper.class);
		job.setOccupationStatus(jobStatusEnum.JOBING.getValue());
		job.setEntryUserid(loginuserid);
		job.setInputtimeIn(new Date());
		mapper.insertSelective(job);
		
		//修改首次入职时间. 
		StudentMapper stmapper = dao.getMapper(StudentMapper.class);
		
		Student st = stmapper.selectByPrimaryKey(job.getUserId());
		Student stnew = new Student();
		
		if(st.getEmpTime() ==null || st.getEmpTime().after(job.getEntryTime()))
		{
			stnew.setEmpTime(job.getEntryTime());
		}
		
		//更新学生的就业方式,就业状态. 
		stnew.setUserId(st.getUserId());
		stnew.setJobstatus(jobStatusEnum.JOBING.getValue());
		stnew.setJobfromtype(job.getJobfromType());
		stmapper.updateByPrimaryKeySelective(stnew);
		
		// 修改学生状态为已经就业. 
		statusservice.UpdateStudentStatus(st.getUserId(), StuStatusEnum.WORKED.getValue(),
				job.getEntryTime(), "学员首次成功入职", loginuserid);
		
		 List<Student>stlist = findWorkedStudentList(st.getLqClassId());
		 boolean isallworked =true;
		 for(Student one : stlist)
		 {
			 if(one.getStatus() != StuStatusEnum.WORKED.getValue())
			 {
				 isallworked =false;
				 break;
			 }
			 
		 }
		 
		 
		if(isallworked)  //100%就业. 
		{
			
			// 修改班级表状态, 100% 就业. 
			//  添加班级状态变化记录.
			 for(Student one : stlist)
			 {
				 
				 //更新学生的换班记录表,修改退出时间.
					Integer oldLogId = studentclassLogService.getLastLogByUserId(one.getUserId());
					if(oldLogId !=null)
					{
						TStudentclassLog oldLog = new TStudentclassLog();
						oldLog.setStudentclassLogId(oldLogId);
						oldLog.setExitTime(new Date());
						oldLog.setRemark("100%就业,退出班级");
						studentclassLogService.updateByPrimaryKeySelective(oldLog);
					}
				 
			 }
			 
			// 修改班级表状态, 100% 就业. 
			//  添加班级状态变化记录.
			 classervice.UpdateClassStatusOnlyCLass(st.getLqClassId(), 
					 ClassStatusEnum.OFTHEEMPLOYMENT.getValue(), 
					 loginuserid, 
					 new Date(), 
					 0, 
					 "100%就业");
			 
			 
		}
	}

	/**
	 * 获取班级内学员的信息. 
	 * @param classid
	 * @return
	 * @throws Exception
	 */
	public List<Map> getStudentById(Integer userids[])throws Exception 
	{
		List<Map<String,Object>> mplist = dictservice.getDictByType(dictservice.DICT_COURSE_TYPE);
		//value,label 
		HashMap p = new HashMap();
		p.put("userids", userids);
		
		List<Map> list=(List<Map> )dao.selectList("com.lanqiao.dao.TJobDetailMapper.selectStudentList", p);
		int i=1;
		for(Map mp :list)
		{
			//课程名称.
			String courseid = mp.get("courseid").toString();
			String coursename = getCourseTypeName(courseid,mplist );
			mp.put("courseType", coursename);
			
			mp.put("indexNo", i);
			
			i++;
		}
		
		return list;
	}
}
