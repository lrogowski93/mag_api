package mag.model.procedure.output;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddOrderHeaderOutput extends ProcedureOutput {
    private long newOrderId;

    public AddOrderHeaderOutput(int returnValue, long newOrderId) {
        super(returnValue);
        this.newOrderId = newOrderId;
    }
}
