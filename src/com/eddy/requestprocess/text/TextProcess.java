package com.eddy.requestprocess.text;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.eddy.requestprocess.IRequestProcess;
import com.eddy.wechat.MessageUtil;
import com.eddy.wechat.TextMessage;

public class TextProcess implements IRequestProcess {

	@Override
	public void requestProcess(HashMap<String, String> request, HttpServletResponse response) {
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(request.get("FromUserName"));
		textMessage.setFromUserName(request.get("ToUserName"));
		textMessage.setCreateTime(Long.parseLong(request.get("CreateTime")) + 3);
		textMessage.setMsgType("text");
		textMessage.setContent(request.get("Content"));
		String responseXml = MessageUtil.messageToXML(textMessage);

		try {
			byte[] xmlData = responseXml.getBytes();

			response.setContentLength(xmlData.length);

			ServletOutputStream os = response.getOutputStream();
			os.write(xmlData);

			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
