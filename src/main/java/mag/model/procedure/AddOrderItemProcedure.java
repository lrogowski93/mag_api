package mag.model.procedure;

import lombok.Getter;
import lombok.Setter;
import mag.model.OrderItem;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AddOrderItemProcedure implements Procedure {

    private final String procedureName = "RM_DodajPozycjeZamowienia";
    private long id;
    private long orderId;
    private long itemId;
    private String vat;
    private int ordered;
    private int fulfilled;
    private int reserved;
    private int toReserve;
    private BigDecimal netPrice;
    private BigDecimal grossPrice;
    private BigDecimal currencyNetPrice = BigDecimal.ZERO;
    private BigDecimal currencyGrossPrice = BigDecimal.ZERO;
    private int conversion = 1;
    private String unit = "szt.";
    private BigDecimal markup = BigDecimal.ZERO;
    private String description;
    private int markupSign;
    private int registrationMode;
    private int deliveryId;
    private int productVariantId;
    private String priceType = "m";
    private String serialNumber;
    private String crossBorderTransType = "Nie dotyczy";

    public AddOrderItemProcedure(long orderId, OrderItem orderItem) {
        this.orderId = orderId;
        this.itemId = orderItem.getId();
        this.ordered = orderItem.getQuantity();
        this.netPrice = orderItem.getNetPrice();
        this.grossPrice = orderItem.getGrossPrice();
        this.vat = orderItem.getVat();
        this.toReserve = orderItem.getQuantity();
        this.description = orderItem.getDescription();
    }

    @Override
    public Map<String, Object> getProcedureParams() {
        Map<String, Object> paramTranslations = new HashMap<>();

        paramTranslations.put("ID_POZYCJI_ZAMOWIENIA", id);
        paramTranslations.put("ID_ZAMOWIENIA", orderId);
        paramTranslations.put("ID_ARTYKULU", itemId);
        paramTranslations.put("KOD_VAT", vat);
        paramTranslations.put("ZAMOWIONO", ordered);
        paramTranslations.put("ZREALIZOWANO", fulfilled);
        paramTranslations.put("ZAREZERWOWANO", reserved);
        paramTranslations.put("DO_REZERWACJI", toReserve);
        paramTranslations.put("CENA_NETTO", netPrice);
        paramTranslations.put("CENA_BRUTTO", grossPrice);
        paramTranslations.put("CENA_NETTO_WAL", currencyNetPrice);
        paramTranslations.put("CENA_BRUTTO_WAL", currencyGrossPrice);
        paramTranslations.put("PRZELICZNIK", conversion);
        paramTranslations.put("JEDNOSTKA", unit);
        paramTranslations.put("NARZUT", markup);
        paramTranslations.put("OPIS", description);
        paramTranslations.put("ZNAK_NARZUTU", markupSign);
        paramTranslations.put("trybrejestracji", registrationMode);
        paramTranslations.put("ID_DOSTAWY_REZ", deliveryId);
        paramTranslations.put("ID_WARIANTU_PRODUKTU", productVariantId);
        paramTranslations.put("ZNACZNIK_CENY", priceType);
        paramTranslations.put("NR_SERII", serialNumber);
        paramTranslations.put("TYP_TRANSKACJI_TRANSGRANICZNEJ", crossBorderTransType);

        return paramTranslations;
    }

}
