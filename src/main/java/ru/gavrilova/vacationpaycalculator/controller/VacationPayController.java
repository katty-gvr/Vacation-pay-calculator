package ru.gavrilova.vacationpaycalculator.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilova.vacationpaycalculator.dto.VacationPayCalculationRequest;
import ru.gavrilova.vacationpaycalculator.dto.VacationPayCalculationResponse;
import ru.gavrilova.vacationpaycalculator.service.VacationPayService;

@RestController
@RequiredArgsConstructor
public class VacationPayController {

    private final VacationPayService vacationPayService;

    @GetMapping("/calculate")
    public VacationPayCalculationResponse calculateVacationPay(@RequestBody @Valid VacationPayCalculationRequest request) {
        return vacationPayService.calculateVacationPay(request);
    }
}
