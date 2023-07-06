package mag.service;

import mag.model.procedure.CancelOrderProcedure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelOrderServiceTest {
    @Mock
    private HelperMethodsService helperMethodsService;
    @InjectMocks
    private CancelOrderService cancelOrderService;

    @Test
    void shouldCancelOrder() {
        //given
        when(helperMethodsService.getOrderStatus(123L)).thenReturn("");
        //then
        assertTrue(cancelOrderService.cancelOrder(123L, 1L));
        verify(helperMethodsService).callProcedure(any(CancelOrderProcedure.class));
    }

    @Test
    void shouldNotCancelOrder() {
        //given
        when(helperMethodsService.getOrderStatus(123L)).thenReturn("V");
        //then
        assertFalse(cancelOrderService.cancelOrder(123L, 1L));
        verify(helperMethodsService, never()).callProcedure(any(CancelOrderProcedure.class));
    }
}