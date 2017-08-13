package net.aulang.account.repository;

import net.aulang.account.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    Account findByUsername(String username);

    Account findByEmail(String email);

    Account findByMobile(String mobile);

}
