package mag.service;

import mag.model.User;
import mag.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HelperMethodsServiceTest {

    @Mock
    private JdbcTemplate magJdbcTemplate;
    @Mock
    private UserRepository userRepository;
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
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(mock(User.class)));
        when(magJdbcTemplate.queryForObject(any(String.class), eq(String.class), eq(123L), anyLong())).thenReturn("A");
        //then
        assertEquals(helperMethodsService.getOrderStatus(123L,"user"), "A");
    }

}