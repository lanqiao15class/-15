package com.lanqiao.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * TODO 可转化为String的Object对象空值判断
 * 
 * @author xinglt
 * @date 2014年8月7日 上午11:28:05
 *
 */
public class NullUtil {

	/**
	 * 判断是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {

		if (null != obj && null != obj.toString()
				&& obj.toString().length() > 0 && null != obj.toString()
				&& obj.toString().trim().length() > 0
				&& StringUtils.isNotBlank(obj.toString())
				&& StringUtils.isNotEmpty(obj.toString())) {

			return false;
		}

		return true;
	}

	/**
	 * 判断是否不为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {

		if (null != obj && null != obj.toString()
				&& obj.toString().length() > 0 && null != obj.toString()
				&& obj.toString().trim().length() > 0
				&& StringUtils.isNotBlank(obj.toString())
				&& StringUtils.isNotEmpty(obj.toString())) {

			return true;
		}

		return false;
	}

	/**
	 * 判断是否不为空
	 * 
	 * @param <T>
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> boolean isNotNull(Map<Long, T> objMap, Long objKey) {

		if (null != objMap && objMap.size() > 0 && null != objKey && objKey > 0
				&& null != objMap.get(objKey)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断不为空并返回String类型值
	 * 
	 * @param obj
	 * @return
	 */
	public static String isNotNullResultStringValue(Object obj) {

		if (null != obj && null != obj.toString()
				&& obj.toString().length() > 0 && null != obj.toString().trim()
				&& obj.toString().trim().length() > 0
				&& StringUtils.isNotBlank(obj.toString())
				&& StringUtils.isNotEmpty(obj.toString())) {

			return obj.toString().trim();
		}

		return null;
	}

	/**
	 * 判断不为空并返回值
	 * 
	 * @param obj
	 * @return
	 */
	public static long isNotNullResultLongValue(Object obj) {

		if (null != obj && null != obj.toString()
				&& null != obj.toString().trim()
				&& StringUtils.isNotBlank(obj.toString())
				&& StringUtils.isNotEmpty(obj.toString())
				&& Long.parseLong(obj.toString().trim()) > 0) {

			return Long.parseLong(obj.toString().trim());
		}

		return 0;
	}

	/**
	 * 判断不为空并返回值
	 * 
	 * @param obj
	 * @return
	 */
	public static int isNotNullResultIntegerValue(Object obj) {

		if (null != obj && null != obj.toString()
				&& null != obj.toString().trim()
				&& StringUtils.isNotBlank(obj.toString())
				&& StringUtils.isNotEmpty(obj.toString())
				&& Long.parseLong(obj.toString().trim()) > 0) {

			return Integer.parseInt(obj.toString().trim());
		}

		return 0;
	}

	/**
	 * 加密字符串中间字符串
	 * 
	 * @return
	 */
	public static String isNotNullResultStringValueByEncrypt(Object obj) {

		if (isNotNull(obj) && isNotNull(isNotNullResultStringValue(obj))) {
			String temp = isNotNullResultStringValue(obj).substring(0, 2);
			for (int i = 0; i < isNotNullResultStringValue(obj).length() - 3; i++) {
				temp += "*";
			}
			temp += isNotNullResultStringValue(obj).substring(
					isNotNullResultStringValue(obj).length() - 3,
					isNotNullResultStringValue(obj).length());
			return temp;
		}

		return null;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	public static String byteToString(byte[] byteArray) {

		String strDigest = "";

		for (int i = 0; i < byteArray.length; i++) {

			strDigest += byteToHexString(byteArray[i]);
		}

		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexString(byte mByte) {

		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };

		char[] tempArr = new char[2];

		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];

		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);

		return s;
	}

	/**
	 * 字符串转码
	 * 
	 * @param str
	 * @return
	 */
	public static String urlEnodeUTF8(String str) {

		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {

		long t = isNotNullResultLongValue("");

		System.out.println(t);
	}
}
