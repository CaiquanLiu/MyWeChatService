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

import com.eddy.requestprocess.IRequestProcess;
import com.eddy.requestprocess.image.ImageProcess;
import com.eddy.requestprocess.link.LinkProcess;
import com.eddy.requestprocess.location.LocationProcess;
import com.eddy.requestprocess.shortvideo.ShortVideoProcess;
import com.eddy.requestprocess.text.TextProcess;
import com.eddy.requestprocess.video.VideoProcess;
import com.eddy.requestprocess.voice.VoiceProcess;
import com.eddy.wechat.MessageUtil;
import com.eddy.wechat.TextMessage;

/**
 * Servlet implementation class MyWeChatMain
 */
@WebServlet("/MyWeChatMain")
public class MyWeChatMain extends HttpServlet {
	// 微信消息定义
	private static final String MSG_TYPE = "MsgType";
	private static final String MSG_TYPE_TEXT = "text";
	private static final String MSG_TYPE_IMAGE = "image";
	private static final String MSG_TYPE_VOICE = "voice";
	private static final String MSG_TYPE_VIDEO = "video";
	private static final String MSG_TYPE_SHORT_VIDEO = "shortvideo";
	private static final String MSG_TYPE_LOCATION = "location";
	private static final String MSG_TYPE_LINK = "link";

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
			response.getWriter().write("this is MyWeChatService!");
		}
		response.getWriter().write(echostr);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// XML解析
		HashMap<String, String> map = null;
		try {
			map = MessageUtil.parseXML(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (map == null) {
			logger.info("map is null");
			return;
		}

		// 打印消息请求内容
		for (String key : map.keySet()) {
			logger.info("key: " + key);
			logger.info(map.get(key));
		}

		// 根据消息类别进行分类处理
		if (map.containsKey(MSG_TYPE)) {
			IRequestProcess requestProcess = null;
			String msgType = map.get(MSG_TYPE);
			if (msgType.equals(MSG_TYPE_TEXT)) {
				requestProcess = new TextProcess();
			} else if (msgType.equals(MSG_TYPE_IMAGE)) {
				requestProcess = new ImageProcess();
			} else if (msgType.equals(MSG_TYPE_VOICE)) {
				requestProcess = new VoiceProcess();
			} else if (msgType.equals(MSG_TYPE_VIDEO)) {
				requestProcess = new VideoProcess();
			} else if (msgType.equals(MSG_TYPE_SHORT_VIDEO)) {
				requestProcess = new ShortVideoProcess();
			} else if (msgType.equals(MSG_TYPE_LOCATION)) {
				requestProcess = new LocationProcess();
			} else if (msgType.equals(MSG_TYPE_LINK)) {
				requestProcess = new LinkProcess();
			}

			if (requestProcess != null) {
				requestProcess.requestProcess(map, response);
			}
		}
	}
}
