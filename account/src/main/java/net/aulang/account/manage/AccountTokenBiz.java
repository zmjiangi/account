package net.aulang.account.manage;

import net.aulang.account.document.AccountToken;
import net.aulang.account.document.OAuthClient;
import net.aulang.account.repository.AccountTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class AccountTokenBiz {
    @Autowired
    private AccountTokenRepository dao;
    @Autowired
    private OAuthClientBiz clientBiz;

    public AccountToken save(AccountToken token) {
        AccountToken accountToken = findByAccountIdAndClientIdAndRedirectUri(
                token.getAccountId(),
                token.getClientId(),
                token.getRedirectUri()
        );

        if (accountToken != null) {
            token.setId(accountToken.getId());
        }

        return dao.save(token);
    }

    public List<AccountToken> finbByClientId(String clientId) {
        return dao.findByClientId(clientId);
    }

    public AccountToken findByAccessToken(String accessToken) {
        return dao.findByAccessToken(accessToken);
    }

    public AccountToken findByRefreshToken(String refreshToken) {
        return dao.findByRefreshToken(refreshToken);
    }

    public List<AccountToken> findByAccountIdAndClientId(String accountId, String clientId) {
        return dao.findByAccountIdAndClientId(accountId, clientId);
    }

    public AccountToken findByAccountIdAndClientIdAndRedirectUri(String accountId, String clientId, String redirectUri) {
        return dao.findByAccountIdAndClientIdAndRedirectUri(accountId, clientId, redirectUri);
    }

    public AccountToken refreshAccessToken(String refreshToken, String newAccessToken) {
        AccountToken accountToken = dao.findByRefreshToken(refreshToken);

        if (accountToken == null) {
            return null;
        }

        Date refreshTokenExpiration = accountToken.getRefreshTokenExpiration();
        if (refreshTokenExpiration != null && refreshTokenExpiration.before(new Date())) {
            throw new InvalidTokenException("令牌已过期");
        }

        accountToken.setAccessToken(newAccessToken);

        OAuthClient client = clientBiz.findByClientId(accountToken.getClientId());

        Calendar calendar = Calendar.getInstance();
        accountToken.setCreationDate(calendar.getTime());

        calendar.add(Calendar.SECOND, client.getAccessTokenValiditySeconds());
        accountToken.setAccessTokenExpiration(calendar.getTime());

        calendar.add(Calendar.SECOND, client.getRefreshTokenValiditySeconds());
        accountToken.setRefreshTokenExpiration(calendar.getTime());

        return save(accountToken);
    }

    public void deleteByAccessToken(String accessToken) {
        AccountToken token = dao.findByAccessToken(accessToken);

        if (token != null) {
            dao.delete(token);
        }
    }

    public void deleteByRefreshToken(String refreshToken) {
        AccountToken token = dao.findByRefreshToken(refreshToken);

        if (token != null) {
            dao.delete(token);
        }
    }

    public AccountToken updateRefreshToken(String accountId, String clientId, String redirectUri, String refreshToken) {
        AccountToken accountToken = findByAccountIdAndClientIdAndRedirectUri(accountId, clientId, redirectUri);
        if (accountToken == null) {
            return null;
        }

        accountToken.setRefreshToken(refreshToken);

        OAuthClient client = clientBiz.findByClientId(clientId);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, client.getRefreshTokenValiditySeconds());
        accountToken.setRefreshTokenExpiration(calendar.getTime());

        return save(accountToken);
    }

    public AccountToken removeRefreshToken(String refreshToken) {
        AccountToken accountToken = findByRefreshToken(refreshToken);
        if (accountToken == null) {
            return null;
        }

        accountToken.setRefreshToken(null);
        accountToken.setRefreshTokenExpiration(null);

        return save(accountToken);
    }

    public AccountToken create(
            String accessToken,
            String refreshToken,
            String clientId,
            Set<String> scope,
            String redirectUri,
            String accountId,
            boolean saved) {
        AccountToken accountToken = new AccountToken();

        accountToken.setScope(scope);
        accountToken.setClientId(clientId);
        accountToken.setRedirectUri(redirectUri);
        accountToken.setAccountId(accountId);
        accountToken.setAccessToken(accessToken);
        accountToken.setRefreshToken(refreshToken);


        OAuthClient client = clientBiz.findByClientId(clientId);
        if (client == null) {
            throw new NoSuchClientException("客户端不存在");
        }

        Calendar calendar = Calendar.getInstance();
        accountToken.setCreationDate(calendar.getTime());

        calendar.add(Calendar.SECOND, client.getAccessTokenValiditySeconds());
        accountToken.setAccessTokenExpiration(calendar.getTime());

        calendar.add(Calendar.SECOND, client.getRefreshTokenValiditySeconds());
        accountToken.setRefreshTokenExpiration(calendar.getTime());

        if (saved) {
            return save(accountToken);
        } else {
            return accountToken;
        }
    }
}
