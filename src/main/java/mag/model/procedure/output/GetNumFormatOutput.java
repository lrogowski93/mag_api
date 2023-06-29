package mag.model.procedure.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNumFormatOutput extends ProcedureOutput {
    private String numFormat;
    private int docNumberPeriod;
    private int autoNum;
    private int numNotDependent;

    public GetNumFormatOutput(int returnValue, String numFormat, int docNumberPeriod, int autoNum, int numNotDependent) {
        super(returnValue);
        this.numFormat = numFormat;
        this.docNumberPeriod = docNumberPeriod;
        this.autoNum = autoNum;
        this.numNotDependent = numNotDependent;
    }
}
