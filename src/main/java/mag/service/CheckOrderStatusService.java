package mag.service;

import mag.model.OrderItem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckOrderStatusService {

    private final JdbcTemplate magJdbcTemplate;
    private final HelperMethodsService helperMethodsService;

    public CheckOrderStatusService(@Qualifier("magJdbcTemplate") JdbcTemplate magJdbcTemplate, HelperMethodsService helperMethodsService) {
        this.magJdbcTemplate = magJdbcTemplate;
        this.helperMethodsService = helperMethodsService;
    }

    public boolean isOrderConfirmed(long orderId) {
        String orderStatus = helperMethodsService.getOrderStatus(orderId);
        return orderStatus != null && orderStatus.equals("V");
    }

    public List<OrderItem> getConfirmedOrderItems(long orderId) {

        String sqlQuery = "SELECT a.INDEKS_HANDLOWY, p.ZAREZERWOWANO, p.OPIS," +
                " p.CENA_NETTO, p.CENA_BRUTTO, p.KOD_VAT FROM POZYCJA_ZAMOWIENIA p" +
                " LEFT JOIN ARTYKUL a ON(a.ID_ARTYKULU=p.ID_ARTYKULU) WHERE ID_ZAMOWIENIA=?";

        return magJdbcTemplate.query(sqlQuery, (rs, rowNum) ->
                        OrderItem.builder()
                                .index(rs.getString("INDEKS_HANDLOWY"))
                                .quantity(rs.getInt("ZAREZERWOWANO"))
                                .netPrice(rs.getBigDecimal("CENA_NETTO"))
                                .grossPrice(rs.getBigDecimal("CENA_BRUTTO"))
                                .vat(rs.getString("KOD_VAT"))
                                .description(rs.getString("OPIS"))
                                .build(),
                orderId);
    }
}
