package org.devocative.examples.springboot.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "myapp")
public class HelloRestController {

	/*
	Map 'myapp.message' from application.properties to following field
	 */
	private String message;

	public void setMessage(String message) {
		this.message = message;
	}

	// ---------------

	@RequestMapping(value = "/hello", produces = "text/plain")
	public String hello() {
		String hostname;
		try {
			hostname = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			hostname = "unknown";
		}

		return String.format("%s Spring Boot @ %s", message, hostname);
	}

}
