package mag.service;


import lombok.RequiredArgsConstructor;
import mag.mapper.ItemRowMapper;
import mag.model.AddOrderRequest;
import mag.model.AddOrderResponse;
import mag.model.OrderItem;
import mag.model.procedure.AddOrderHeaderProcedure;
import mag.model.procedure.AddOrderItemProcedure;
import mag.model.procedure.ConfirmOrderProcedure;
import mag.model.procedure.SumUpOrderProcedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    @Qualifier("magJdbcTemplate")
    @Autowired
    private final JdbcTemplate magJdbcTemplate;

    @Qualifier("magJdbcTemplateNamedParameter")
    @Autowired
    private final NamedParameterJdbcTemplate magJdbcTemplateNamedParameter;

    private long getNewOrderId(Map<String,Object> outParameters)
    {
        Object resultSet = ((Map)((List)outParameters.get("#result-set-1")).get(0)).get("");
        BigDecimal orderId = (BigDecimal) resultSet;
        return orderId.longValue();
    }

    private long addOrderHeader(AddOrderHeaderProcedure addOrderHeaderProcedure)
    {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(magJdbcTemplate)
                .withProcedureName("RM_DodajZamowienie");

        SqlParameterSource inParameters = new MapSqlParameterSource()
                .addValues(addOrderHeaderProcedure.getProcedureParams());

        simpleJdbcCall.withReturnValue(); //todo check return value

        return getNewOrderId(simpleJdbcCall.execute(inParameters));
    }

    private int addOrderItem(AddOrderItemProcedure addOrderItemProcedure)
    {
        return callProcedure(
                addOrderItemProcedure.getProcedureName(),
                addOrderItemProcedure.getProcedureParams()
        );
    }

    private int sumUpOrder(SumUpOrderProcedure sumUpOrderProcedure)
    {
        return callProcedure(
                sumUpOrderProcedure.getProcedureName(),
                sumUpOrderProcedure.getProcedureParams()
        );
    }

    private int confirmOrder(ConfirmOrderProcedure confirmOrderProcedure)
    {
        return callProcedure(
                confirmOrderProcedure.getProcedureName(),
                confirmOrderProcedure.getProcedureParams()
        );

    }



    private int callProcedure(String procedureName, Map<String,Object> procedureParams)
    {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(magJdbcTemplate)
                .withProcedureName(procedureName);
        SqlParameterSource inParameters = new MapSqlParameterSource()
                .addValues(procedureParams);
        simpleJdbcCall.withReturnValue();
        return (int) simpleJdbcCall.execute(inParameters).get("RETURN_VALUE");
    }

    private long getCurrentDate()
    {
        return ChronoUnit.DAYS.between(LocalDate.of(1800,12,28),LocalDate.now());
    }

    private List<OrderItem> getOrderItemIds(List<String> orderItemsIndexes){

        String sqlQuery = "SELECT ID_ARTYKULU, INDEKS_HANDLOWY FROM ARTYKUL WHERE INDEKS_HANDLOWY IN (:indexes) AND ID_MAGAZYNU=1";
        Map<String,List> paramMap = Collections.singletonMap("indexes", orderItemsIndexes);

        List<OrderItem> orderItemList = magJdbcTemplateNamedParameter.query(sqlQuery, paramMap, new ItemRowMapper());

        return orderItemList;
    }

    private List<String> extractIndexes(List<OrderItem> orderItems){
        return orderItems.stream().map(OrderItem::getIndex).collect(Collectors.toList());
    }

    private OrderItem setItemQuantityAndDesc(OrderItem loopItem, List<OrderItem> orderItems){
        OrderItem singleFound = orderItems.stream()
                .filter(
                        item -> item.getIndex().equals(loopItem.getIndex())
                ).findFirst().orElseThrow();

        loopItem.setQuantity(singleFound.getQuantity());
        loopItem.setDescription(singleFound.getDescription());

        return loopItem;
    }

    public AddOrderResponse addOrder (AddOrderRequest request)
    {
        long newOrderId = addOrderHeader(
                new AddOrderHeaderProcedure(
                        1,
                        7909,
                        1,
                        getCurrentDate(),
                        3000001)
        );

        List<String> indexes = extractIndexes(request.getOrderItems());
        List<OrderItem> foundOrderItemList = getOrderItemIds(indexes);

        for(OrderItem loopItem : foundOrderItemList)
        {
            setItemQuantityAndDesc(loopItem,request.getOrderItems());

            addOrderItem(new AddOrderItemProcedure(
                    newOrderId,
                    loopItem.getId(),
                    "23",
                    loopItem.getQuantity(),
                    loopItem.getDescription()
            ));
        }

        sumUpOrder(new SumUpOrderProcedure(newOrderId));
        confirmOrder(new ConfirmOrderProcedure(
                newOrderId,
                7909,
                1,1,
                getCurrentDate(),
                3000001,
                request.getNotes())
        );

        return AddOrderResponse.builder().status("OK").build();
    }

}
