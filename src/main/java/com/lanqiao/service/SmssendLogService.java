package com.lanqiao.service;

import org.springframework.stereotype.Service;

import com.lanqiao.model.TSmssendLog;

/**
 * 
* 项目名称:lqzp2
* 类名称： SmssendLogService
* 类描述:短信发送记录
* 创建人: ZhouZhiHua
* 创建时间:2016年11月24日 上午10:44:45 
* 修改人： 
* 修改时间：2016年11月24日 上午10:44:45
* 修改备注:
 */
@Service
public class SmssendLogService extends BaseService {

	// 具有所有的id
	public static int All_Data_ID = -1;

	/**
	 * 
	* @Description:保存
	* @return 
	* @return Map<String,Object> 
	* @author ZhouZhiHua
	* @createTime 2016年11月24日 上午10:52:48
	 */
	public void insert(TSmssendLog smssendLog) throws Exception {
		dao.insert("com.lanqiao.dao.TSmssendLogMapper.insert", smssendLog);
	}
}
