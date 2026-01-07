package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByCustomerIdStartsWithAndAccountTypeIdStartsWith(Long customerId, Long accountTypeId);

}
