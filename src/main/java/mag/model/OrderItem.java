package mag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class OrderItem {
    @JsonIgnore
    private long id;
    @NotEmpty(message = "Index cannot be blank")
    private String index;
    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private BigDecimal netPrice;
    private BigDecimal grossPrice;
    //@NotEmpty(message = "VAT rate cannot be blank")
    @Pattern(regexp = "^(0|5|8|23)$", message = "Invalid VAT rate")
    private String vat;
    private String description;
}
