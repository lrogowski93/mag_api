package mag.service;

import mag.config.MagConfig;
import mag.controller.request.AddOrderRequest;
import mag.controller.response.AddOrderResponse;
import mag.model.OrderItem;
import mag.model.User;
import mag.model.procedure.AddOrderHeaderProcedure;
import mag.model.procedure.ConfirmOrderProcedure;
import mag.model.procedure.GetNumFormatProcedure;
import mag.model.procedure.output.GetNumFormatOutput;
import mag.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private MagConfig magConfig;
    @Mock
    private Authentication authentication;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HelperMethodsService helperMethodsService;
    @Mock
    private AddOrderService addOrderService;
    @Mock
    private ProcessOrderService processOrderService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldAddOrder() {
        //given
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(mock(User.class)));
        when(addOrderService.addOrderHeader(any(AddOrderHeaderProcedure.class))).thenReturn(12345L);
        when(processOrderService.getOrderItemIds(anyList())).thenReturn(List.of(
                OrderItem.builder().id(111L).index("K100").build(),
                OrderItem.builder().id(222L).index("A20").build(),
                OrderItem.builder().id(333L).index("F120").build(),
                OrderItem.builder().id(444L).index("B6").build()
        ));
        when(addOrderService.getNumFormat(any(GetNumFormatProcedure.class))).thenReturn(mock(GetNumFormatOutput.class));
        when(addOrderService.confirmOrder(any(ConfirmOrderProcedure.class))).thenReturn(0);
        //when
        AddOrderResponse result = orderService.addOrder(mock(AddOrderRequest.class), "username");
        //then
        assertEquals(12345L, result.getOrderId());
        assertEquals("OK", result.getStatus());
        assertEquals(111L, result.getOrderItems().get(0).getId());
        assertEquals(444L, result.getOrderItems().get(3).getId());
        assertEquals("F120", result.getOrderItems().get(2).getIndex());
    }
}