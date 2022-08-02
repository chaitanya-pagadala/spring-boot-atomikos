package com.pagadala.atomikos.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.pagadala.atomikos.model.team.Team;
import com.pagadala.atomikos.repository.team.TeamRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = TeamRepository.class, entityManagerFactoryRef = "teamEntityManagerFactory", transactionManagerRef = "teamTransactionManager")
public class TeamConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.team")
    public DataSourceProperties teamDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource teamDataSource() {
        return teamDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean teamEntityManagerFactory(
            @Qualifier("teamDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(teamDataSource())
                .packages(Team.class)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager teamTransactionManager(
            @Qualifier("teamEntityManagerFactory") LocalContainerEntityManagerFactoryBean teamEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(teamEntityManagerFactory.getObject()));
    }
    
}
