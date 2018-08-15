package org.devocative.examples.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(
	name = "backend",
	urlPatterns = "/backend")
public class BackendHttpServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String greeting = req.getParameter("greeting");

		System.out.println(">>> greeting = " + greeting);

		Map<String, Object> map = new LinkedHashMap<>();
		map.put("greeting", (greeting != null ? greeting : "Hi!") + " from cluster Backend");
		map.put("time", System.currentTimeMillis());
		map.put("ip", getIp());

		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();

		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(out, map);
	}

	private String getIp() {
		String hostname;
		try {
			hostname = InetAddress.getLocalHost()
				.getHostAddress();
		} catch (UnknownHostException e) {
			hostname = "unknown";
		}
		return hostname;
	}

}
