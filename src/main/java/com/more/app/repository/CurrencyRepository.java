package com.more.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.more.app.entity.Country;
import com.more.app.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
	List<Currency> findByCodeStartsWith(String code);
	List<Currency> findByCodeStartsWithAndCurrencyStartsWith(String code, String currency);
}
