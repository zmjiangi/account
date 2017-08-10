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
 * OAuth服务端认证处理器
 */
@Component("oauthServerAuthenticationHandler")
public class OAuthServerAuthenticationHandler implements AuthenticationHandler {
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
            return createHandlerResult(credential, principalFactory.createPrincipal(account.getId()));
        } else {
            throw new FailedLoginException();
        }
    }

    @Override
    public boolean supports(Credential credential) {
        return (credential instanceof UsernamePasswordCredential) && !(credential instanceof AccountCredential);
    }
}
