package net.aulang.account;

import org.apereo.cas.configuration.CasConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@ImportResource(locations = { "classpath:/applicationContext.xml", "classpath:/deployerConfigContext.groovy" })
@SpringBootApplication(exclude = { 
		JerseyAutoConfiguration.class,
		GroovyTemplateAutoConfiguration.class,
		MetricsDropwizardAutoConfiguration.class })
@ComponentScan(basePackages = { "org.apereo.cas", "org.pac4j.springframework", "net.aulang.account" },
		excludeFilters = { 
				@ComponentScan.Filter(
						type = FilterType.REGEX,
						pattern = "org\\.pac4j\\.springframework\\.web\\.ApplicationLogoutController") })
@EnableAsync
// @EnableConfigServer
@EnableConfigurationProperties(CasConfigurationProperties.class)
public class AccountApplication extends SpringBootServletInitializer {
	public static void main(final String[] args) {
		SpringApplication.run(AccountApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		return builder.sources(AccountApplication.class);
	}
}