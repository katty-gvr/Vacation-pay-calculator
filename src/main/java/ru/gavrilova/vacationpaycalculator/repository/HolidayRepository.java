package ru.gavrilova.vacationpaycalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gavrilova.vacationpaycalculator.model.Holiday;

import java.time.LocalDate;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    int countHolidaysByDateBetween(LocalDate fromDate, LocalDate toDate);
}
