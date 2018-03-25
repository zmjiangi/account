package net.aulang.account.oauth.token;

import net.aulang.account.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

@Service
public class MongoTokenService implements AuthorizationServerTokenServices {
    @Autowired
    private MongoTokenStore tokenStore;

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(StrUtils.UUID());

        accessToken.setScope(authentication.getOAuth2Request().getScope());
        accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(StrUtils.UUID()));

        tokenStore.storeAccessToken(accessToken, authentication);

        return tokenStore.readAccessToken(accessToken.getValue());
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshToken, TokenRequest tokenRequest) throws AuthenticationException {
        return tokenStore.refreshAccessToken(refreshToken, StrUtils.UUID());
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return tokenStore.getAccessToken(authentication);
    }
}
