package com.lanqiao.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lanqiao.dao.TVisitSubLogMapper;
import com.lanqiao.dao.TVisit_logMapper;
import com.lanqiao.model.TVisitSubLog;
import com.lanqiao.model.TVisit_log;
import com.lanqiao.util.CommonUtil;

@Service
public class VisitService extends BaseService {

	/**
	 * 插入跟踪记录
	 * @param param
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean InsertVisit(HashMap<String, Object> param, int loginuserid) throws Exception {
		boolean b = false;
		TVisit_log vlog = new TVisit_log();
		vlog.setCreatetime(new java.util.Date());
		vlog.setCreatorUserid(loginuserid);
		vlog.setVisitContent((String) param.get("vcontent"));
		vlog.setVisitDatatype(2);
		vlog.setVisitGoal((String) param.get("vgoal"));
		vlog.setVisitTime(CommonUtil.dateStrToDate((String) param.get("visittime")));

		TVisit_logMapper mpper = dao.getMapper(TVisit_logMapper.class);
		if (param.get("visitid") != null && StringUtils.isNotBlank(param.get("visitid").toString())) {
			//修改
			vlog.setVisitId(Integer.valueOf(param.get("visitid").toString()));
			int nval = mpper.updateByPrimaryKeySelective(vlog);
			if (nval == 1) {
				TVisitSubLogMapper submapper = dao.getMapper(TVisitSubLogMapper.class);
				int mval = submapper.deleteByvisitId(vlog.getVisitId());
				if (mval > 0) {
					String sa[] = ((String) param.get("stid")).split(",");
					for (String s : sa) {
						TVisitSubLog sublog = new TVisitSubLog();
						sublog.setVisitId(vlog.getVisitId());
						sublog.setDataId(new Integer(s));
						submapper.insert(sublog);
					}
					b = true;
				}
			}
		} else {
			int nval = mpper.insert(vlog);
			if (nval == 1) {
				TVisitSubLogMapper submapper = dao.getMapper(TVisitSubLogMapper.class);

				String sa[] = ((String) param.get("stid")).split(",");
				for (String s : sa) {
					TVisitSubLog sublog = new TVisitSubLog();
					sublog.setVisitId(vlog.getVisitId());
					sublog.setDataId(new Integer(s));
					submapper.insert(sublog);
				}
				b = true;
			}
		}

		return b;

	}

	/**
	 * 查询访谈对象的信息
	 * @param vid
	 * @return
	 */
	public List<HashMap> findVisitManInfo(long vid) {
		List<HashMap> mp = (List<HashMap>) dao.selectList("com.lanqiao.dao.TVisit_logMapper.FindVisitlog_maninfo",
				(int) vid);
		return mp;

	}

	/**
	 * 查询访谈对象的信息
	 * @param vid
	 * @return
	 */
	public HashMap selectMemberInOneUser(int vid) {
		HashMap mp = (HashMap) dao.selectOne("com.lanqiao.dao.TLqclassMapper.selectMemberInOneUser", vid);
		return mp;

	}

	/**
	 * 查询意向学员的访谈记录.
	 * @param parmlist
	 * @return
	 */

	public List<HashMap> findVisitLog(HashMap parmlist) {
		List<HashMap> list = null;
		list = (List<HashMap>) dao.selectList("com.lanqiao.dao.TVisit_logMapper.FindVisitlog", parmlist);
		return list;

	}

	public int findVisitLogCount(HashMap parmlist) {
		Integer nval = (Integer) dao.selectOne("com.lanqiao.dao.TVisit_logMapper.FindVisitlog_Totalcount", parmlist);
		return nval;
	}

	//创建访谈记录
	public int insertSelective(TVisit_log record) throws Exception {
		TVisit_logMapper visitMapper = dao.getMapper(TVisit_logMapper.class);
		return visitMapper.insertSelective(record);
	}

}
