package com.eddy.turing;

import com.eddy.interfaces.IAIProcess;
import com.eddy.utils.Aes;
import com.eddy.utils.Md5;
import com.eddy.utils.PostServer;

import net.sf.json.JSONObject;

public class Turing implements IAIProcess {
	private final String API_KEY = "fd1659197b02414098fb6d0b277de938";
	private final String REQUEST_KEY = "key";
	private final String REQUEST_INFO = "info";
	private final String SECRET = "57281ae181d15c70";
	private final String TIME_STAMP = "timestamp";
	private final String REQUEST_DATA = "data";

	private final String URL = "http://www.tuling123.com/openapi/api";

	private boolean mIsEncrypt;

	public Turing(boolean isEncrypt) {
		mIsEncrypt = isEncrypt;
	}

	@Override
	public String process(String content) {
		if (mIsEncrypt) {
			return requestWithEncrypt(content);
		} else {
			return requestWithoutEncrypt(content);
		}
	}

	public String requestWithEncrypt(String request) {
		// 图灵网站上的secret
		String secret = SECRET;
		// 图灵网站上的apiKey
		String apiKey = API_KEY;
		String cmd = request;// 测试用例
		// 待加密的json数据
		JSONObject json = new JSONObject();
		json.put(REQUEST_KEY, API_KEY);
		json.put(REQUEST_INFO, request);
		String data = json.toString();
		// 获取时间戳
		String timestamp = String.valueOf(System.currentTimeMillis());

		// 生成密钥
		String keyParam = secret + timestamp + apiKey;
		String key = Md5.MD5(keyParam);

		// 加密
		Aes mc = new Aes(key);
		data = mc.encrypt(data);

		// 封装请求参数
		json = new JSONObject();
		json.put(REQUEST_KEY, apiKey);
		json.put(TIME_STAMP, timestamp);
		json.put(REQUEST_DATA, data);

		String rst = PostServer.SendPost(json.toString(), URL);

		return rst;
	}

	public String requestWithoutEncrypt(String request) {
		JSONObject json = new JSONObject();
		json.put(REQUEST_KEY, API_KEY);
		json.put(REQUEST_INFO, request);

		String rst = PostServer.SendPost(json.toString(), URL);

		return rst;
	}
}
