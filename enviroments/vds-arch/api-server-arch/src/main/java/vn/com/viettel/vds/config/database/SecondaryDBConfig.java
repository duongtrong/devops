package vn.com.viettel.vds.config.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * If we need an (optional) secondary database, configure it here
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "app.datasource.secondary.enable", havingValue = "true")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager", basePackages = {"vn.com.viettel.vds.repository.secondary"})
public class SecondaryDBConfig {

    @Bean
    @ConfigurationProperties("app.datasource.secondary")
    public DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "secondDataSource")
    @ConfigurationProperties(prefix = "app.datasource.secondary.configuration")
    public DataSource dataSource() {
        return secondaryDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean(name = "secondEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("secondDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("vn.com.viettel.vds.model").persistenceUnit("secondary")
                .build();
    }

    @Bean(name = "secondTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondEntityManagerFactory") EntityManagerFactory barEntityManagerFactory) {
        return new JpaTransactionManager(barEntityManagerFactory);
    }
}