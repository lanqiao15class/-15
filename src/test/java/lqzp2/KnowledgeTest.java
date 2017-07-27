package lqzp2;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class KnowledgeTest {
	private static final Logger logger = LogManager.getLogger(KnowledgeTest.class);
	
	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			//e.printStackTrace(logger.);
		}  
		
		
		long b = System.currentTimeMillis();
		
		System.out.println(b-a>1000);
		
		
		
	}
	
	
	

}
