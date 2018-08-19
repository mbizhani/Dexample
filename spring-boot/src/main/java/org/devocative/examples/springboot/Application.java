package org.devocative.examples.springboot;

import org.devocative.adroit.date.EUniCalendar;
import org.devocative.adroit.date.UniDate;
import org.devocative.examples.springboot.entity.Customer;
import org.devocative.examples.springboot.iservice.ICustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// ------------------------------

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.info(">>> CommandLineRunner - args = {}", Arrays.toString(args));
			log.info(">>> #BEANS = {}", ctx.getBeanDefinitionCount());

			/*Arrays
				.stream(ctx.getBeanDefinitionNames())
				.sorted()
				.forEach(s -> System.out.println(">>> Bean: " + s));*/
		};
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> log.info("### ApplicationRunner - args = " + args);
	}

	@Bean
	public CommandLineRunner init(ICustomerRepository customerRepository) {
		return args -> {
			UniDate dt = UniDate.of(EUniCalendar.Gregorian, 2018, 1, 1);

			customerRepository.save(new Customer("Ali", dt.toDate()));
			customerRepository.save(new Customer("Joe", dt.setDay(5).toDate()));
			customerRepository.save(new Customer("Bill", dt.setMonth(2).setDay(8).toDate()));
			customerRepository.save(new Customer("Mat", dt.setMonth(1).setDay(28).toDate()));
			customerRepository.save(new Customer("Sara", dt.setMonth(5).setDay(10).toDate()));


			for (Customer customer : customerRepository.findByNameContainingIgnoreCase("a")) {
				log.info("Saved Customer: {}", customer);
			}
		};
	}
}
