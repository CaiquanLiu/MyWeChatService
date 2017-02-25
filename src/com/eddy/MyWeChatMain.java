package com.eddy;

import java.io.IOException;
import java.util.HashMap;

import javax.crypto.MacSpi;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eddy.wechat.MessageUtil;
import com.eddy.wechat.TextMessage;

/**
 * Servlet implementation class MyWeChatMain
 */
@WebServlet("/MyWeChatMain")
public class MyWeChatMain extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(MyWeChatMain.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyWeChatMain() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("doGet: ");
		Utils.showParams(request);

		// for WeChat developer URL binding
		String echostr = request.getParameter("echostr");
		if (echostr == null) {
			return;
		}
		response.getWriter().write(echostr);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> map = null;
		try {
			map = MessageUtil.parseXML(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (map == null) {
			logger.info("map is null");
		}

		for (String key : map.keySet()) {
			logger.info("key: " + key);
			logger.info(map.get(key));
		}

		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(map.get("FromUserName"));
		textMessage.setFromUserName(map.get("ToUserName"));
		textMessage.setCreateTime(Long.parseLong(map.get("CreateTime")) + 3);
		textMessage.setMsgType("text");
		textMessage.setContent(map.get("Content"));
		String responseXml = MessageUtil.messageToXML(textMessage);
		logger.info("response xml: " + responseXml);

		try {
			// °Ñxml×Ö·û´®Ð´ÈëÏìÓ¦
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
