package net.aulang.account.authentication.principal;

import net.aulang.account.authentication.AccountCredential;
import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.Account;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.UsernamePasswordCredential;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OAuth服务端凭证信息到登录账号转换器
 */
@Component("oauthServerPrincipalResolver")
public class OAuthServerPrincipalResolver implements PrincipalResolver {
    @Autowired
    private AccountBiz accountBiz;
    private PrincipalFactory principalFactory = new DefaultPrincipalFactory();

    /**
     * 凭证信息转换
     */
    @Override
    public Principal resolve(Credential credential, Principal principal, AuthenticationHandler handler) {
        Account account = accountBiz.getByLoginName(credential.getId());
        return this.principalFactory.createPrincipal(account.getId());
    }

    @Override
    public IPersonAttributeDao getAttributeRepository() {
        return null;
    }

    @Override
    public boolean supports(Credential credential) {
        return (credential instanceof UsernamePasswordCredential) && !(credential instanceof AccountCredential);
    }
}
