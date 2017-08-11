package net.aulang.account.configuration;

import net.aulang.account.authentication.handler.AccountAuthenticationHandler;
import net.aulang.account.authentication.handler.OAuthServerAuthenticationHandler;
import net.aulang.account.authentication.principal.DefaultPrincipalResolver;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlan;
import org.apereo.cas.authentication.AuthenticationEventExecutionPlanConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * 认证处理器配置
 */
@Configuration
public class AuthenticationHandlerConfiguration implements AuthenticationEventExecutionPlanConfigurer {
    @Autowired
    private DefaultPrincipalResolver defaultPrincipalResolver;
    @Autowired
    private AccountAuthenticationHandler accountAuthenticationHandler;
    @Autowired
    private OAuthServerAuthenticationHandler oAuthServerAuthenticationHandler;

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandlerWithPrincipalResolver(accountAuthenticationHandler, defaultPrincipalResolver);
        plan.registerAuthenticationHandlerWithPrincipalResolver(oAuthServerAuthenticationHandler, defaultPrincipalResolver);
    }
}
