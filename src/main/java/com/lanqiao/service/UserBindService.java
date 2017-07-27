package com.lanqiao.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.lanqiao.model.TUserBind;

@Service
public class UserBindService extends BaseService
{

	// 具有所有的id
	public static int All_Data_ID = -1;

	/**
	 * 
	* @Description:验证是否绑定第三方账号
	* @param openid
	* @return 
	* @return Map<String,Object> 
	* @author ZhouZhiHua
	* @createTime 2016年11月25日 下午5:29:46
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> bindOpenidCheck(String openid) {
		List<Map<String, Object>> list=(List<Map<String, Object>>)dao.selectList("com.lanqiao.dao.UserBindMapper.bindOpenidCheck", openid);
		if(list != null && list.size() >0) {
			return list.get(0);
		}
		return null;
	}
	
	
	/**
	 * 
	* @Description:添加绑定记录
	* @param user
	* @param student
	* @throws Exception 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月25日 下午6:09:30
	 */
	public void saveUserBind(TUserBind userBind)throws Exception{
		dao.insert("com.lanqiao.dao.TUserBindMapper.insertSelective", userBind);
	}
	
	
	/**
	 * 
	* @Description:根据用户id查询绑定的项
	* @param userid
	* @return 
	* @return Map<String,Object> 
	* @author ZhouZhiHua
	* @createTime 2016年11月26日 上午10:26:56
	 */
	public List<TUserBind> selectUserBindByUserId(Integer userid) {
		List<TUserBind> list=(List<TUserBind>)dao.selectList("com.lanqiao.dao.UserBindMapper.selectUserBindByUserId", userid);
		return list;
	}
	
	/**
	 * 
	* @Description:解除绑定
	* @param userBind 
	* @return void 
	* @author ZhouZhiHua
	* @createTime 2016年11月26日 下午5:28:06
	 */
	public void deleteByUserIdAndOpenidType(TUserBind userBind){
		dao.selectList("com.lanqiao.dao.UserBindMapper.deleteByUserIdAndOpenidType", userBind);
	}
	
}
