package lqzp2;

import java.util.HashMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lanqiao.constant.GlobalConstant;
import com.lanqiao.service.StudentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml",
		"classpath:spring-mybatis.xml" })
public class ExportExcelTest
{
	
	@Resource
	StudentService service;
	
	
	@Test
	@Rollback(false)
	public void test1() throws Exception 
	{
		HashMap paramMap =new HashMap();
		paramMap.put("teaUserId", "242");
		//设置学员状态查询参数
		StringBuffer str=new StringBuffer().append(GlobalConstant.StuStatusEnum.EXPEL.getValue()).append(",");//开除
		str.append(GlobalConstant.StuStatusEnum.QUANTUI.getValue()).append(",");//劝退
		str.append(GlobalConstant.StuStatusEnum.LEAVESCHOLL.getValue()).append(",");//退学
		str.append(GlobalConstant.StuStatusEnum.XIUXUE.getValue()).append(",");//休学
		str.append(GlobalConstant.StuStatusEnum.XIUXUEBack.getValue()).append(",");//休学重返
		str.append(GlobalConstant.StuStatusEnum.DELAYGRADUATE.getValue()).append(",");//延期结业
		str.append(GlobalConstant.StuStatusEnum.DELAYWORK.getValue());//延期就业
		String stuStatusParam=str.toString();
		paramMap.put("stuStatusParam", stuStatusParam);//学员状态
		paramMap.put("auditStatusParam", GlobalConstant.auditStatusEnum.PASS.getValue());//学员审核状态:通过
		
		service.getAllStuExportList(paramMap);
		
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
