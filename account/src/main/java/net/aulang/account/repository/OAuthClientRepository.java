package net.aulang.account.repository;

import net.aulang.account.document.OAuthClient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthClientRepository extends MongoRepository<OAuthClient, String> {

    OAuthClient findByClientId(String clientId);

}
