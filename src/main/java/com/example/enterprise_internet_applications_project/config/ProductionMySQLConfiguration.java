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
        entityManagerFactoryRef = "proEntityManagerFactory",
        transactionManagerRef = "proTransactionManager")
@Profile("production-mysql")
public class ProductionMySQLConfiguration {


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.pro.mysql")
    public DataSourceProperties proDatasourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.pro.mysql.configuration")
    public DataSource proDatasource() {
        return proDatasourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "proEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean proEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(proDatasource())
                .packages("com.example.enterprise_internet_applications_project.models")
                .build();
    }

    @Bean(name = "proTransactionManager")
    @Primary
    public PlatformTransactionManager proTransactionManager(final @Qualifier("proEntityManagerFactory") LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return new JpaTransactionManager(localContainerEntityManagerFactoryBean.getObject());
    }


}
