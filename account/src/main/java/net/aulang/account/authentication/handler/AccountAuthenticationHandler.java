package net.aulang.account.authentication.handler;

import net.aulang.account.authentication.AccountCredential;
import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.Account;
import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.security.GeneralSecurityException;

/**
 * 账号密码认证处理器
 */
@Component("accountAuthenticationHandler")
public class AccountAuthenticationHandler implements AuthenticationHandler {
    @Autowired
    private AccountBiz accountBiz;

    private PrincipalFactory principalFactory = new DefaultPrincipalFactory();

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    protected HandlerResult createHandlerResult(final Credential credential, final Principal principal) {
        return new DefaultHandlerResult(this, new BasicCredentialMetaData(credential), principal, null);
    }

    /**
     * 认证处理过程
     */
    @Override
    public HandlerResult authenticate(Credential credential) throws GeneralSecurityException, PreventedException {
        Account account;
        AccountCredential accountCredential;
        try {
            accountCredential = (AccountCredential) credential;
            account = accountBiz.login(accountCredential.getUsername(), accountCredential.getPassword());
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
            return createHandlerResult(accountCredential, principalFactory.createPrincipal(account.getId()));
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
