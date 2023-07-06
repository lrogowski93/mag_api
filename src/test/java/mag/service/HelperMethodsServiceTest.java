package mag.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelperMethodsServiceTest {

    @Mock
    private JdbcTemplate magJdbcTemplate;
    @InjectMocks
    private HelperMethodsService helperMethodsService;

    @Test
    void shouldGetCurrentDate() {
        //given
        long date = ChronoUnit.DAYS.between(LocalDate.of(1800, 12, 28), LocalDate.now());
        //then
        assertEquals(date, helperMethodsService.getCurrentDate());
    }

    @Test
    void shouldGetOrderStatus() {
        //given
        when(magJdbcTemplate.queryForObject(any(String.class), eq(String.class), eq(123L))).thenReturn("A");
        //then
        assertEquals(helperMethodsService.getOrderStatus(123L), "A");
    }

}