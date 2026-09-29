package com.ems.algaworks.algashop.ordering.infrastructure.persistence.config;

import org.h2.tools.Server;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class H2ConsoleConfig {


    @Bean(initMethod = "start", destroyMethod = "stop")
    @ConditionalOnProperty(
            name = "algashop.h2-console.enabled",
            havingValue = "true",
            matchIfMissing = true
    )
    public Server h2ConsoleServer() throws SQLException {
        return Server.createWebServer(
                "-web",
                "-webAllowOthers",
                "-webPort", "8082"
        );
    }
}