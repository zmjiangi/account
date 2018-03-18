package net.aulang.account.repository;

import net.aulang.account.document.AccountToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTokenRepository extends MongoRepository<AccountToken, String> {

    AccountToken findByAccessToken(String accessToken);

    AccountToken findByRefreshToken(String refreshToken);

    AccountToken findByAccountIdAndClientId(String accountId, String clientId);

}
