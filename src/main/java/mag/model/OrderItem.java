package mag.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class OrderItem {
    //RM_DodajPozycjeZamowienia
    private long id;
    private long orderId;
    private long itemId;
    private String vat;
    private int ordered;
    private int fulfilled;
    private int reserved;
    private int toReserve;
    private BigDecimal netValue;
    private BigDecimal grossValue;
    private BigDecimal currencyNetVal;
    private BigDecimal currencyGrossVal;
    private int conversion;
    private String unit;
    private BigDecimal markup;
    private String description;
    private int markupSign;
    private int registrationMode;
    private int deliveryId;
    private int productVariantId;
    private Character priceType;
    private String serialNumber;
    private String crossBorderTransType;

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
        paramTranslations.put("TYP_TRANSAKCJI_TRANSGRANICZNEJ",crossBorderTransType);

        return  paramTranslations;
    }

}
