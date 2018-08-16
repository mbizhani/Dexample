package org.devocative.examples.springboot.controller;

import org.devocative.examples.springboot.common.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "backend")
public class HelloRestController {

	@Autowired
	private IHelloService helloService;

	private RestTemplate template = new RestTemplate();

	/*
	Map 'backend.message' from 'application.properties' to following field
	 */
	private String message;

	/*
	Map 'backend.entry' from 'application.properties' to following field
	 */
	private String entry;

	/*
	Map 'backend.address' from 'application.properties' to following field
	 */
	private String address;

	// ------------------------------

	public void setMessage(String message) {
		this.message = message;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// ---------------

	@RequestMapping(value = {"/info", "/info/{name}"}, produces = "text/plain")
	public String info(@PathVariable(value = "name", required = false) String name) {
		return helloService.getMessage(name != null ? name : message);
	}

	@RequestMapping(value = "/call/{person}", produces = "text/plain")
	public String call(@PathVariable("person") String person) {
		try {
			BackendVO vo = template.getForObject(
					String.format("%s/%s?greeting={p}", address, entry),
					BackendVO.class,
					person);
			return vo.toString();
		} catch (RestClientException e) {
			return "ERROR: " + e.getMessage();
		}
	}

	// ------------------------------

	private static class BackendVO {
		private String greeting;
		private Long time;
		private String ip;

		// ---------------

		public String getGreeting() {
			return greeting;
		}

		public void setGreeting(String greeting) {
			this.greeting = greeting;
		}

		public Long getTime() {
			return time;
		}

		public void setTime(Long time) {
			this.time = time;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		@Override
		public String toString() {
			Date dt = new Date(getTime());
			return String.format("Msg: %s, From: %s, Time: %s", greeting, ip, dt);
		}
	}
}
