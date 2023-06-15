package mag.mapper;

import mag.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemRowMapper implements RowMapper<OrderItem> {
    @Override
    public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        return OrderItem.builder()
                .id(rs.getLong("ID_ARTYKULU"))
                .index(rs.getString("INDEKS_HANDLOWY"))
                .build();
    }
}
