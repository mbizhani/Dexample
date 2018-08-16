package org.devocative.examples.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Path("/api")
public class HelloRestController {
	private String message = "Hi!";

	@Path("/info")
	@GET
	public String info() {
		String hostname;
		try {
			hostname = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			hostname = "unknown";
		}

		return String.format("%s DropWizard @ %s", message, hostname);
	}
}
