package net.aulang.account.service;

import net.aulang.account.model.Account;

/**
 * 账号服务
 *
 * @author Aulang
 */
public interface AccountService {
    Account get(String id);

    Account login(String loginName, String password);

    Account register(Account account);
}
