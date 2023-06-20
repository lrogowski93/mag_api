package mag.service;

import mag.controller.CheckOrderResponse;
import mag.controller.AddOrderRequest;
import mag.controller.AddOrderResponse;
import mag.model.OrderItem;
import mag.model.procedure.*;
import mag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class OrderService {

    private final UserRepository userRepository;
    private final JdbcTemplate magJdbcTemplate;
    private final NamedParameterJdbcTemplate magJdbcTemplateNamedParameter;

    @Value("${mag.cfg.companyid}")
    private int companyId;
    @Value("${mag.cfg.warehouseid}")
    private int warehouseId;
    @Value("${mag.cfg.userid}")
    private int userId;
    @Value("${mag.cfg.employeeid}")
    private int employeeId;

    public OrderService(UserRepository userRepository,
                        @Qualifier("magJdbcTemplate") JdbcTemplate magJdbcTemplate,
                        @Qualifier("magJdbcTemplateNamedParameter") NamedParameterJdbcTemplate magJdbcTemplateNamedParameter) {
        this.userRepository = userRepository;
        this.magJdbcTemplate = magJdbcTemplate;
        this.magJdbcTemplateNamedParameter = magJdbcTemplateNamedParameter;
    }

    private long getNewOrderId(Map<String, Object> outParameters) {
        Object resultSet = ((Map<?, ?>) ((List<?>) outParameters.get("#result-set-1")).get(0)).get("");
        BigDecimal orderId = (BigDecimal) resultSet;
        return orderId.longValue();
    }

    private long addOrderHeader(AddOrderHeaderProcedure addOrderHeaderProcedure) {
        return callProcedure(addOrderHeaderProcedure).getNewOrderId();
    }

    private int addOrderItem(AddOrderItemProcedure addOrderItemProcedure) {
        return callProcedure(addOrderItemProcedure).getReturnValue();
    }

    private int sumUpOrder(SumUpOrderProcedure sumUpOrderProcedure) {
        return callProcedure(sumUpOrderProcedure).getReturnValue();
    }

    private int confirmOrder(ConfirmOrderProcedure confirmOrderProcedure) {
        return callProcedure(confirmOrderProcedure).getReturnValue();
    }

    private int setCatalogPrices(SetCatalogPricesProcedure setCatalogPricesProcedure) {
        return callProcedure(setCatalogPricesProcedure).getReturnValue();
    }

    private ProcedureReturnValue callProcedure(GenericProcedure genericProcedure) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(magJdbcTemplate)
                .withProcedureName(genericProcedure.getProcedureName());
        SqlParameterSource inParameters = new MapSqlParameterSource()
                .addValues(genericProcedure.getProcedureParams());
        simpleJdbcCall.withReturnValue();

        Map<String, Object> outParameters = simpleJdbcCall.execute(inParameters);
        ProcedureReturnValue returnValue = new ProcedureReturnValue((int) outParameters.get("RETURN_VALUE"));

        if (genericProcedure.getClass().equals(AddOrderHeaderProcedure.class)) {
            returnValue.setNewOrderId(getNewOrderId(outParameters));
        }

        return returnValue;
    }

    private long getCurrentDate() {
        return ChronoUnit.DAYS.between(LocalDate.of(1800, 12, 28), LocalDate.now());
    }

    private List<OrderItem> getOrderItemIds(List<String> orderItemsIndexes) {

        String sqlQuery = "SELECT ID_ARTYKULU, INDEKS_HANDLOWY FROM ARTYKUL WHERE INDEKS_HANDLOWY IN (:indexes) AND ID_MAGAZYNU=1";
        Map<String, List> paramMap = Collections.singletonMap("indexes", orderItemsIndexes);

        return magJdbcTemplateNamedParameter.query(sqlQuery, paramMap, (rs, rowNum) ->
                OrderItem.builder()
                        .id(rs.getLong("ID_ARTYKULU"))
                        .index(rs.getString("INDEKS_HANDLOWY"))
                        .build()
        );
    }

    private List<String> extractIndexes(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderItem::getIndex).collect(Collectors.toList());
    }

    private OrderItem setItemQuantityAndDesc(OrderItem loopItem, List<OrderItem> orderItems) {
        OrderItem singleFound = orderItems.stream()
                .filter(
                        item -> item.getIndex().equals(loopItem.getIndex())
                ).findFirst().orElseThrow();

        loopItem.setQuantity(singleFound.getQuantity());
        loopItem.setDescription(singleFound.getDescription());

        return loopItem;
    }

    private long getMagId(String username) {
        return userRepository.findByUsername(username).get().getMagId();
    }

    private int processOrderItems(long magId, long newOrderId, List<OrderItem> foundOrderItemList, List<OrderItem> orderItems) {
        int returnValues = 0;

        for (OrderItem loopItem : foundOrderItemList) {
            setItemQuantityAndDesc(loopItem, orderItems);
            returnValues += addOrderItem(new AddOrderItemProcedure(
                    newOrderId,
                    loopItem.getId(),
                    loopItem.getQuantity(),
                    loopItem.getDescription()
            ));
        }

        returnValues += setCatalogPrices(new SetCatalogPricesProcedure(newOrderId, magId));
        returnValues += sumUpOrder(new SumUpOrderProcedure(newOrderId));

        return returnValues;
    }

    public AddOrderResponse addOrder(AddOrderRequest request, Authentication authentication) {
        long magId = getMagId(authentication.getName());
        long newOrderId = addOrderHeader(
                new AddOrderHeaderProcedure(
                        companyId,
                        magId,
                        warehouseId,
                        getCurrentDate(),
                        userId)
        );

        List<OrderItem> foundOrderItemList = getOrderItemIds(extractIndexes(request.getOrderItems()));

        int returnValues = processOrderItems(magId, newOrderId, foundOrderItemList, request.getOrderItems());

        returnValues += confirmOrder(new ConfirmOrderProcedure(
                newOrderId,
                magId,
                companyId,
                warehouseId,
                getCurrentDate(),
                employeeId,
                request.getNotes())
        );

        return AddOrderResponse.builder()
                .orderId(newOrderId)
                .orderItems(foundOrderItemList)
                .status(returnValues == 0 ? "OK" : "Errors occurred").build();
    }

    private boolean isOrderConfirmed(Long orderId) {
        try {
            String sqlQuery = "SELECT STATUS_ZAM FROM ZAMOWIENIE WHERE ID_ZAMOWIENIA=?";
            String orderStatus = magJdbcTemplate.queryForObject(sqlQuery, String.class, orderId);
            return orderStatus.equals("V");
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    private List<OrderItem> getConfirmedOrderItems(Long orderId) {

        String sqlQuery = "SELECT a.INDEKS_HANDLOWY, p.ZAREZERWOWANO, p.OPIS FROM POZYCJA_ZAMOWIENIA p" +
                " LEFT JOIN ARTYKUL a ON(a.ID_ARTYKULU=p.ID_ARTYKULU) WHERE ID_ZAMOWIENIA=?";

        return magJdbcTemplate.query(sqlQuery, (rs, rowNum) ->
                        OrderItem.builder()
                                .index(rs.getString("INDEKS_HANDLOWY"))
                                .quantity(rs.getInt("ZAREZERWOWANO"))
                                .description(rs.getString("OPIS"))
                                .build(),
                orderId);
    }

    public CheckOrderResponse checkOrder(Long orderId) {
        if (isOrderConfirmed(orderId)) {
            return new CheckOrderResponse(getConfirmedOrderItems(orderId));
        }
        return new CheckOrderResponse();
    }
}