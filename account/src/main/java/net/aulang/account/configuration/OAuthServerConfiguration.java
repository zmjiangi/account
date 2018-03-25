package net.aulang.account.configuration;

import net.aulang.account.oauth.AccountDetailsService;
import net.aulang.account.oauth.provider.AuthCodeService;
import net.aulang.account.oauth.provider.OAuthClientService;
import net.aulang.account.oauth.provider.OAuthTokenService;
import net.aulang.account.oauth.token.MongoTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(AuthorizationServerProperties.class)
public class OAuthServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthorizationServerProperties properties;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AccountDetailsService userDetailsService;
    @Autowired
    private OAuthClientService clientService;
    @Autowired
    private OAuthTokenService tokenService;
    @Autowired
    private AuthCodeService codeService;
    @Autowired
    private MongoTokenStore tokenStore;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        if (properties.getCheckTokenAccess() != null) {
            security.checkTokenAccess(properties.getCheckTokenAccess());
        }
        if (properties.getTokenKeyAccess() != null) {
            security.tokenKeyAccess(properties.getTokenKeyAccess());
        }
        if (properties.getRealm() != null) {
            security.realm(properties.getRealm());
        }
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(codeService)
                .tokenServices(tokenService)
                .tokenStore(tokenStore);
    }
}
