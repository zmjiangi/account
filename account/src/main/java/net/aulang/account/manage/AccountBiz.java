package net.aulang.account.manage;

import net.aulang.account.model.Account;
import net.aulang.account.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountBiz implements AccountService {
    @Override
    public Account get() {
        return null;
    }

    @Override
    public Account getByUsername(String username) {
        return new Account(username, username, username);
    }

    @Override
    public Account login(String username, String password) {
        return new Account(username, username, password);
    }

    @Override
    public Account register(Account account) {
        return null;
    }

    public Account changePassword(String accountId, String password) {
        return new Account(accountId, accountId, password);
    }
}
