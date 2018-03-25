package net.aulang.account.manage;

import net.aulang.account.document.OAuthCode;
import net.aulang.account.repository.OAuthCodeRepository;
import net.aulang.account.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Set;

@Service
public class OAuthCodeBiz {
    private int validitySeconds = 20;

    @Autowired
    private OAuthCodeRepository dao;

    public OAuthCode save(OAuthCode code) {
        return dao.save(code);
    }

    public void delete(String id) {
        dao.delete(id);
    }

    public OAuthCode findByCode(String code) {
        return dao.findByCode(code);
    }

    public OAuthCode consumeCode(String code) {
        OAuthCode authCode = findByCode(code);
        if (authCode == null) {
            return null;
        }
        delete(authCode.getId());
        return authCode;
    }

    public OAuthCode create(String clientId, Set<String> scope, String redirectUri, String accountId, boolean saved) {
        OAuthCode code = new OAuthCode();
        code.setCode(StrUtils.UUID());
        code.setRedirectUri(redirectUri);
        code.setAccountId(accountId);
        code.setClientId(clientId);
        code.setScope(scope);

        Calendar calendar = Calendar.getInstance();
        code.setCreationDate(calendar.getTime());
        calendar.add(Calendar.SECOND, validitySeconds);
        code.setExpirationDate(calendar.getTime());

        if (saved) {
            return save(code);
        } else {
            return code;
        }
    }
}
