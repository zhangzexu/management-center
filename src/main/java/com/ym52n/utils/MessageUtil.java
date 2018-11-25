package com.ym52n.utils;

public class MessageUtil {

	public static String getMessage(String template, String... keys) {
		int count = 0;
		for (String key : keys) {
			template = template.replace("{" + count++ + "}", key);
		}
		return template;
	}

}
