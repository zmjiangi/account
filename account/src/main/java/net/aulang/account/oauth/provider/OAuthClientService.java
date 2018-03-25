package net.aulang.account.oauth.provider;

import net.aulang.account.document.OAuthClient;
import net.aulang.account.manage.OAuthClientBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

@Service
public class OAuthClientService implements ClientDetailsService {
    @Autowired
    private OAuthClientBiz clientBiz;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OAuthClient client = clientBiz.findByClientId(clientId);
        if (client == null) {
            throw new NoSuchClientException("无效的客户端！");
        }
        return client;
    }
}
