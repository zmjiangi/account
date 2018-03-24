package net.aulang.account.oauth.token;

import net.aulang.account.document.AccountToken;
import net.aulang.account.manage.AccountTokenBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Component
public class MongoTokenStore implements TokenStore {
    private static final int REFRESHTOKEN_VALID_MONTHS = 3;

    @Autowired
    private AccountTokenBiz tokenBiz;

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        AccountToken accountToken = tokenBiz.findByAccessToken(token);
        if (accountToken == null) {
            return null;
        }

        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        AccountToken accountToken = new AccountToken();

        accountToken.setScope(token.getScope());
        accountToken.setAccessToken(token.getValue());
        accountToken.setTokenType(token.getTokenType());

        Calendar calendar = Calendar.getInstance();

        Date date = token.getExpiration();
        if (date != null) {
            calendar.setTime(date);
        } else {
            calendar.add(Calendar.SECOND, token.getExpiresIn());
        }
        accountToken.setAccessTokenExpiration(calendar.getTime());

        calendar.add(Calendar.MONTH, REFRESHTOKEN_VALID_MONTHS);
        accountToken.setRefreshTokenExpiration(calendar.getTime());

        OAuth2RefreshToken refreshToken = token.getRefreshToken();
        if (refreshToken != null) {
            accountToken.setRefreshToken(refreshToken.getValue());
        }

        accountToken.setClientId(authentication.getOAuth2Request().getClientId());
        accountToken.setAccountId(authentication.getUserAuthentication().getName());

        accountToken.setCreationDate(new Date());

        tokenBiz.save(accountToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        AccountToken accountToken = tokenBiz.findByAccessToken(tokenValue);
        if (accountToken == null) {
            return null;
        }

        DefaultOAuth2AccessToken accessToken = new DefaultOAuth2AccessToken(accountToken.getAccessToken());

        accessToken.setScope(accountToken.getScope());
        accessToken.setTokenType(accountToken.getTokenType());
        accessToken.setExpiration(accountToken.getAccessTokenExpiration());

        if (accountToken.getRefreshToken() != null) {
            accessToken.setRefreshToken(new DefaultOAuth2RefreshToken(accountToken.getRefreshToken()));
        }

        return accessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        tokenBiz.deleteByAccessToken(token.getValue());
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        AccountToken accountToken = tokenBiz.findByAccountIdAndClientId(
                authentication.getUserAuthentication().getName(),
                authentication.getOAuth2Request().getClientId()
        );
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, REFRESHTOKEN_VALID_MONTHS);

        accountToken.setRefreshToken(refreshToken.getValue());
        accountToken.setRefreshTokenExpiration(calendar.getTime());
        tokenBiz.save(accountToken);
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
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {

    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {

    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return null;
    }
}
