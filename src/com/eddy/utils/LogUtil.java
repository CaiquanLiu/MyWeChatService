package com.eddy.utils;

public class LogUtil {
	private static final boolean LOG_SWITCH = true;

	public static void d(String tag, String content) {
		if (!LOG_SWITCH) {
			return;
		}

		System.out.println("[" + tag + "]" + content);
	}
}
