package mag.model.procedure;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class ConfirmOrderProcedure{

    private final String procedureName = "RM_ZatwierdzZamowienie";
    private long orderId;
    private long customerId;
    private int docTypeId;
    private String docNumber;
    private String docNumberFormat;
    private int docNumberPeriod;
    private int autoNum;
    private int numNotDependent;
    private int autoNum2;
    private int companyId;
    private int warehouseId;
    private int date;
    private int completionDate;
    private BigDecimal advancePayment;
    private int priority;
    private int autoReservation;
    private String customerOrderNumber;
    private int orderType;
    private long employeeId;
    private BigDecimal currencyConversion;
    private int currencyValueDate;
    private String currency;
    private int currencyDoc;
    private int status;
    private int registrationMode;
    private BigDecimal markup;
    private int markupSign;
    private String notes;
    private int bankAccNumId;
    private String additionalInfo;
    private String orderingPersonName;
    private long contactPersonId;
    private String paymentMethod;
    private int daysToPay;
    private int invoiceReceipt;
    private String shipmentNumber;
    private long shipmentOperatorId;

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
