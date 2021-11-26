package com.uploadservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class MainDBConfig {

    @Primary
    @Bean(name="videouploadDataSource")
    @ConfigurationProperties(prefix="spring.datasource.videoupload")
    public DataSource videouploadDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "videouploadJpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(
            EntityManagerFactoryBuilder _builder,
            @Qualifier("videouploadDataSource") DataSource _dataSource ) {
        return _builder.dataSource(_dataSource).packages("com.genesislabs.video.entity").build();
    }

    @Primary
    @Bean(name = "videouploadTransactionManager")
    public JpaTransactionManager transactionManager(
            @Qualifier("videouploadJpaEntityManagerFactory") LocalContainerEntityManagerFactoryBean _mfBean
    ) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(_mfBean.getObject());
        return transactionManager;
    }
}