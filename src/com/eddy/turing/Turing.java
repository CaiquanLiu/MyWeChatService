package com.eddy.turing;

import com.eddy.utils.APIHttpClient;

import net.sf.json.JSONObject;

/**
 * Json解析参考：http://zhangfan822.iteye.com/blog/1880830
 * 
 * @author Administrator
 *
 */
public class Turing {
	private final String REQUEST_KEY = "key";
	private final String REQUEST_KEY_VALUE = "APIKey";
	private final String REQUEST_INFO = "info";

	private final String URL = "http://www.tuling123.com/openapi/api";
	private APIHttpClient mApiHttpClient = new APIHttpClient(URL);

	public String request(String request) {
		JSONObject json = new JSONObject();
		json.put(REQUEST_KEY, REQUEST_KEY_VALUE);
		json.put(REQUEST_INFO, request);

		String rst = mApiHttpClient.post(json.toString());

		return rst;
		// return request;
	}
}
