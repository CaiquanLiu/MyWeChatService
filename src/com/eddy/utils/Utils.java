package com.eddy.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eddy.MyWeChatMain;

public class Utils {
	private static Logger logger = Logger.getLogger(MyWeChatMain.class);

	/**
	 * display request parameter from Get/Post
	 * @param request
	 */
	static public void showParams(HttpServletRequest request) {
		Map map = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
		}

		Set<Map.Entry<String, String>> set = map.entrySet();
		// System.out.println("------------------------------");
		logger.info("------------------------------");
		for (Map.Entry entry : set) {
			// System.out.println(entry.getKey() + ":" + entry.getValue());
			logger.info(entry.getKey() + ":" + entry.getValue());
		}
		// System.out.println("------------------------------");
		logger.info("------------------------------");
	}
}
