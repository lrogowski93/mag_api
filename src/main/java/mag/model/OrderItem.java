package mag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;


@Builder
@Getter
@Setter
public class OrderItem {
    @JsonIgnore
    private long id;
    @NonNull
    private String index;
    private int quantity;
    private String description;
}
