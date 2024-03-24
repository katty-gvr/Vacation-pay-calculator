import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gavrilova.vacationpaycalculator.dto.VacationPayCalculationRequest;
import ru.gavrilova.vacationpaycalculator.dto.VacationPayCalculationResponse;
import ru.gavrilova.vacationpaycalculator.exception.ValidationException;
import ru.gavrilova.vacationpaycalculator.repository.HolidayRepository;
import ru.gavrilova.vacationpaycalculator.service.VacationPayServiceImpl;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VacationPayServiceImplTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private VacationPayServiceImpl vacationPayService;

    @Test
    void calculateVacationPayWithoutDates() {
        VacationPayCalculationRequest requestWithoutDates = VacationPayCalculationRequest.builder()
            .averageYearSalary(620000)
            .vacationDaysCount(10)
            .build();

        VacationPayCalculationResponse response = vacationPayService.calculateVacationPay(requestWithoutDates);

        assertEquals(17630, response.getVacationPay());
    }

    @Test
    void calculateVacationPayWithDates() {
        LocalDate fromDate = LocalDate.of(2024, 1, 7);
        LocalDate toDate = LocalDate.of(2024, 1, 17);

        VacationPayCalculationRequest requestWithDates = VacationPayCalculationRequest.builder()
            .averageYearSalary(620000)
            .vacationDaysCount(10)
            .vacationFrom(fromDate)
            .vacationTo(toDate)
            .build();

        when(holidayRepository.countHolidaysByDateBetween(fromDate, toDate)).thenReturn(2);

        VacationPayCalculationResponse response = vacationPayService.calculateVacationPay(requestWithDates);

        assertEquals(14104, response.getVacationPay());
    }

    @Test
    void badRequest() {
        LocalDate fromDate = LocalDate.of(2024, 1, 7);
        LocalDate toDate = LocalDate.of(2024, 1, 17);

        VacationPayCalculationRequest requestWithDates = VacationPayCalculationRequest.builder()
            .averageYearSalary(620000)
            .vacationDaysCount(2)
            .vacationFrom(fromDate)
            .vacationTo(toDate)
            .build();

        assertThrows(ValidationException.class, () -> vacationPayService.calculateVacationPay(requestWithDates));
    }
}
