package net.aulang.account.authentication.handler;

import net.aulang.account.authentication.AccountCredential;
import net.aulang.account.model.Account;
import net.aulang.account.service.AccountService;

import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.security.GeneralSecurityException;

/**
 * 账号密码认证处理器
 */
@Component("accountAuthenticationHandler")
public class AccountAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
	@Autowired
	private AccountService accountService;

	public AccountAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory,
			Integer order) {
		super(name, servicesManager, principalFactory, order);
	}

	@Override
	@Deprecated
	protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential transformedCredential,
			String originalPassword) throws GeneralSecurityException, PreventedException {
		/**
		 * 源代码有问题禁止使用
		 */
		return null;
	}

	/**
	 * 认证处理过程
	 */
	@Override
	protected HandlerResult doAuthentication(final Credential credential)
			throws GeneralSecurityException, PreventedException {
		Account account;
		AccountCredential accountCredential;
		try {
			accountCredential = (AccountCredential) credential;
			account = accountService.login(accountCredential.getUsername(), accountCredential.getPassword());
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}

		if (account != null) {
			accountCredential.setId(account.getId());
			if (account.getMustChangePassword()) {
				/**
				 * 必须修改密码
				 */
				throw new AccountPasswordMustChangeException("请修改您的初始密码！");
			}
			return createHandlerResult(accountCredential, principalFactory.createPrincipal(account.getId()), null);
		} else {
			throw new FailedLoginException();
		}
	}

	/**
	 * 判断本处理器是否能处理该类型的凭证
	 */
	@Override
	public boolean supports(final Credential credential) {
		return credential instanceof AccountCredential;
	}
}
