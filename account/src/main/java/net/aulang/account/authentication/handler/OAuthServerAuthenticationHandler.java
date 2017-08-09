package net.aulang.account.authentication.handler;

import net.aulang.account.model.Account;
import net.aulang.account.service.AccountService;
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
 * OAuth服务端认证处理器
 */
@Component("oauthServerAuthenticationHandler")
public class OAuthServerAuthenticationHandler extends AbstractUsernamePasswordAuthenticationHandler {
	@Autowired
	private AccountService accountService;

	public OAuthServerAuthenticationHandler(String name, ServicesManager servicesManager,
			PrincipalFactory principalFactory, Integer order) {
		super(name, servicesManager, principalFactory, order);
	}

	@Override
	protected HandlerResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential,
			String originalPassword) throws GeneralSecurityException, PreventedException {
		Account account;
		try {
			account = accountService.login(credential.getUsername(), credential.getPassword());
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		}

		if (account != null) {
			if (account.getMustChangePassword()) {
				/**
				 * 必须修改密码
				 */
				throw new AccountPasswordMustChangeException("请修改您的初始密码！");
			}
			return createHandlerResult(credential, principalFactory.createPrincipal(account.getId()), null);
		} else {
			throw new FailedLoginException();
		}
	}
}
