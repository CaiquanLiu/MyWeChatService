package com.eddy;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

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
		Util.showParams(request);

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
	}
}
