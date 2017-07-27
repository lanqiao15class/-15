package com.lanqiao.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;


public class DESEDEEncryptor extends Coder {

	// ///////////////////////////////////////////////////////////////////////////////////////////

	public static final String ALGORITHM = "DES";

	public static final String DesKey = "QCbf6oDmWIU=";

	private static Key toKey(byte[] key) throws Exception {

		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey secretKey = keyFactory.generateSecret(dks);

		// 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
		// SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);

		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {

		Key k = toKey(decryptBASE64(key));

		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);

		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {

		Key k = toKey(decryptBASE64(key));
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);

		return cipher.doFinal(data);
	}

	/**
	 * 生成密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initKey() throws Exception {

		return initKey(null);
	}

	/**
	 * 生成密钥
	 * 
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initKey(String seed) throws Exception {

		SecureRandom secureRandom = null;

		if (seed != null) {
			secureRandom = new SecureRandom(decryptBASE64(seed));
		} else {
			secureRandom = new SecureRandom();
		}

		KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		return encryptBASE64(secretKey.getEncoded());
	}

	public static String base64En(byte[] key) {

		return Base64.encode(key);
	}

	public static byte[] base64De(String key) {

		return Base64.decode(key);
	}

	/**
	 * 获取加密数据
	 * 
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public static String desEnString(String paras) throws Exception {

		byte[] inputData = paras.getBytes("utf-8");
		inputData = encrypt(inputData, DesKey);
		String s = base64En(inputData);

		return DESEDEEncryptor.hexEncode(s);
	}

	/**
	 * 获取解密数据
	 * 
	 * @param paras
	 * @return
	 * @throws Exception
	 */
	public static String desDeString(String paras) throws Exception {

		String newS = DESEDEEncryptor.hexDecode(paras);
		byte[] outputData = DESEDEEncryptor.decrypt(DESEDEEncryptor.base64De(newS), DesKey);
		return new String(outputData, "utf-8");
	}

	public static String toHexString(String s) {

		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	private static String hexString = "0123456789ABCDEF";

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String hexEncode(String str) {

		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String hexDecode(String bytes) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes
					.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	
	public static void main(String[] args)throws Exception  {
		
		String src="666666666666666666666666666666666";
		String msrc = desEnString(src);
		System.out.println ("加密后("+msrc.length()+"):"+msrc );
		
		String plainsrc = desDeString(msrc);
		System.out.println ("解密后("+plainsrc.length()+"):"+plainsrc);
		
		
		
	}
}
