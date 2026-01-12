package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	//fullName, customerNo, customerType
	List<Customer> findByFullNameStartsWith(String paramCode);
	List<Customer> findByFullNameStartsWithAndCustomerNoStartsWithAndCustomerTypeStartsWith(String fullName, String customerNo, String customerType);
}
