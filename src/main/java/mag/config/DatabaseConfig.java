package mag.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {


    @Primary
    @Bean(name = "authDataSource")
    @ConfigurationProperties(prefix = "auth.datasource")
    public DataSource authDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "magDataSource")
    @ConfigurationProperties(prefix = "mag.datasource")
    public DataSource madDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "authJdbcTemplate")
    public JdbcTemplate authJdbcTemplate(@Qualifier("authDataSource") DataSource authDataSource) {
        return new JdbcTemplate(authDataSource);
    }

    @Bean(name ="magJdbcTemplate")
    public JdbcTemplate magJdbcTemplate(@Qualifier("magDataSource") DataSource magDataSource) {
        return new JdbcTemplate(magDataSource);
    }

    @Bean(name ="magJdbcTemplateNamedParameter")
    public NamedParameterJdbcTemplate magJdbcTemplateNamedParameter(@Qualifier("magDataSource") DataSource magDataSource) {
        return new NamedParameterJdbcTemplate(magDataSource);
    }

}
