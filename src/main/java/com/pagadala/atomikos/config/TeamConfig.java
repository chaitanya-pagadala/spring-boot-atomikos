package com.pagadala.atomikos.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.pagadala.atomikos.model.team.Team;
import com.pagadala.atomikos.repository.team.TeamRepository;

@Configuration
//@EnableJpaRepositories(basePackageClasses = TeamRepository.class, entityManagerFactoryRef = "teamEntityManagerFactory", transactionManagerRef = "teamTransactionManager")
@EnableJpaRepositories(basePackageClasses = TeamRepository.class, entityManagerFactoryRef = "teamEntityManagerFactory", transactionManagerRef = "transactionManager")
public class TeamConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.team")
    public DataSourceProperties teamDataSourceProperties() {
        return new DataSourceProperties();
    }

    // @Bean
    // @Primary
    // public DataSource teamDataSource() {
    //     return teamDataSourceProperties()
    //             .initializeDataSourceBuilder()
    //             .build();
    // }

    @Bean
    @Primary
    public AtomikosDataSourceBean teamDataSource(final @Qualifier("teamDataSourceProperties") DataSourceProperties teamDataSourceProperties) {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(teamDataSourceProperties.getUrl());        
        mysqlXaDataSource.setPassword(teamDataSourceProperties.getPassword());
        mysqlXaDataSource.setUser(teamDataSourceProperties.getUsername());

        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
		xaDataSource.setXaDataSource(mysqlXaDataSource);
		xaDataSource.setUniqueResourceName("teamDS");
		xaDataSource.setMinPoolSize(1);
		xaDataSource.setMaxPoolSize(10);
		xaDataSource.setBorrowConnectionTimeout(10000);
		return xaDataSource;        
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean teamEntityManagerFactory(
            @Qualifier("teamDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {
        return builder                
                .dataSource(dataSource)
                .jta(true)
                .packages(Team.class)
                .persistenceUnit("team")
                .build();
    }

    // @Bean
    // @Primary
    // public PlatformTransactionManager teamTransactionManager(
    //         @Qualifier("teamEntityManagerFactory") LocalContainerEntityManagerFactoryBean teamEntityManagerFactory) {
    //     return new JpaTransactionManager(Objects.requireNonNull(teamEntityManagerFactory.getObject()));
    // }
    
}
