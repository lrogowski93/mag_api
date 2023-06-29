package mag.service;

import mag.model.procedure.CancelOrderProcedure;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CancelOrderService {

    private final HelperMethodsService helperMethodsService;

    private final JdbcTemplate magJdbcTemplate;

    public CancelOrderService(HelperMethodsService helperMethodsService,
                              @Qualifier("magJdbcTemplate") JdbcTemplate magJdbcTemplate) {
        this.helperMethodsService = helperMethodsService;
        this.magJdbcTemplate = magJdbcTemplate;
    }

    private boolean isOrderCanceled(Long orderId) {
        try {
            String sqlQuery = "SELECT STATUS_ZAM FROM ZAMOWIENIE WHERE ID_ZAMOWIENIA=?";
            String orderStatus = magJdbcTemplate.queryForObject(sqlQuery, String.class, orderId);
            return orderStatus.equals("A");
        } catch (EmptyResultDataAccessException ex) {
            return true;
        }
    }

    public boolean cancelOrder(long orderId, long userId) {
        if (isOrderCanceled(orderId)) {
            return false;
        }
        helperMethodsService.callProcedure(new CancelOrderProcedure(orderId, userId));
        return true;
    }

}
