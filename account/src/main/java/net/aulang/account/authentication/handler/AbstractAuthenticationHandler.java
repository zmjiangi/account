package net.aulang.account.authentication.handler;

import org.apereo.cas.authentication.*;
import org.apereo.cas.authentication.principal.Principal;

public abstract class AbstractAuthenticationHandler implements AuthenticationHandler {
    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public boolean supports(Credential credential) {
        return credential instanceof UsernamePasswordCredential;
    }

    protected HandlerResult createHandlerResult(final Credential credential, final Principal principal) {
        return new DefaultHandlerResult(this, new BasicCredentialMetaData(credential), principal, null);
    }
}
