package net.aulang.account.configuration;

import net.aulang.account.authentication.PasswordResetConfigurer;
import org.apereo.cas.web.flow.CasWebflowConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

@Configuration
public class PasswordRestConfiguration {
    @Autowired
    private FlowBuilderServices flowBuilderServices;

    @Autowired
    @Qualifier("loginFlowRegistry")
    private FlowDefinitionRegistry loginFlowDefinitionRegistry;

    @Bean
    @Primary
    public CasWebflowConfigurer passwordManagementWebflowConfigurer() {
        return new PasswordResetConfigurer(flowBuilderServices, loginFlowDefinitionRegistry);
    }
}
