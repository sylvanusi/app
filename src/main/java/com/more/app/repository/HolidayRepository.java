package com.more.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.more.app.entity.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
	List<Holiday> findByTitleStartsWith(String title);
	List<Holiday> findByTitleStartsWithAndHolidayDateStartsWith(String title, LocalDate holidayDate);
}
