package org.devocative.examples.springboot.iservice;

import org.devocative.examples.springboot.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@RepositoryRestResource()
public interface ICustomerRepository extends PagingAndSortingRepository<Customer, Long> {
	List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);
}
