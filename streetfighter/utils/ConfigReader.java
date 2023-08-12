package com.streetfighter.utils;

import java.util.ResourceBundle;

public class ConfigReader {
	private static ResourceBundle rb = ResourceBundle.getBundle("com/streetfighter/utils/config");
	public static String getValue(String key) {
		return rb.getString(key);
	}
}
