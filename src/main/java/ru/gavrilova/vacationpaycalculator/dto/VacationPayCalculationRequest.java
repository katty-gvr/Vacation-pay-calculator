package ru.gavrilova.vacationpaycalculator.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class VacationPayCalculationRequest {

    @NotNull
    @Positive
    Integer averageYearSalary;

    @NotNull
    @Positive
    Integer vacationDaysCount;

    LocalDate vacationFrom;

    LocalDate vacationTo;
}
