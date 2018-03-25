package net.aulang.account.configuration;

import net.aulang.account.oauth.AccountDetailsService;
import net.aulang.account.oauth.provider.AuthCodeService;
import net.aulang.account.oauth.provider.OAuthClientService;
import net.aulang.account.oauth.token.MongoTokenService;
import net.aulang.account.oauth.token.MongoTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AccountDetailsService accountDetailsService;
    @Autowired
    private OAuthClientService clientService;
    @Autowired
    private MongoTokenService tokenService;
    @Autowired
    private AuthCodeService codeService;
    @Autowired
    private MongoTokenStore tokenStore;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(accountDetailsService);
        endpoints.authorizationCodeServices(codeService);
        endpoints.tokenServices(tokenService);
        endpoints.tokenStore(tokenStore);
    }
}
