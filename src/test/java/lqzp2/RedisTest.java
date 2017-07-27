package lqzp2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.lanqiao.service.TestService;

public class RedisTest {


		public static void main(String[] args)throws Exception {

			ApplicationContext context = new FileSystemXmlApplicationContext(
					"classpath:spring-context.xml", "classpath:spring-mybatis.xml");
			
			TestService t = context.getBean(TestService.class);
			long ltime = System.currentTimeMillis();
			t.setRedisValue();
			while(true)
			{
				Object o = t.getRedisValue();
				long dtime = System.currentTimeMillis() - ltime;
				if(o !=null)
				{
					System.out.println (dtime+":"+o.toString());
					
				}else
				{
					System.out.println (dtime+": null");
					break;
				}
				
			}
		}
	}


