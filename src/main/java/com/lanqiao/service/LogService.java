package com.lanqiao.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.lanqiao.model.SysLog;
import com.lanqiao.util.CommonUtil;

/**
 * 操作日志实现类
 * @author chenbaoji
 *
 */
public class LogService {
	protected BaseDao dao;

	@Resource(name = "baseDao")
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	/**
	 * 保存操作日志。
	 * @param log
	 */
	public void WriteLog(SysLog log) throws Exception {
		if (log.getCreateDate() == null)
			log.setCreateDate(new java.util.Date());
		dao.insert("com.lanqiao.dao.SysLogMapper.insert", log);
	}

	/**
	 * 查询数据
	 * @param map
	 */
	public List<Map<String, Object>> getdata(Map<String, Object> map) {
		return (List<Map<String, Object>>) dao.selectList("com.lanqiao.dao.SysLogMapper.selectlogbyTable", map);
	}

	/**
	 * 查询数据分页
	 * @param map
	 */
	public List<Map<String, Object>> getLogPage(Map<String, Object> map) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) dao.selectList(
				"com.lanqiao.dao.SysLogMapper.getLogPage", map);
		if (list != null) {
			int i = 1;
			for (Map<String, Object> map2 : list) {
				map2.put("indexNo", i);
				if (CommonUtil.isNotNull(map2.get("optype"))) {
					if (StringUtils.equals(map2.get("optype").toString(), "0")) {
						map2.put("optypeName", "增加");
					} else if (StringUtils.equals(map2.get("optype").toString(), "1")) {
						map2.put("optypeName", "修改");
					} else if (StringUtils.equals(map2.get("optype").toString(), "2")) {
						map2.put("optypeName", "删除");
					} else if (StringUtils.equals(map2.get("optype").toString(), "5")) {
						map2.put("optypeName", "异常");
					}
				}

				if (map2.get("real_name") != null && map2.get("real_name") != "") {
					if (StringUtils.equals(map2.get("type").toString(), "1")) {
						map2.put("real_name", map2.get("real_name") + "（老师）");
					} else {
						map2.put("real_name", map2.get("real_name") + "（学生）");
					}

				}
				//格式化时间
				try {
					map2.put("datetime", CommonUtil.dateToDateStr((Date) map2.get("create_date")));

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					map2.put("datetime", "");

				}
				i++;
			}
		}
		return list;
	}

	/**
	 * 查询数据总条数
	 * @param map
	 */
	public int getLogCount(Map<String, Object> map) {
		return (int) dao.selectOne("com.lanqiao.dao.SysLogMapper.getLogCount", map);
	}

	public void WriteLog(String oldData, String content, String optype, int userId, String remoteAddr, String requestUri)
			throws Exception {
		SysLog log = new SysLog();
		log.setContent(content);
		log.setOldData(oldData);
		log.setOptype(optype);
		log.setRemoteAddr(remoteAddr);
		log.setRequestUri(requestUri);
		log.setUserId(userId);
		WriteLog(log);

	}

}
