package net.aulang.account.manage;

import net.aulang.account.document.OAuthClient;
import net.aulang.account.repository.OAuthClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthClientBiz {
    @Autowired
    private OAuthClientRepository dao;

    public OAuthClient findByClientId(String clientId) {
        return dao.findByClientId(clientId);
    }
}
