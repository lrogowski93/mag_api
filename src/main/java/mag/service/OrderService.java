package mag.service;

import lombok.RequiredArgsConstructor;
import mag.config.MagConfig;
import mag.controller.request.AddOrderRequest;
import mag.controller.response.AddOrderResponse;
import mag.controller.response.CheckOrderResponse;
import mag.model.OrderItem;
import mag.model.procedure.AddOrderHeaderProcedure;
import mag.model.procedure.ConfirmOrderProcedure;
import mag.model.procedure.GetNumFormatProcedure;
import mag.model.procedure.output.GetNumFormatOutput;
import mag.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final HelperMethodsService helperMethodsService;
    private final AddOrderService addOrderService;
    private final ProcessOrderService processOrderService;
    private final CheckOrderStatusService checkOrderStatusService;
    private final CancelOrderService cancelOrderService;
    private final UserRepository userRepository;
    private final MagConfig magConfig;


    public AddOrderResponse addOrder(AddOrderRequest request, String username) {
        long customerMagId = userRepository.findByUsername(username).get().getMagId();
        long newOrderId = addOrderService.addOrderHeader(
                new AddOrderHeaderProcedure(magConfig, customerMagId, helperMethodsService.getCurrentDate())
        );

        List<OrderItem> foundOrderItemList = processOrderService.getOrderItemIds(processOrderService.extractIndexes(request.getOrderItems()));
        int returnValues = processOrderService.processOrderItems(newOrderId, foundOrderItemList, request.getOrderItems());

        GetNumFormatOutput getNumFormatOutput = addOrderService.getNumFormat(new GetNumFormatProcedure(magConfig));
        returnValues += addOrderService.confirmOrder(new ConfirmOrderProcedure(
                        magConfig,
                        getNumFormatOutput,
                        newOrderId,
                        customerMagId,
                        helperMethodsService.getCurrentDate(),
                        request.getNotes()
                )
        );
        return AddOrderResponse.builder()
                .orderId(newOrderId)
                .orderItems(foundOrderItemList)
                .status(returnValues == 0 ? "OK" : "Errors occurred").build();
    }

    public CheckOrderResponse checkOrder(long orderId, String username) {
        if (checkOrderStatusService.isOrderConfirmed(orderId, username)) {
            return new CheckOrderResponse(checkOrderStatusService.getConfirmedOrderItems(orderId));
        }
        return new CheckOrderResponse();
    }

    public boolean cancelOrder(long orderId, String username) {
        return cancelOrderService.cancelOrder(orderId, magConfig.userId(),username);
    }

}