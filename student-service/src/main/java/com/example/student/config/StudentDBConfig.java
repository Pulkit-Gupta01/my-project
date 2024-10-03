package com.example.student.config;


import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.example.student.repository.jpa.StudentRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.example.student.repository.jpa"},
        entityManagerFactoryRef = "studentEntityManagerFactory",
        transactionManagerRef = "studentTransactionManager"
)
public class StudentDBConfig {


    @Bean(name = "studentDataSource")
    @ConfigurationProperties("spring.datasource.student")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }



    @Bean(name = "studentEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("com.example.student.model.jpa")
                .properties(jpaProperties())
                .build();
    }

    @Bean(name = "studentTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("studentEntityManagerFactory") EntityManagerFactory studentEntityManagerFactory) {
        return new JpaTransactionManager(studentEntityManagerFactory);
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }

}