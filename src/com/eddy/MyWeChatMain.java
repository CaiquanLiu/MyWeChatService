package com.eddy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sun.jndi.toolkit.ctx.StringHeadTail;

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
		logger.info("doPost: ");
		Util.showParams(request);

		logger.info("xml content: ");
		String xmlContent = "";
		// try {
		// xmlContent = Util.getDoc(request);
		// } catch (Exception e) {
		// // TODO: handle exception
		// e.printStackTrace();
		// }

		xmlContent += Util.getPostData(request);
		logger.info(xmlContent);
	}

}
