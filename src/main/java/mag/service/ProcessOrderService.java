package mag.service;

import mag.model.OrderItem;
import mag.model.procedure.AddOrderItemProcedure;
import mag.model.procedure.SumUpOrderProcedure;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessOrderService {

    private final HelperMethodsService helperMethodsService;
    private final AddOrderService addOrderService;
    private final NamedParameterJdbcTemplate magJdbcTemplateNamedParameter;

    public ProcessOrderService(HelperMethodsService helperMethodsService,
                               AddOrderService addOrderService,
                               @Qualifier("magJdbcTemplateNamedParameter") NamedParameterJdbcTemplate magJdbcTemplateNamedParameter) {
        this.helperMethodsService = helperMethodsService;
        this.addOrderService = addOrderService;
        this.magJdbcTemplateNamedParameter = magJdbcTemplateNamedParameter;
    }

    public List<String> extractIndexes(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItem::getIndex).collect(Collectors.toList());
    }

    public void setItemDetails(OrderItem loopItem, List<OrderItem> orderItems) {
        OrderItem singleFound = orderItems.stream()
                .filter(
                        item -> item.getIndex().equals(loopItem.getIndex())
                ).findFirst().orElseThrow();

        loopItem.setQuantity(singleFound.getQuantity());
        loopItem.setDescription(singleFound.getDescription());
        loopItem.setNetPrice(singleFound.getNetPrice());
        loopItem.setGrossPrice(helperMethodsService.calcGrossPrice(singleFound.getNetPrice(), singleFound.getVat()));
        loopItem.setVat(singleFound.getVat());
    }

    public int processOrderItems(long newOrderId, List<OrderItem> foundOrderItemList, List<OrderItem> orderItems) {
        int returnValues = 0;

        for (OrderItem loopItem : foundOrderItemList) {
            setItemDetails(loopItem, orderItems);
            returnValues += addOrderService.addOrderItem(new AddOrderItemProcedure(newOrderId, loopItem));
        }
        /*
        !!! - use setCatalogPrices method if you don't want to receive prices in request and set default Mag catalog prices
        returnValues += setCatalogPrices(new SetCatalogPricesProcedure(newOrderId, magId));
        */
        returnValues += addOrderService.sumUpOrder(new SumUpOrderProcedure(newOrderId));

        return returnValues;
    }

    public List<OrderItem> getOrderItemIds(List<String> orderItemsIndexes) {

        String sqlQuery = "SELECT ID_ARTYKULU, INDEKS_HANDLOWY FROM ARTYKUL WHERE INDEKS_HANDLOWY IN (:indexes) AND ID_MAGAZYNU=1";
        Map<String, List<String>> paramMap = Collections.singletonMap("indexes", orderItemsIndexes);

        return magJdbcTemplateNamedParameter.query(sqlQuery, paramMap, (rs, rowNum) ->
                OrderItem.builder()
                        .id(rs.getLong("ID_ARTYKULU"))
                        .index(rs.getString("INDEKS_HANDLOWY"))
                        .build()
        );
    }


}
