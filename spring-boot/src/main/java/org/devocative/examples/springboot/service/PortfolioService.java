package org.devocative.examples.springboot.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/")
public class PortfolioService implements InitializingBean {
	private Object[][][][] clientPortfolios;

	@GET
	@Path("customer/{customer-id}")
	@Produces(MediaType.APPLICATION_JSON)
	// a portfolio consists of an array of arrays, each containing an array of
	// stock ticker and associated shares
	public Object[][][] getPortfolios(@PathParam("customer-id") int customerId) {
		return clientPortfolios[customerId];
	}

	@GET
	@Path("customer/{customer-id}/portfolio/{portfolio-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object[][] getPortfolio(@PathParam("customer-id") int customerId,
								   @PathParam("portfolio-id") int portfolioId) {
		return getPortfolios(customerId)[portfolioId];
	}

	@Override
	public void afterPropertiesSet() {
		this.clientPortfolios = new Object[][][][]{
			{
				// 3 customers, 3 portfolios each
				{new Object[]{"JPM", 10201}, new Object[]{"GE", 20400}, new Object[]{"UTX", 38892}},
				{new Object[]{"KO", 12449}, new Object[]{"JPM", 23454}, new Object[]{"MRK", 45344}},
				{new Object[]{"WMT", 39583}, new Object[]{"DIS", 95867}, new Object[]{"TRV", 384756}},
			},
			{
				{new Object[]{"GE", 38475}, new Object[]{"MCD", 12395}, new Object[]{"IBM", 91234}},
				{new Object[]{"VZ", 22342}, new Object[]{"AXP", 385432}, new Object[]{"UTX", 23432}},
				{new Object[]{"IBM", 18343}, new Object[]{"DIS", 45673}, new Object[]{"AAPL", 23456}},
			},
			{
				{new Object[]{"AXP", 34543}, new Object[]{"TRV", 55322}, new Object[]{"NKE", 45642}},
				{new Object[]{"CVX", 44332}, new Object[]{"JPM", 12453}, new Object[]{"JNJ", 45433}},
				{new Object[]{"MRK", 32346}, new Object[]{"UTX", 46532}, new Object[]{"TRV", 45663}},
			}
		};
	}
}
