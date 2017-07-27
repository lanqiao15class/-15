package lqzp2;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.lanqiao.service.ClassMemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml",
		"classpath:spring-mybatis.xml" })
public class MemberclassServiceTest
{
	
	@Resource
	ClassMemberService service;
	
	
	@Test
	@Rollback(false)
	public void test1() throws Exception 
	{
		List l = service.getlqClassMember(151);
		
		String s = JSON.toJSONString(l);
		System.out.println (s);
		
	}
	
		/**
		 * 
		* @Description:发送邮箱验证码
		* @throws Exception 
		* @return void 
		* @author ZhouZhiHua
		* @createTime 2016年8月29日 下午3:57:42
		 */
/*		@Test
		public void tetLogin() throws Exception 
		{
			User u = service.login("3892633@qq.com", "2F0544864369BB5EE3231BD86EEC2764");
			
			if(u !=null)
				System.out.println ("success ............."+ u.getId());
			else
				System.out.println ("not login ...............");
			
		}
	*/
		/*@Test
		public void tetLogin2() throws Exception 
		{
			User u = service.login2("3892633@qq.com", "2F0544864369BB5EE3231BD86EEC2764");
			
			if(u !=null)
				System.out.println ("success ............."+ u.getId());
			else
				System.out.println ("not login ...............");
			
		}*/
		
		
	/*	
		@Test
		public void tetLogin3() throws Exception 
		{
			service.findstdent(new int[]{95});
			
		}
*/
}
