package mag.service;


import lombok.RequiredArgsConstructor;
import mag.model.OrderHeader;
import mag.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    @Qualifier("magJdbcTemplate")
    private final JdbcTemplate magJdbcTemplate;


    private long getNewOrderId(Map<String,Object> outParameters)
    {
        Object test = ((Map)((List)outParameters.get("#result-set-1")).get(0)).get("");
        BigDecimal orderId = (BigDecimal) test;
        return orderId.longValue();
    }

    public long addOrderHeader(OrderHeader orderHeader)
    {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(magJdbcTemplate)
                .withProcedureName("RM_DodajZamowienie");

        SqlParameterSource inParameters = new MapSqlParameterSource()
                .addValues(orderHeader.getProcedureParams());

        simpleJdbcCall.withReturnValue(); //todo sprawdz zwracana wartosc

        return getNewOrderId(simpleJdbcCall.execute(inParameters));
    }

    public int addOrderItem(OrderItem orderItem)
    {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(magJdbcTemplate)
                .withProcedureName("RM_DodajPozycjeZamowienia");

        SqlParameterSource inParameters = new MapSqlParameterSource()
                .addValues(orderItem.getProcedureParams());

        simpleJdbcCall.withReturnValue();

        return (int) simpleJdbcCall.execute(inParameters).get("RETURN_VALUE");
    }


}
