package mag.service;

import mag.model.procedure.CancelOrderProcedure;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderService {

    private final HelperMethodsService helperMethodsService;

    public CancelOrderService(HelperMethodsService helperMethodsService) {
        this.helperMethodsService = helperMethodsService;
    }

    private boolean canOrderBeCancelled(long orderId) {
        String orderStatus = helperMethodsService.getOrderStatus(orderId);
        return orderStatus != null && (orderStatus.equals(" ") || orderStatus.equals(""));
    }

    public boolean cancelOrder(long orderId, long userId) {
        if (canOrderBeCancelled(orderId)) {
            helperMethodsService.callProcedure(new CancelOrderProcedure(orderId, userId));
            return true;
        }
        return false;
    }

}
