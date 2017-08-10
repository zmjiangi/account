package net.aulang.account.configuration;

import net.aulang.account.authentication.handler.AccountAuthenticationHandler;
import net.aulang.account.authentication.handler.OAuthServerAuthenticationHandler;
import net.aulang.account.authentication.principal.AccountPrincipalResolver;
import net.aulang.account.authentication.principal.OAuthServerPrincipalResolver;
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
    private AccountPrincipalResolver accountPrincipalResolver;
    @Autowired
    private AccountAuthenticationHandler accountAuthenticationHandler;

    @Autowired
    private OAuthServerAuthenticationHandler oAuthServerAuthenticationHandler;
    @Autowired
    private OAuthServerPrincipalResolver oAuthServerPrincipalResolver;

    @Override
    public void configureAuthenticationExecutionPlan(AuthenticationEventExecutionPlan plan) {
        plan.registerAuthenticationHandlerWithPrincipalResolver(accountAuthenticationHandler, accountPrincipalResolver);
        plan.registerAuthenticationHandlerWithPrincipalResolver(oAuthServerAuthenticationHandler, oAuthServerPrincipalResolver);
    }
}
