package com.eddy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpress {
	public boolean isFormatMatch(String content, String regEx) {
		// 编译正则表达式
		// Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		// 字符串是否与正则表达式相匹配
		return matcher.matches();
	}

	public boolean isContainKeyWord(String content, String regEx) {
		// 编译正则表达式
		// Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		return matcher.find();
	}
}
