package mag.model.procedure;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcedureReturnValue {
    private int returnValue;
    private long newOrderId;

    public ProcedureReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }
}
