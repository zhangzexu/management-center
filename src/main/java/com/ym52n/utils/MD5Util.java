package com.ym52n.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.MessageDigest;


public class MD5Util {

	public static String encrypt(String dataStr) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(dataStr.getBytes("UTF8"));
			byte s[] = m.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00)
						.substring(6);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
	public static String encrypt(String salt,String passowrd){
		String hashAlgorithmName = "MD5";
		int hashIterations = 2;
		Object result = new SimpleHash(hashAlgorithmName, passowrd, salt, hashIterations);
		String password = result.toString();
		return password;
	}

}
