package com.nikolay.autoconfigure;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
@ConditionalOnMissingBean(DataSource.class)
@EnableConfigurationProperties(DatabaseProperties.class)
public class DatabaseAutoConfiguration {

  private static final String DRIVER_CLASS_NAME = "org.hsqldb.jdbcDriver";
  private static final String URL = "jdbc:hsqldb:mem:hr";
  private static final String USERNAME = "sa";
  private static final String PASSWORD = "";
  private static final String DEFAULT_SCHEMA = "create_table.sql";

  private final DatabaseProperties databaseProperties;

  public DatabaseAutoConfiguration(DatabaseProperties databaseProperties) {
    this.databaseProperties = databaseProperties;
  }

  @Bean
  @ConditionalOnMissingBean
  public DataSource dataSource() {

    String driverClassName =
        databaseProperties.getDriverClassName() == null ? DRIVER_CLASS_NAME : databaseProperties.getDriverClassName();
    String url = databaseProperties.getUrl() == null ? URL : databaseProperties.getUrl();
    String username = databaseProperties.getUsername() == null ? USERNAME : databaseProperties.getUsername();
    String password = databaseProperties.getPassword() == null ? PASSWORD : databaseProperties.getPassword();
    String schema = databaseProperties.getSchema() == null ? DEFAULT_SCHEMA : databaseProperties.getSchema();

    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(driverClassName);
    dataSource.setUrl(url);
    dataSource.setUsername(username);
    dataSource.setPassword(password);

    DatabasePopulatorUtils.execute(createDatabasePopulator(schema), dataSource);
    return dataSource;
  }

  private DatabasePopulator createDatabasePopulator(String schema) {
    ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
    resourceDatabasePopulator.setContinueOnError(true);
    resourceDatabasePopulator.addScript(new ClassPathResource(schema));
    return resourceDatabasePopulator;
  }

  @Bean
  @ConditionalOnMissingBean
  public DataSourceTransactionManager txManager() {
    return new DataSourceTransactionManager(dataSource());
  }

}
