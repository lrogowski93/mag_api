package mag.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mag.cfg")
public record MagConfig(int companyId, int warehouseId, int userId, int employeeId, int orderDocTypeId) {
}
