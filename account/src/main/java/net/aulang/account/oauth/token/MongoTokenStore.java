package net.aulang.account.oauth.token;

import net.aulang.account.document.AccountToken;
import net.aulang.account.manage.AccountTokenBiz;
import net.aulang.account.oauth.AccountIdAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class MongoTokenStore implements TokenStore {
    @Autowired
    private AccountTokenBiz tokenBiz;

    private OAuth2AccessToken toAccessToken(AccountToken accountToken) {
        if (accountToken == null) {
            return null;
        }

        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(accountToken.getAccessToken());

        accessToken.setExpiration(accountToken.getAccessTokenExpiration());
        accessToken.setTokenType(accountToken.getTokenType());
        accessToken.setScope(accountToken.getScope());

        if (accountToken.getRefreshToken() != null) {
            accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(accountToken.getRefreshToken()));
        }

        return accessToken;
    }

    private OAuth2Authentication toAuthentication(AccountToken accountToken) {
        if (accountToken == null) {
            return null;
        }

        OAuth2Request storedRequest = new OAuth2Request(
                null,
                accountToken.getClientId(),
                null,
                true,
                accountToken.getScope(),
                null,
                accountToken.getRedirectUri(),
                null,
                null
        );

        AccountIdAuthentication authentication = new AccountIdAuthentication(accountToken.getAccountId());

        return new OAuth2Authentication(storedRequest, authentication);
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        AccountToken accountToken = tokenBiz.findByAccessToken(token);
        return toAuthentication(accountToken);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }

        tokenBiz.create(
                token.getValue(),
                token.getTokenType(),
                refreshToken,
                authentication.getOAuth2Request().getClientId(),
                authentication.getOAuth2Request().getScope(),
                authentication.getOAuth2Request().getRedirectUri(),
                authentication.getUserAuthentication().getName(),
                true
        );
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        AccountToken accountToken = tokenBiz.findByAccessToken(tokenValue);
        return toAccessToken(accountToken);
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        tokenBiz.deleteByAccessToken(token.getValue());
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        tokenBiz.updateRefreshToken(
                authentication.getUserAuthentication().getName(),
                authentication.getOAuth2Request().getClientId(),
                authentication.getOAuth2Request().getRedirectUri(),
                refreshToken.getValue()
        );
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        AccountToken accountToken = tokenBiz.findByRefreshToken(tokenValue);
        if (accountToken == null) {
            return null;
        }
        return new DefaultOAuth2RefreshToken(tokenValue);
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        AccountToken accountToken = tokenBiz.findByRefreshToken(token.getValue());
        return toAuthentication(accountToken);
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        tokenBiz.removeRefreshToken(token.getValue());
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        tokenBiz.deleteByRefreshToken(refreshToken.getValue());
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        AccountToken accountToken = tokenBiz.findByAccountIdAndClientIdAndRedirectUri(
                authentication.getUserAuthentication().getName(),
                authentication.getOAuth2Request().getClientId(),
                authentication.getOAuth2Request().getRedirectUri()
        );
        return toAccessToken(accountToken);
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();
        tokenBiz.findByAccountIdAndClientId(userName, clientId).parallelStream().forEach(
                e -> accessTokens.add(toAccessToken(e))
        );
        return accessTokens;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<OAuth2AccessToken> accessTokens = new ArrayList<>();
        tokenBiz.finbByClientId(clientId).parallelStream().forEach(
                e -> accessTokens.add(toAccessToken(e))
        );
        return accessTokens;
    }
}
