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
import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.constant.GlobalConstant.ClassStatusEnum;
import com.lanqiao.constant.GlobalConstant.StuStatusEnum;
import com.lanqiao.dao.TClassStatusLogMapper;
import com.lanqiao.dao.TLqclassMapper;
import com.lanqiao.model.TClassStatusLog;
import com.lanqiao.model.TLqclass;
import com.lanqiao.util.CommonUtil;

@Service
public class ClassStatusService extends BaseService
{
	@Resource
	StudentStatusService studentservice;

	/**
	 * 查询某一个班级的信息.
	 * @param classid
	 * @return
	 */
	public TLqclass getlqClass(int classid)
	{
		TLqclassMapper mapper = dao.getMapper(TLqclassMapper.class);
		TLqclass lqclass = mapper.selectByPrimaryKey(classid);
		return lqclass;
	}
	

	/**
	 * 统计班级人数, 只算正常状态的学员
	 * 
	 * @param classid
	 * @return
	 */
	public int findOpenclassManCount(int classid)
	{
		int ncount = 0;
		HashMap mp = new HashMap();
		mp.put("classid", classid);
		ArrayList al = new ArrayList();
		al.add(StuStatusEnum.NOCLASS.getValue());
		al.add(StuStatusEnum.NOSTARTCLASS.getValue());
		al.add(StuStatusEnum.BESTUDY.getValue());
		al.add(StuStatusEnum.EndStudy.getValue());
		al.add(StuStatusEnum.FindJobing.getValue());
		
		al.add(StuStatusEnum.XIUXUEBack.getValue());
		al.add(StuStatusEnum.DELAYGRADUATE.getValue());
		al.add(StuStatusEnum.DELAYWORK.getValue());
		al.add(StuStatusEnum.WORKED.getValue());
		  
		mp.put("statuslist", al);
		
		ncount = (Integer) dao.selectOne(
				"com.lanqiao.dao.TLqclassMapper.selectCountInClass", mp);

		return ncount;

	}

	/**
	 *  变更班级状态, 
	 * @param classid 班级id
	 * @param newstatus  新状态. 
	 * @param loginuserid 当前登录者
	 * @param happentime  发生时间
	 * @param finishcount ; 发生课时
	 * @param remark 备注
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void UpdateClassStatusOnlyCLass(int classid, int newstatus,
			int loginuserid, Date happentime, int finishcount, String remark)
			throws Exception
	{

		TLqclassMapper mapper = dao.getMapper(TLqclassMapper.class);
		TClassStatusLogMapper logmapper = dao
				.getMapper(TClassStatusLogMapper.class);

		TLqclass lqclass = mapper.selectByPrimaryKey(classid);
		if (lqclass == null)
			throw new Exception("班级不存在..");

		//
		if(lqclass.getStatus() ==newstatus )
		{
			System.out.println ("班级已经处于状态:" + newstatus);
			return ;
		}
		
		// 修改班级的状态.
		TLqclass lqclassnew = new TLqclass();
		lqclassnew.setLqClassId(lqclass.getLqClassId());
		lqclassnew.setStatus(newstatus);
		lqclassnew.setUpdateTime(new Date());
		if (newstatus == ClassStatusEnum.INTHELECTURE.getValue())
		{
			// 开班, 设置实际开班人数
			int nman = findOpenclassManCount(classid);
			lqclassnew.setRealCount(nman);
			lqclassnew.setStartTime(happentime);
		}
		
		if (newstatus == ClassStatusEnum.THEGRADUATION.getValue())
		{
			// 结课时间.
			lqclassnew.setGraduateTime(happentime);
		}

		
		if (newstatus == ClassStatusEnum.SHUTDOWN.getValue())
		{
			// 关闭时间.
			lqclassnew.setCloseTime(happentime);
		}
		
		mapper.updateByPrimaryKeySelective(lqclassnew);
		
		// 插入班级状态变化表.
		TClassStatusLog lqstatus = (TClassStatusLog) dao.selectOne(
				"com.lanqiao.dao.TLqclassMapper.selectLastChangenlog", classid);
		if (lqstatus != null)
		{
			// 修改上一个状态的结束时间.
			TClassStatusLog lq = new TClassStatusLog();
			lq.setClassStatusLogId(lqstatus.getClassStatusLogId());
			lq.setEndTime(happentime);
			lq.setRemark(remark);
			logmapper.updateByPrimaryKeySelective(lq);
		}
		
		// 插入一条新的.
		TClassStatusLog lq = new TClassStatusLog();
		lq.setBeginTime(happentime);
		lq.setClassId(classid);
		lq.setInputtime(new Date());
		lq.setNewstatus(newstatus);
		if (lqstatus != null)
			lq.setOldstatus(lqstatus.getNewstatus());
		lq.setOperatorUserid(loginuserid);
		lq.setRemark(remark);
		lq.setFinishcount(finishcount);
		logmapper.insertSelective(lq);
		
	}

	
	/**
	 * 修改班级状态. 以及将班级内的学员状态也修改.
	 * 
	 * @param newstatus
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void UpdateClassStatus(int classid, int newstatus, int loginuserid,
			Date happentime, int finishcount, String remark, HashMap extparam) throws Exception
	{
		TLqclassMapper mapper = dao.getMapper(TLqclassMapper.class);

		TLqclass lqclass = mapper.selectByPrimaryKey(classid);
		if (lqclass == null)
			throw new Exception("班级不存在.");
		;
		// 只有这些状态才有效.
		if (newstatus >= GlobalConstant.ClassStatusEnum.NOCLASSES.getValue()
				&& newstatus <= GlobalConstant.ClassStatusEnum.INTHEEMPLOYMENT
						.getValue())
		{
			//根据班级状态, 得到班级内的学员的正常状态. 
			int stuoldstatus = CommonUtil.getStudentNewStatus(lqclass
					.getStatus());
			int stunewstatus = CommonUtil.getStudentNewStatus(newstatus);
			// 班级状态名称.
			String newstatusName = WebUtil.getClassStatusByValue(newstatus);
			
			if (stuoldstatus < 0 || stunewstatus < 0)
			{
				throw new Exception("错误的班级状态. ");
			}
			if (stuoldstatus != stunewstatus)
			{
				// 改班级内的学生状态, 先得到需要修改状态的班级学生.
				HashMap mp = new HashMap();
				mp.put("status", stuoldstatus);
				mp.put("classid", classid);

				List<Integer> stulist = (List<Integer>) dao.selectList(
						"com.lanqiao.dao.TLqclassMapper.selectUseridInClass",
						mp);
				// 修改每个学生的状态.
				for (Integer a : stulist)
				{
					studentservice.UpdateStudentStatus(a, stunewstatus,
							happentime, "班级状态修改为:" + newstatusName, extparam,loginuserid);
				}
				
			}
			// 修改班级表的状态,并添加状态变化记录表
			UpdateClassStatusOnlyCLass(classid, newstatus, loginuserid,
					happentime, finishcount, remark);

		} else
		{
			// 修改班级表的状态,并添加状态变化记录表
			UpdateClassStatusOnlyCLass(classid, newstatus, loginuserid,
					happentime, finishcount, remark);

		}

	}
	
	/**
	 * 
	 * @Description:根据班级id获取班级的状态列表
	 * @param userId
	 * @return
	 * @return List<Map<String,Object>>
	 * @author ZhouZhiHua
	 * @createTime 2016年12月7日 上午10:13:00
	 */
	public List<Map<String, Object>> getClassStatusLogByClassId(Integer classId)
	{
		return (List<Map<String, Object>>) dao
				.selectList(
						"com.lanqiao.dao.TClassStatusLogMapper.getClassStatusLogByClassId",
						classId);
	}

}
