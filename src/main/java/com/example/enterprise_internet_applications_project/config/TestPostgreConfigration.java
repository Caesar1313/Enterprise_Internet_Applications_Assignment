package com.example.enterprise_internet_applications_project.config;


import com.example.enterprise_internet_applications_project.models.*;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.enterprise_internet_applications_project.repositories",
        entityManagerFactoryRef = "testEntityManagerFactory",
        transactionManagerRef = "testTransactionManager")
@Profile("test-postgre")
public class TestPostgreConfigration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.test.postgre")
    public DataSourceProperties testDatasourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.test.postgre.configuration")
    public DataSource testDatasource() {
        return testDatasourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "testEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean testEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(testDatasource())
                .packages("com.example.enterprise_internet_applications_project.models")
                .build();
    }

    @Bean(name = "testTransactionManager")
    @Primary
    public PlatformTransactionManager testTransactionManager(final @Qualifier("testEntityManagerFactory") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }
}
