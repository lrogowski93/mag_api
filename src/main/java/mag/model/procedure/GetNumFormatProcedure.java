package mag.model.procedure;

import lombok.Getter;
import lombok.Setter;
import mag.config.MagConfig;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class GetNumFormatProcedure implements Procedure {
    private final String procedureName = "JL_PobierzFormatNumeracji_Server";
    private final int documentId = 2;
    private int companyId;
    private int docTypeId;
    private int warehouseId;

    public GetNumFormatProcedure(MagConfig magConfig) {
        this.companyId = magConfig.companyId();
        this.docTypeId = magConfig.orderDocTypeId();
        this.warehouseId = magConfig.warehouseId();
    }

    @Override
    public Map<String, Object> getProcedureParams() {
        Map<String, Object> paramTranslations = new HashMap<>();

        paramTranslations.put("id_firmy", companyId);
        paramTranslations.put("dokument", documentId);
        paramTranslations.put("id_typu", docTypeId);
        paramTranslations.put("id_zasobu", warehouseId);

        return paramTranslations;
    }

}
