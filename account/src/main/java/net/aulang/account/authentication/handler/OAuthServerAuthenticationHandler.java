package net.aulang.account.authentication.handler;

import net.aulang.account.authentication.AccountCredential;
import net.aulang.account.authentication.principal.DefaultPrincipal;
import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.Account;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.HandlerResult;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.exceptions.AccountPasswordMustChangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import java.security.GeneralSecurityException;

/**
 * OAuth服务端认证处理器
 */
@Component("oauthServerAuthenticationHandler")
public class OAuthServerAuthenticationHandler extends AbstractAuthenticationHandler {
    @Autowired
    private AccountBiz accountBiz;

    /**
     * 认证处理过程
     */
    @Override
    public HandlerResult authenticate(Credential credential) throws GeneralSecurityException, PreventedException {
        Account account;
        try {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            account = accountBiz.login(usernamePasswordCredential.getUsername(), usernamePasswordCredential.getPassword());
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
            DefaultPrincipal principal = new DefaultPrincipal(account.getId());

            return createHandlerResult(credential, principal);
        } else {
            throw new FailedLoginException();
        }
    }

    @Override
    public boolean supports(Credential credential) {
        return (credential instanceof UsernamePasswordCredential) && !(credential instanceof AccountCredential);
    }
}
