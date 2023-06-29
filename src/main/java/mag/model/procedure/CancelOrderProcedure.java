package mag.model.procedure;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CancelOrderProcedure implements Procedure {
    private final String procedureName = "RM_UstawStanZamowien";
    private final int infMode = 0;
    private final String orderStatus = "A";
    private long orderId;
    private long userId;

    public CancelOrderProcedure(long orderId, long userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    @Override
    public Map<String, Object> getProcedureParams() {
        Map<String, Object> paramTranslations = new HashMap<>();

        paramTranslations.put("id_zamowienia", orderId);
        paramTranslations.put("id_sesji", userId);
        paramTranslations.put("tryb_inf_realiz", infMode);
        paramTranslations.put("status_zam", orderStatus);

        return paramTranslations;
    }
}
