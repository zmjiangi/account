package net.aulang.account.authentication.principal;

import net.aulang.account.authentication.AccountCredential;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.PrincipalResolver;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.springframework.stereotype.Component;

/**
 * 凭证信息到登录账号转换器
 */
@Component("accountPrincipalResolver")
public class AccountPrincipalResolver implements PrincipalResolver {
	private PrincipalFactory principalFactory = new DefaultPrincipalFactory();

	/**
	 * 凭证信息转换
	 */
	@Override
	public Principal resolve(Credential credential, Principal principal, AuthenticationHandler handler) {
		return principalFactory.createPrincipal(credential.getId());
	}

	@Override
	public IPersonAttributeDao getAttributeRepository() {
		return null;
	}

	@Override
	public boolean supports(Credential credential) {
		return credential instanceof AccountCredential;
	}
}
