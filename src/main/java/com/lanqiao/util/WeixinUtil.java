package com.lanqiao.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * 微信工具类
 * @author chenbaoji
 *
 */
public class WeixinUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	/**
	 *  微信点击菜单后， 进入我们的页面会携带code 参数， 
	 *  根据code 可以调用接口得到openid . 
	 *  
	 *  微信菜单设置
	 *  https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxaef86c44bd98a195&redirect_uri=http%3a%2f%2fstatic.lanqiao.cn%2ft.jsp&response_type=code&scope=snsapi_base&state=123#wechat_redirect
	 *  
	 * @param code
	 * @param appid
	 * @param secret
	 * @return
	 */
	public static JSONObject getOpenidByCode(String code, String appid,
			String secret) {
		Object authorize = null;
		try {
			HttpClient hc = new HttpClient();
			hc.getHttpConnectionManager().getParams()
					.setConnectionTimeout(10000);
			hc.getHttpConnectionManager().getParams().setSoTimeout(10000);

			String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
			PostMethod post = new PostMethod(url);
			// post.set
			post.addParameter("appid", appid);
			post.addParameter("secret", secret);
			post.addParameter("code", code);
			post.addParameter("grant_type", "authorization_code");
			int ncode = hc.executeMethod(post);
			//System.out.println("code=" + ncode);
			if(ncode==200)
			{
				String s = post.getResponseBodyAsString();
				authorize = JSON.parse(s);
			//System.out.println(authorize);
			}
			post.releaseConnection();

		} catch (Exception e) {

		}
		return (JSONObject) authorize;
	}

}
