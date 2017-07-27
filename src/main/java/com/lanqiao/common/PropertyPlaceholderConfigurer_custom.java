package com.lanqiao.common;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;

public class PropertyPlaceholderConfigurer_custom extends
		PropertyPlaceholderConfigurer {

	@Override
	public void setLocations(Resource... locations) {

		String tomcatid = System.getProperty("tomcatid");
		
		if(tomcatid ==null )
			tomcatid = "0";
		int nid = Integer.parseInt(tomcatid);
		
		Resource s = locations[nid];
		System.out.println ("启用配置文件:" + s.getFilename());
		super.setLocation(s);
		
	}
}
