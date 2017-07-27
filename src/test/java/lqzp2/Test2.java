package lqzp2;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lanqiao.service.TestService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml",
		"classpath:spring-mybatis.xml" })
public class Test2 
{
	
	@Resource
	TestService service;
	
	
	@Test
	public void tetLogin() throws Exception 
	{
		service.CacheSet("a", "12343455");
		Object o = service.CacheGet("a");
		
		System.err.println (o.toString());
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
