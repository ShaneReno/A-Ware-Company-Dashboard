
package io.shane.awareproject;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JpaConfig {

    @Bean(name = "data")
    @Primary
    public DataSource dataDataSource()
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost/data");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("datahouse");
        return dataSourceBuilder.build();
    }
    

    @Bean(name = "aware")
    public DataSource awareDataSource()
    {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost/aware");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("datahouse");
        return dataSourceBuilder.build();
    }
}
