package net.aulang.account.authentication;

import net.aulang.account.document.Account;
import net.aulang.account.manage.AccountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AccountAuthProvider implements AuthenticationProvider {
    @Autowired
    private AccountBiz accountBiz;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePassword = (UsernamePasswordAuthenticationToken) authentication;
        String username = usernamePassword.getPrincipal().toString();
        String password = usernamePassword.getCredentials().toString();

        Account account;
        try {
            account = accountBiz.login(username, password);
        } catch (Exception e) {
            throw new AuthenticationServiceException("认证服务异常", e);
        }

        if (account == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        return new AccountIdAuthentication(account.getId());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
