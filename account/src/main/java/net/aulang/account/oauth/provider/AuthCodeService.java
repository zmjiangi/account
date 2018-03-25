package net.aulang.account.oauth.provider;

import net.aulang.account.document.OAuthCode;
import net.aulang.account.manage.OAuthCodeBiz;
import net.aulang.account.oauth.AccountIdAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.stereotype.Service;

@Service
public class AuthCodeService implements AuthorizationCodeServices {
    @Autowired
    private OAuthCodeBiz codeBiz;

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        return codeBiz.create(
                authentication.getOAuth2Request().getClientId(),
                authentication.getOAuth2Request().getScope(),
                authentication.getUserAuthentication().getName(),
                true
        ).getCode();
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        OAuthCode authCode = codeBiz.findByCode(code);
        if (authCode == null) {
            return null;
        }

        OAuth2Request storedRequest = new OAuth2Request(
                null,
                authCode.getClientId(),
                null,
                true,
                authCode.getScope(),
                null,
                null,
                null,
                null
        );

        AccountIdAuthentication authentication = new AccountIdAuthentication(authCode.getAccountId());

        return new OAuth2Authentication(storedRequest, authentication);
    }
}
