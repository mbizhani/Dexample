package org.devocative.examples.springboot.controller;

import org.devocative.examples.springboot.entity.Customer;
import org.devocative.examples.springboot.iservice.ICustomerRepository;
import org.devocative.examples.springboot.iservice.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@ConfigurationProperties(prefix = "backend")
public class HelloRestController {

	@Autowired
	private IHelloService helloService;

	@Autowired
	private ICustomerRepository customerRepository;

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

	@RequestMapping(value = {"/info", "/info/{name}"})
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

	@RequestMapping(value = "/customer/all")
	public List<Customer> list() {
		List<Customer> result = new ArrayList<>();
		customerRepository.findAll().forEach(result::add);
		return result;
	}

	@RequestMapping(value = "/customer/byName/{name}")
	public List<Customer> find(@PathVariable("name") String name) {
		return customerRepository.findByNameContainingIgnoreCase(name);
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
