package org.devocative.examples.springboot;

import org.devocative.examples.springboot.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Hop;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate trt;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testInfo() {
		String info = trt.getForObject("/api/info", String.class);
		System.out.println("info = " + info);
		assertTrue(info.startsWith("HI!!! Spring Boot @"));

		info = trt.getForObject("/api/info/Joe", String.class);
		System.out.println("info = " + info);
		assertTrue(info.startsWith("Joe Spring Boot @"));
	}

	@Test
	public void testInitialCustomersRest() {
		Customer[] customers = trt.getForObject("/api/customer/all", Customer[].class);
		assertEquals(5, customers.length);
	}

	@Test
	public void testInitialCustomersCallHAL() throws URISyntaxException {
		System.out.println("### port = " + port);

		Traverson trv = new Traverson(new URI("http://localhost:" + port), MediaTypes.HAL_JSON);
		Resources<Customer> customers = trv
			.follow(Hop.rel("customers"))
			.toObject(new ParameterizedTypeReference<Resources<Customer>>() {
			});
		assertEquals(5, customers.getContent().size());
	}

}
