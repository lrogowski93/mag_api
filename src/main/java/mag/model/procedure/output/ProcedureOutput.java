package mag.model.procedure.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcedureOutput {
    private int returnValue;

    public ProcedureOutput(int returnValue) {
        this.returnValue = returnValue;
    }
}
