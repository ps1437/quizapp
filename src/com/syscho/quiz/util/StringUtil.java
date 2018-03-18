package com.syscho.quiz.util;

public class StringUtil {

	public static String notNullTrim(String value){
		if(null == value){
			return "";
		}
		return value.trim().toUpperCase();
	}
}
