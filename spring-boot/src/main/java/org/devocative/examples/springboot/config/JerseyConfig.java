package org.devocative.examples.springboot.config;

import org.devocative.examples.springboot.service.PortfolioService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("portfolios")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(PortfolioService.class);
	}

}
