package com.eddy.requestprocess;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

public interface IRequestProcess {
	void requestProcess(HashMap<String, String> request, HttpServletResponse response);
}
