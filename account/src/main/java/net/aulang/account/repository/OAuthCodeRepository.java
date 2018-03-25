package net.aulang.account.repository;

import net.aulang.account.document.OAuthCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthCodeRepository extends MongoRepository<OAuthCode, String> {

    OAuthCode findByCode(String code);

}
