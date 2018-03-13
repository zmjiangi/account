package net.aulang.account.manage;

import net.aulang.account.model.Account;
import net.aulang.account.repository.AccountRepository;
import net.aulang.account.service.AccountService;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.LongValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountBiz implements AccountService {
    @Autowired
    private AccountRepository dao;
    private LongValidator longValidator = LongValidator.getInstance();
    private EmailValidator emailValidator = EmailValidator.getInstance();
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Account save(Account account) {
        return dao.save(account);
    }

    @Override
    public Account get(String id) {
        return dao.findById(id).get();
    }

    public Account getByLoginName(String loginName) {
        if (emailValidator.isValid(loginName)) {
            return dao.findByEmail(loginName);
        } else if (longValidator.isValid(loginName)) {
            return dao.findByMobile(loginName);
        } else {
            return dao.findByUsername(loginName);
        }
    }

    @Override
    public Account login(String loginName, String password) {
        Account account = getByLoginName(loginName);
        if (account != null) {
            if (passwordEncoder.matches(password, account.getPassword())) {
                return account;
            }
        }
        return null;
    }

    @Override
    public Account register(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return save(account);
    }

    public Account changePassword(String accountId, String password) {
        Account account = get(accountId);
        if (account != null) {
            account.setPassword(passwordEncoder.encode(password));
            return save(account);
        }
        return null;
    }

    public long count() {
        return dao.count();
    }
}
