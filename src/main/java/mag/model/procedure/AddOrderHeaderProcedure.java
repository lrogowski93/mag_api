package mag.model.procedure;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class AddOrderHeaderProcedure {

    private final String procedureName = "RM_DodajZamowienie";
    private int companyId;
    private int customerId;
    private int warehouseId;
    private int type;
    private int date;
    private int userId;
    private int registrationMode;
    private String valueType;

    public Map<String, Object> getProcedureParams(){
        Map<String,Object> paramTranslations = new HashMap<>();

        paramTranslations.put("id_firmy",companyId);
        paramTranslations.put("id_kontrahenta",customerId);
        paramTranslations.put("id_magazynu",warehouseId);
        paramTranslations.put("typ",type);
        paramTranslations.put("data",date);
        paramTranslations.put("semafor",userId);
        paramTranslations.put("trybrejestracji",registrationMode);
        paramTranslations.put("brutto_netto",valueType);

        return paramTranslations;
    }

}
