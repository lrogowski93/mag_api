package mag.service;

import mag.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckOrderStatusServiceTest {

    @Mock
    private JdbcTemplate magJdbcTemplate;
    @Mock
    private HelperMethodsService helperMethodsService;
    @InjectMocks
    private CheckOrderStatusService checkOrderStatusService;

    @Test
    void shouldOrderBeConfirmed() {
        //given
        when(helperMethodsService.getOrderStatus(123L,"user")).thenReturn("V");
        //then
        assertTrue(checkOrderStatusService.isOrderConfirmed(123L,"user"));
    }

    @Test
    void shouldOrderBeNotConfirmed() {
        //given
        when(helperMethodsService.getOrderStatus(123L,"user")).thenReturn("");
        //then
        assertFalse(checkOrderStatusService.isOrderConfirmed(123L,"user"));
    }

    @Test
    void shouldGetConfirmedOrderItems() {
        //given
        List<OrderItem> orderItemList = List.of(
                OrderItem.builder().index("A1").quantity(123).netPrice(new BigDecimal(34.5)).vat("23").build(),
                OrderItem.builder().index("B4").quantity(300).netPrice(new BigDecimal(12.3)).vat("23").build()
        );
        when(magJdbcTemplate.query(anyString(), any(RowMapper.class), eq(123L))).thenReturn(orderItemList);
        //then
        assertEquals(orderItemList, checkOrderStatusService.getConfirmedOrderItems(123L));
    }
}