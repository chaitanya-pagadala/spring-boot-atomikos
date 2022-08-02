package com.pagadala.atomikos.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.pagadala.atomikos.model.player.Player;
import com.pagadala.atomikos.repository.player.PlayerRepository;

@Configuration
//@EnableJpaRepositories(basePackageClasses = PlayerRepository.class, entityManagerFactoryRef = "playerEntityManagerFactory", transactionManagerRef = "playerTransactionManager")
@EnableJpaRepositories(basePackageClasses = PlayerRepository.class, entityManagerFactoryRef = "playerEntityManagerFactory", transactionManagerRef = "transactionManager")
public class PlayerConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.player")
    public DataSourceProperties playerDataSourceProperties() {
        return new DataSourceProperties();
    }

    // @Bean
    // public DataSource playerDataSource() {
    //     return playerDataSourceProperties()
    //             .initializeDataSourceBuilder()
    //             .build();
    // }

    @Bean
    public AtomikosDataSourceBean playerDataSource(final @Qualifier("playerDataSourceProperties") DataSourceProperties playerDataSourceProperties) {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(playerDataSourceProperties.getUrl());        
        mysqlXaDataSource.setPassword(playerDataSourceProperties.getPassword());
        mysqlXaDataSource.setUser(playerDataSourceProperties.getUsername());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("playerDS");
		xaDataSource.setMinPoolSize(1);
		xaDataSource.setMaxPoolSize(10);
		xaDataSource.setBorrowConnectionTimeout(10000);
		return xaDataSource;        
    }
    

    @Bean    
    public LocalContainerEntityManagerFactoryBean playerEntityManagerFactory(
            @Qualifier("playerDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .jta(true)
                .packages(Player.class)
                .persistenceUnit("player")
                .build();
    }

    // @Bean
    // public PlatformTransactionManager playerTransactionManager(
    //         @Qualifier("playerEntityManagerFactory") LocalContainerEntityManagerFactoryBean playerEntityManagerFactory) {
    //     return new JpaTransactionManager(Objects.requireNonNull(playerEntityManagerFactory.getObject()));
    // }
}
