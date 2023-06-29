package mag.service;

import lombok.RequiredArgsConstructor;
import mag.model.procedure.*;
import mag.model.procedure.output.AddOrderHeaderOutput;
import mag.model.procedure.output.GetNumFormatOutput;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddOrderService {

    private final HelperMethodsService helperMethodsService;

    public long addOrderHeader(AddOrderHeaderProcedure addOrderHeaderProcedure) {
        return ((AddOrderHeaderOutput) helperMethodsService.callProcedure(addOrderHeaderProcedure)).getNewOrderId();
    }

    public int addOrderItem(AddOrderItemProcedure addOrderItemProcedure) {
        return helperMethodsService.callProcedure(addOrderItemProcedure).getReturnValue();
    }

    public int sumUpOrder(SumUpOrderProcedure sumUpOrderProcedure) {
        return helperMethodsService.callProcedure(sumUpOrderProcedure).getReturnValue();
    }

    public int confirmOrder(ConfirmOrderProcedure confirmOrderProcedure) {
        return helperMethodsService.callProcedure(confirmOrderProcedure).getReturnValue();
    }

    public GetNumFormatOutput getNumFormat(GetNumFormatProcedure getNumFormatProcedure) {
        return (GetNumFormatOutput) helperMethodsService.callProcedure(getNumFormatProcedure);
    }

/*
    !!! - use setCatalogPrices method if you don't want to receive prices in request and set default Mag catalog prices
    (check ProcessOrderService.processOrderItems)

    public int setCatalogPrices(SetCatalogPricesProcedure setCatalogPricesProcedure) {
        return helperMethodsService.callProcedure(setCatalogPricesProcedure).getReturnValue();
    }
*/


}
