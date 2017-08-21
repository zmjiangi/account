package net.aulang.account;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
@EnableRedisHttpSession
@SpringBootApplication(exclude = {
        JerseyAutoConfiguration.class,
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        GroovyTemplateAutoConfiguration.class,
        MetricsDropwizardAutoConfiguration.class
})
@ComponentScan({"net.aulang.account", "org.apereo.cas"})
@EnableConfigurationProperties(CasConfigurationProperties.class)
@ImportResource(locations = {"classpath:/deployerConfigContext.xml", "classpath:/deployerConfigContext.groovy"})
public class AccountApplication extends SpringBootServletInitializer {
    public static void main(final String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(AccountApplication.class);
    }
}