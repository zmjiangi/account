package net.aulang.account.authentication.principal;

import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.springframework.stereotype.Component;

/**
 * 凭证信息到登录账号转换器
 */
@Component("defaultPrincipalResolver")
public class DefaultPrincipalResolver implements PrincipalResolver {
    @Override
    public Principal resolve(Credential credential, Principal principal, AuthenticationHandler handler) {
        return principal;
    }

    @Override
    public IPersonAttributeDao getAttributeRepository() {
        return null;
    }

    @Override
    public boolean supports(Credential credential) {
        return true;
    }
}
