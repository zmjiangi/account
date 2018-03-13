package net.aulang.account.oauth.provider;

import net.aulang.account.document.OAuthClient;
import net.aulang.account.repository.OAuthClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class OAuthClientService implements ClientDetailsService {
    @Autowired
    private OAuthClientRepository dao;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuthClient client = dao.findByClientId(clientId);
        if (client == null) {
            throw new ClientRegistrationException("无效的客户端！");
        }
        return client;
    }
}
