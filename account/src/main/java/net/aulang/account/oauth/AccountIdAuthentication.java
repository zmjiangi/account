package net.aulang.account.oauth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class AccountIdAuthentication implements Authentication {
    private String accountId;

    public AccountIdAuthentication(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getCredentials() {
        return accountId;
    }

    @Override
    public Object getDetails() {
        return accountId;
    }

    @Override
    public Object getPrincipal() {
        return accountId;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {

    }

    @Override
    public String getName() {
        return accountId;
    }
}
