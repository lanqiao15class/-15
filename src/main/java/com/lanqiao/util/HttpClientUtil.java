package com.lanqiao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author zjyou
 * @date 2016年7月14日 下午12:12:38
 * @desc HTTP请求工具类
 */
public class HttpClientUtil {

	/**
	 * HTTP GET请求
	 * @param baseUrl 请求基本路径，即不带参数的路径
	 * @param paramList 参数集合
	 */
	public static void getRequest(String baseUrl, Map<String, Object> paramMap) throws Exception {
		//创建httpclient实例，用于发送请求
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(baseUrl);
		//封装参数列表
		if (paramMap != null && paramMap.size() > 0) {
			URIBuilder builder = new URIBuilder().setPath(baseUrl);
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			NameValuePair nameValuePair = null;
			Iterator ite = paramMap.entrySet().iterator();
			while (ite.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
				nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				paramList.add(nameValuePair);
			}
			//追加参数
			builder.addParameters(paramList);
			//设置GET请求URL
			httpGet.setURI(builder.build());
			//执行请求返回结果集
			CloseableHttpResponse response = client.execute(httpGet);
			// 服务器返回码
			int status_code = response.getStatusLine().getStatusCode();
			System.out.println(status_code);
			// 服务器返回内容
			String respStr = null;
			HttpEntity responseEntity = response.getEntity();
			if (responseEntity != null) {
				respStr = EntityUtils.toString(responseEntity, "UTF-8");
			}
			System.out.println("respStr = " + respStr);
			// 释放资源
			EntityUtils.consume(responseEntity);
		}
	}

	/**
	 * HTTP POST请求
	 * @param baseUrl 请求基本路径，即不带参数的路径
	 * @param paramList 参数集合
	 */
	public static void postRequest(String baseUrl, Map<String, Object> paramMap) {
		//创建httpclient实例，用于发送请求
		CloseableHttpClient client = HttpClients.createDefault();
		//创建post请求
		HttpPost httpPost = new HttpPost(baseUrl);
		try {
			//封装参数列表
			if (paramMap != null && paramMap.size() > 0) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				NameValuePair nameValuePair = null;
				Iterator ite = paramMap.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
					nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
					paramList.add(nameValuePair);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
				httpPost.setEntity(entity);
				//执行请求返回结果集
				CloseableHttpResponse response = client.execute(httpPost);
				// 服务器返回码
				int status_code = response.getStatusLine().getStatusCode();
				System.out.println(status_code);
				// 服务器返回内容
				String respStr = null;
				HttpEntity responseEntity = response.getEntity();
				if (responseEntity != null) {
					respStr = EntityUtils.toString(responseEntity, "UTF-8");
				}
				System.out.println("respStr = " + respStr);
				// 释放资源
				EntityUtils.consume(responseEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * HTTP POST请求
	 * @param baseUrl 请求基本路径，即不带参数的路径
	 * @param paramList 参数集合
	 */
	public static String postRequestResult(String baseUrl, Map<String, Object> paramMap) {
		//创建httpclient实例，用于发送请求
		CloseableHttpClient client = HttpClients.createDefault();
		// 服务器返回内容
		String respStr = null;
		//创建post请求
		HttpPost httpPost = new HttpPost(baseUrl);
		try {
			//封装参数列表
			if (paramMap != null && paramMap.size() > 0) {
				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
				NameValuePair nameValuePair = null;
				Iterator ite = paramMap.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry<String, String> entry = (Map.Entry<String, String>) ite.next();
					nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
					paramList.add(nameValuePair);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "UTF-8");
				httpPost.setEntity(entity);
				//执行请求返回结果集
				CloseableHttpResponse response = client.execute(httpPost);
				// 服务器返回码
				int status_code = response.getStatusLine().getStatusCode();
				System.out.println(status_code);
				if(status_code ==200)
				{
					HttpEntity responseEntity = response.getEntity();
					if (responseEntity != null) {
						respStr = EntityUtils.toString(responseEntity, "UTF-8");
					}
					EntityUtils.consume(responseEntity);
				}
				System.out.println("respStr = " + respStr);
				// 释放资源
				
			}
		} catch (Exception e) {
			respStr = null;
			e.printStackTrace();
		} finally {
			httpPost.releaseConnection();//关闭连接
		}

		return respStr;
	}

	public static void main(String[] args) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String baseUrl = "http://10.100.40.103/resources/public/resource/?c=api&m=list";
		//?c=api&m=list （需要stu_id 学生id）
		/*paramMap.put("c", "api");
		paramMap.put("m", "list");*/
		paramMap.put("stu_id", "28594");
		String str = HttpClientUtil.postRequestResult(baseUrl, paramMap);
		System.out.println(str);
	}
}