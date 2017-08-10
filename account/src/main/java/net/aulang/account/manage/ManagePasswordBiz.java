package net.aulang.account.manage;

import net.aulang.account.authentication.AccountCredential;
import net.aulang.account.model.Account;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.pm.PasswordChangeBean;
import org.apereo.cas.pm.PasswordManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("passwordChangeService")
public class ManagePasswordBiz implements PasswordManagementService {
    @Autowired
    private AccountBiz accountBiz;

    @Override
    public boolean change(Credential credential, PasswordChangeBean bean) {
        try {
            if (credential instanceof AccountCredential) {
                AccountCredential accountCredential = (AccountCredential) credential;
                Account account = accountBiz.changePassword(accountCredential.getId(), bean.getPassword());
                if (account != null) {
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public String findEmail(String username) {
        Account account = accountBiz.getByLoginName(username);
        if (account != null) {
            return null;
        }
        return null;
    }

    @Override
    public String createToken(String username) {
        return null;
    }

    @Override
    public String parseToken(String token) {
        return null;
    }

    @Override
    public Map<String, String> getSecurityQuestions(String username) {
        Account account = accountBiz.getByLoginName(username);
        if (account != null) {
            return null;
        } else {
            return null;
        }
    }
}