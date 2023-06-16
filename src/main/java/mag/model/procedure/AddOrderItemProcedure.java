package mag.model.procedure;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AddOrderItemProcedure {

    private final String procedureName = "RM_DodajPozycjeZamowienia";
    private long id;
    private long orderId;
    private long itemId;
    private String vat = "23";
    private int ordered;
    private int fulfilled;
    private int reserved;
    private int toReserve;
    private BigDecimal netValue = BigDecimal.ZERO;
    private BigDecimal grossValue = BigDecimal.ZERO;
    private BigDecimal currencyNetVal = BigDecimal.ZERO;
    private BigDecimal currencyGrossVal = BigDecimal.ZERO;
    private int conversion = 1;
    private String unit = "szt.";
    private BigDecimal markup = BigDecimal.ZERO;
    private String description;
    private int markupSign;
    private int registrationMode;
    private int deliveryId;
    private int productVariantId;
    private String priceType = "i";
    private String serialNumber;
    private String crossBorderTransType = "Nie dotyczy";

    public AddOrderItemProcedure(long orderId, long itemId, int ordered, String description) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.ordered = ordered;
        this.toReserve = ordered;
        this.description = description;
    }

    public Map<String, Object> getProcedureParams() {
        Map<String, Object> paramTranslations = new HashMap<>();

        paramTranslations.put("ID_POZYCJI_ZAMOWIENIA",id);
        paramTranslations.put("ID_ZAMOWIENIA",orderId);
        paramTranslations.put("ID_ARTYKULU",itemId);
        paramTranslations.put("KOD_VAT",vat);
        paramTranslations.put("ZAMOWIONO",ordered);
        paramTranslations.put("ZREALIZOWANO",fulfilled);
        paramTranslations.put("ZAREZERWOWANO",reserved);
        paramTranslations.put("DO_REZERWACJI",toReserve);
        paramTranslations.put("CENA_NETTO",netValue);
        paramTranslations.put("CENA_BRUTTO",grossValue);
        paramTranslations.put("CENA_NETTO_WAL",currencyNetVal);
        paramTranslations.put("CENA_BRUTTO_WAL",currencyGrossVal);
        paramTranslations.put("PRZELICZNIK",conversion);
        paramTranslations.put("JEDNOSTKA",unit);
        paramTranslations.put("NARZUT",markup);
        paramTranslations.put("OPIS",description);
        paramTranslations.put("ZNAK_NARZUTU",markupSign);
        paramTranslations.put("trybrejestracji",registrationMode);
        paramTranslations.put("ID_DOSTAWY_REZ",deliveryId);
        paramTranslations.put("ID_WARIANTU_PRODUKTU",productVariantId);
        paramTranslations.put("ZNACZNIK_CENY",priceType);
        paramTranslations.put("NR_SERII",serialNumber);
        paramTranslations.put("TYP_TRANSKACJI_TRANSGRANICZNEJ",crossBorderTransType);

        return  paramTranslations;
    }

}
