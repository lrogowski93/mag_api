package mag.model.procedure;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SetCatalogPricesProcedure implements Procedure {
    private final String procedureName = "RM_UstawCenyAktualne";
    private long orderId;
    private long sessionId;
    private int use;
    private long customerId;

    public SetCatalogPricesProcedure(long orderId, long customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
    }

    @Override
    public Map<String, Object> getProcedureParams() {
        Map<String, Object> paramTranslations = new HashMap<>();

        paramTranslations.put("id_zamowienia", orderId);
        paramTranslations.put("id_sesji", sessionId);
        paramTranslations.put("uzycie", use);
        paramTranslations.put("id_kontr", customerId);

        return paramTranslations;
    }
}
