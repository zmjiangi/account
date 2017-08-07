package net.aulang.account.service;

import net.aulang.account.model.Account;

/**
 * 账号服务
 * @author Aulang
 *
 */
public interface AccountService {
	Account get();
	
	Account register(Account account);
}
