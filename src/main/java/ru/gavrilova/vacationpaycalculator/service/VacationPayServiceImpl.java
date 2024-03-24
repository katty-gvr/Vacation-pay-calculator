package ru.gavrilova.vacationpaycalculator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilova.vacationpaycalculator.dto.VacationPayCalculationRequest;
import ru.gavrilova.vacationpaycalculator.dto.VacationPayCalculationResponse;
import ru.gavrilova.vacationpaycalculator.exception.ValidationException;
import ru.gavrilova.vacationpaycalculator.repository.HolidayRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VacationPayServiceImpl implements VacationPayService {

    private final HolidayRepository holidayRepository;
    private static final int MONTHS_IN_YEAR = 12;
    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;

    @Override
    public VacationPayCalculationResponse calculateVacationPay(VacationPayCalculationRequest request) {

        Integer averageDaySalary = calculateAverageDaySalary(request.getAverageYearSalary());
        Integer vacationSalary;

        if (request.getVacationFrom() != null && request.getVacationTo() != null) {

            log.info("Calculating vacation salary with dates {} and {}", request.getVacationFrom(), request.getVacationTo());

            if (ChronoUnit.DAYS.between(request.getVacationFrom(), request.getVacationTo())
                != request.getVacationDaysCount()) {
                throw new ValidationException("Количество дней отпуска должно совпадать " +
                    "с количеством дней между указанными датами!");
            }

            vacationSalary = calculateVacationPayWithDates(averageDaySalary, request.getVacationDaysCount(),
                request.getVacationFrom(), request.getVacationTo());

        } else {
            log.info("Calculating vacation salary without dates");

            vacationSalary = calculateVacationPayWithoutDates(averageDaySalary, request.getVacationDaysCount());
        }
        return new VacationPayCalculationResponse(vacationSalary);
    }

    private Integer calculateVacationPayWithoutDates(Integer averageDaySalary, Integer vacationDaysCount) {
        return averageDaySalary * vacationDaysCount;
    }

    private Integer calculateVacationPayWithDates(Integer averageDaySalary, Integer vacationDaysCount,
                                                 LocalDate fromDate, LocalDate toDate) {

        int numberOfHolidays = holidayRepository.countHolidaysByDateBetween(fromDate, toDate);
        return averageDaySalary * vacationDaysCount - (averageDaySalary * numberOfHolidays);
    }

    private Integer calculateAverageDaySalary(Integer averageYearSalary) {
        double averageDaySalary = averageYearSalary / (MONTHS_IN_YEAR * AVERAGE_DAYS_IN_MONTH);
        return (int) Math.round(averageDaySalary);
    }
}
