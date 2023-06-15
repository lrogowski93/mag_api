package mag.model.procedure;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AddOrderHeaderProcedure {

    private final String procedureName = "RM_DodajZamowienie";
    private int companyId;
    private int customerId;
    private int warehouseId;
    private int type = 1;
    private long date;
    private int userId;
    private int registrationMode = 0;
    private String valueType = "Netto";

    public AddOrderHeaderProcedure(int companyId, int customerId, int warehouseId, long date, int userId) {
        this.companyId = companyId;
        this.customerId = customerId;
        this.warehouseId = warehouseId;
        this.date = date;
        this.userId = userId;

    }

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
