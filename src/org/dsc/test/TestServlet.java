package org.dsc.test;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {
	
	private static final String PARAM = "encoded-request";
	private static final String ACTION = "af-command";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doPost(req, resp);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String encoded_request = req.getParameter(PARAM);
		String decoded_request = decodeRequest(encoded_request);
		Map<String,String> parsed_request = parseRequest(decoded_request);
		
		String action = parsed_request.get(ACTION);
		
		
		resp.setContentType("text/json");
		resp.getWriter().println("Hello, world");
	}
}
