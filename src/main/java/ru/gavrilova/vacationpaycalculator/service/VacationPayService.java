package ru.gavrilova.vacationpaycalculator.service;

import ru.gavrilova.vacationpaycalculator.dto.VacationPayCalculationRequest;
import ru.gavrilova.vacationpaycalculator.dto.VacationPayCalculationResponse;

public interface VacationPayService {

    VacationPayCalculationResponse calculateVacationPay(VacationPayCalculationRequest request);
}
