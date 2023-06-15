package mag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    private long id;
    private long magId;
    private String username;
    @JsonIgnore
    private String password;
    private boolean enabled;

    @JsonIgnore
    public List<String> getRoles(){
        return List.of("USER");
    }

}
