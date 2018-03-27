package net.aulang.account.authentication;

import net.aulang.account.document.Account;
import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.UserInfo;
import net.aulang.account.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AccountDetailsService implements UserDetailsService, UserInfoService {
    @Autowired
    private AccountBiz accountBiz;

    @Override
    public UserInfo get(String accountId) {
        Account account = accountBiz.get(accountId);
        if (account == null) {
            return null;
        }

        return new UserInfo(account.getId(), account.getNickname());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("用户名为空");
        }
        Account account = accountBiz.get(username);
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(username, "N/A", Collections.EMPTY_LIST);
    }
}
