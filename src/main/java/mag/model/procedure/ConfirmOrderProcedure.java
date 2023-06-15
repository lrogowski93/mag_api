package mag.model.procedure;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ConfirmOrderProcedure{

    private final String procedureName = "RM_ZatwierdzZamowienie";
    private long orderId;
    private long customerId;
    private int docTypeId = 12;
    private String docNumber = "<auto> ";
    private String docNumberFormat = "ZO ####/$$";
    private int docNumberPeriod = 2;
    private int autoNum = 1;
    private int numNotDependent = 1;
    private int autoNum2;
    private int companyId;
    private int warehouseId;
    private long date;
    private long completionDate;
    private BigDecimal advancePayment;
    private int priority = 2;
    private int autoReservation = 1;
    private String customerOrderNumber;
    private int orderType = 1;
    private long employeeId;
    private BigDecimal currencyConversion = new BigDecimal(1);
    private int currencyValueDate;
    private String currency;
    private int currencyDoc;
    private int status = 1;
    private int registrationMode;
    private BigDecimal markup;
    private int markupSign;
    private String notes;
    private int bankAccNumId;
    private String additionalInfo;
    private String orderingPersonName;
    private long contactPersonId;
    private String paymentMethod = "przelew";
    private int daysToPay = 7;
    private int invoiceReceipt = 1;
    private String shipmentNumber;
    private long shipmentOperatorId;

    public ConfirmOrderProcedure(long orderId, long customerId, int companyId, int warehouseId, long date, long employeeId, String notes) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.companyId = companyId;
        this.warehouseId = warehouseId;
        this.date = date;
        this.completionDate = date;
        this.employeeId = employeeId;
        this.notes = notes;
    }

    public Map<String, Object> getProcedureParams() {
        Map<String, Object> paramTranslations = new HashMap<>();
        paramTranslations.put("ID_ZAMOWIENIA",orderId);
        paramTranslations.put("ID_KONTRAHENTA",customerId);
        paramTranslations.put("ID_TYPU",docTypeId);
        paramTranslations.put("NUMER",docNumber);
        paramTranslations.put("NUM_FORMAT",docNumberFormat);
        paramTranslations.put("NUM_OKRESNUMERACJI",docNumberPeriod);
        paramTranslations.put("NUM_AUTO",autoNum);
        paramTranslations.put("NUM_NIEZALEZNY",numNotDependent);
        paramTranslations.put("AUTONUMER",autoNum2);
        paramTranslations.put("ID_FIRMY",companyId);
        paramTranslations.put("ID_MAG",warehouseId);
        paramTranslations.put("DATA",date);
        paramTranslations.put("DATA_REALIZACJI",completionDate);
        paramTranslations.put("ZALICZKA",advancePayment);
        paramTranslations.put("PRIORYTET",priority);
        paramTranslations.put("AUTO_REZERWACJA",autoReservation);
        paramTranslations.put("NR_ZAM_KLIENTA",customerOrderNumber);
        paramTranslations.put("TYP",orderType);
        paramTranslations.put("ID_PRACOWNIKA",employeeId);
        paramTranslations.put("PRZELICZNIK_WAL",currencyConversion);
        paramTranslations.put("DATA_KURS_WAL",currencyValueDate);
        paramTranslations.put("SYM_WAL",currency);
        paramTranslations.put("DOK_WAL",currencyDoc);
        paramTranslations.put("FLAGA_STANU",status);
        paramTranslations.put("trybrejestracji",registrationMode);
        paramTranslations.put("RABAT_NARZUT",markup);
        paramTranslations.put("ZNAK_RABATU",markupSign);
        paramTranslations.put("UWAGI",notes);
        paramTranslations.put("ID_RACHUNKU",bankAccNumId);
        paramTranslations.put("InformacjeDodatkowe",additionalInfo);
        paramTranslations.put("OsobaZamawiajaca",orderingPersonName);
        paramTranslations.put("Id_KONTAKTU",contactPersonId);
        paramTranslations.put("FormaPlatnosci",paymentMethod);
        paramTranslations.put("DniPlatnosci",daysToPay);
        paramTranslations.put("FakturaParagon",invoiceReceipt);
        paramTranslations.put("NumerPrzesylki",shipmentNumber);
        paramTranslations.put("IdOperatoraPrzesylki",shipmentOperatorId);
        return paramTranslations;
    }

}
