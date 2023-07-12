package mag.service;

import mag.model.procedure.AddOrderHeaderProcedure;
import mag.model.procedure.GetNumFormatProcedure;
import mag.model.procedure.Procedure;
import mag.model.procedure.output.AddOrderHeaderOutput;
import mag.model.procedure.output.GetNumFormatOutput;
import mag.model.procedure.output.ProcedureOutput;
import mag.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class HelperMethodsService {

    private final JdbcTemplate magJdbcTemplate;
    private final UserRepository userRepository;
    public HelperMethodsService(@Qualifier("magJdbcTemplate") JdbcTemplate magJdbcTemplate, UserRepository userRepository) {
        this.magJdbcTemplate = magJdbcTemplate;
        this.userRepository = userRepository;
    }

    public BigDecimal calcGrossPrice(BigDecimal netPrice, String vat) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(magJdbcTemplate)
                .withFunctionName("JL_Func_ObliczBrutto");
        SqlParameterSource inParameters = new MapSqlParameterSource()
                .addValue("netto", netPrice)
                .addValue("vat", vat);
        simpleJdbcCall.withReturnValue();

        Map<String, Object> outParameters = simpleJdbcCall.execute(inParameters);

        return (BigDecimal) outParameters.get("RETURN_VALUE");
    }

    public long getCurrentDate() {
        return ChronoUnit.DAYS.between(LocalDate.of(1800, 12, 28), LocalDate.now());
    }

    public ProcedureOutput callProcedure(Procedure procedure) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(magJdbcTemplate).withProcedureName(procedure.getProcedureName());
        SqlParameterSource inParameters = new MapSqlParameterSource().addValues(procedure.getProcedureParams());
        simpleJdbcCall.withReturnValue();
        Map<String, Object> outParameters = simpleJdbcCall.execute(inParameters);
        int returnValue = (int) outParameters.get("RETURN_VALUE");
        return getProcedureOutput(returnValue, procedure, outParameters);
    }

    private ProcedureOutput getProcedureOutput(int returnValue, Procedure procedure, Map<String, Object> outParameters) {
        if (procedure instanceof AddOrderHeaderProcedure) {
            return new AddOrderHeaderOutput(returnValue, getNewOrderId(outParameters));
        } else if (procedure instanceof GetNumFormatProcedure) {
            return new GetNumFormatOutput(
                    returnValue,
                    (String) outParameters.get("format_num"),
                    (short) outParameters.get("okresnumeracji"),
                    (short) outParameters.get("parametr1"),
                    (short) outParameters.get("parametr2")
            );
        } else
            return new ProcedureOutput(returnValue);
    }

    private long getNewOrderId(Map<String, Object> outParameters) {
        Object resultSet = ((Map<?, ?>) ((List<?>) outParameters.get("#result-set-1")).get(0)).get("");
        BigDecimal orderId = (BigDecimal) resultSet;
        return orderId.longValue();
    }

    public String getOrderStatus(long orderId, String username) {
        long customerId = userRepository.findByUsername(username).get().getMagId();
        try {
            String sqlQuery = "SELECT STATUS_ZAM FROM ZAMOWIENIE WHERE ID_ZAMOWIENIA=? AND ID_KONTRAHENTA=?";
            return magJdbcTemplate.queryForObject(sqlQuery, String.class, orderId, customerId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


}
