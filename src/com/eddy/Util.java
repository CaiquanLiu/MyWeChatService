package com.eddy;

import java.io.BufferedReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class Util {
	private static Logger logger = Logger.getLogger(MyWeChatMain.class);

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

	static public String getDoc(HttpServletRequest request) throws Exception {
		char[] readerBuffer = new char[request.getContentLength()];
		BufferedReader bufferedReader = request.getReader();

		// Logger.info("开始处理上传数据");
		int portion = bufferedReader.read(readerBuffer);
		int amount = portion;
		while (amount < readerBuffer.length) {
			portion = bufferedReader.read(readerBuffer, amount, readerBuffer.length - amount);
			amount = amount + portion;
		}

		StringBuffer stringBuffer = new StringBuffer((int) (readerBuffer.length * 1.5));
		for (int index = 0; index < readerBuffer.length; index++) {
			char c = readerBuffer[index];
			stringBuffer.append(c);
		}

		String xml = new String(stringBuffer.toString().getBytes(), "utf-8");
		// logger.info(xml);
		return xml;
	}

	static public String getPostData(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				sb.append(line);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
