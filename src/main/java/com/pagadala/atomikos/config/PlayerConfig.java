package com.pagadala.atomikos.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.pagadala.atomikos.model.player.Player;
import com.pagadala.atomikos.repository.player.PlayerRepository;

@Configuration
@EnableJpaRepositories(basePackageClasses = PlayerRepository.class, entityManagerFactoryRef = "playerEntityManagerFactory", transactionManagerRef = "playerTransactionManager")
public class PlayerConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.player")
    public DataSourceProperties playerDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource playerDataSource() {
        return playerDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean playerEntityManagerFactory(
            @Qualifier("playerDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(playerDataSource())
                .packages(Player.class)
                .build();
    }

    @Bean
    public PlatformTransactionManager playerTransactionManager(
            @Qualifier("playerEntityManagerFactory") LocalContainerEntityManagerFactoryBean playerEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(playerEntityManagerFactory.getObject()));
    }
}
