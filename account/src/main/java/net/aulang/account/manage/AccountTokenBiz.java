package net.aulang.account.manage;

import net.aulang.account.document.AccountToken;
import net.aulang.account.repository.AccountTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountTokenBiz {
    @Autowired
    private AccountTokenRepository dao;

    public AccountToken save(AccountToken token) {
        return dao.save(token);
    }

    public AccountToken findByAccessToken(String accessToken) {
        AccountToken token = dao.findByAccessToken(accessToken);

        return token;
    }

    public AccountToken findByRefreshToken(String refreshToken) {
        AccountToken token = dao.findByRefreshToken(refreshToken);

        return token;
    }
}
