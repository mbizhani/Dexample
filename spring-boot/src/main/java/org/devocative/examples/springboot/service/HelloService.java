package org.devocative.examples.springboot.service;

import org.devocative.examples.springboot.iservice.IHelloService;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service("hiService")
public class HelloService implements IHelloService {

	@Override
	public String getMessage(String message) {
		String hostname;
		try {
			hostname = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			hostname = "unknown";
		}

		return String.format("%s Spring Boot @ %s", message, hostname);
	}
}
