package mag.service;

import mag.model.OrderItem;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckOrderStatusService {

    private final JdbcTemplate magJdbcTemplate;

    public CheckOrderStatusService(@Qualifier("magJdbcTemplate") JdbcTemplate magJdbcTemplate) {
        this.magJdbcTemplate = magJdbcTemplate;
    }

    public boolean isOrderConfirmed(Long orderId) {
        try {
            String sqlQuery = "SELECT STATUS_ZAM FROM ZAMOWIENIE WHERE ID_ZAMOWIENIA=?";
            String orderStatus = magJdbcTemplate.queryForObject(sqlQuery, String.class, orderId);
            return orderStatus.equals("V");
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }

    public List<OrderItem> getConfirmedOrderItems(Long orderId) {

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
