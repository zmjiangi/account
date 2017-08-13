package net.aulang.account.authentication.handler;

import net.aulang.account.authentication.AccountCredential;
import net.aulang.account.authentication.principal.DefaultPrincipal;
import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.Account;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.security.GeneralSecurityException;

/**
 * 账号密码认证处理器
 */
@Component("accountAuthenticationHandler")
public class AccountAuthenticationHandler extends AbstractAuthenticationHandler {
    @Autowired
    private AccountBiz accountBiz;

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
            if (account.isMustChangePassword()) {
                /**
                 * 必须修改密码
                 */
                throw new AccountPasswordMustChangeException("请修改您的初始密码！");
            }
            DefaultPrincipal principal = new DefaultPrincipal(account.getId());

            return createHandlerResult(accountCredential, principal);
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
