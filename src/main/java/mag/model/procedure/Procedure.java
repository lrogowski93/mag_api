package mag.model.procedure;

import java.util.Map;

public interface Procedure {
    String getProcedureName();

    Map<String, Object> getProcedureParams();
}
