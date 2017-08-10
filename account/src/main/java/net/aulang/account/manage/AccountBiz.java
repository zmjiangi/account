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
    public Account getByLoginName(String loginName) {
        return null;
    }

    @Override
    public Account login(String username, String password) {
        return null;
    }

    @Override
    public Account register(Account account) {
        return null;
    }

    public Account changePassword(String accountId, String password) {
        return null;
    }
}
