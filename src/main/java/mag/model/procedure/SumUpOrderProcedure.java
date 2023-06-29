package mag.model.procedure;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class SumUpOrderProcedure implements Procedure {

    private final String procedureName = "RM_SumujZamowienie";
    private long orderId;

    @Override
    public Map<String, Object> getProcedureParams() {
        Map<String, Object> paramTranslations = new HashMap<>();
        paramTranslations.put("id_zamowienia", orderId);
        return paramTranslations;
    }
}
