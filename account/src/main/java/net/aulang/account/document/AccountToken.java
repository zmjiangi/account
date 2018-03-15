package net.aulang.account.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Set;

@Document
public class AccountToken implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Indexed(unique = true, sparse = true)
    private String accessToken;
    @Indexed(unique = true, sparse = true)
    private String refreshToken;

    private String clientId;
    private Set<String> scope;

    /**
     * 过期时间accessToken和refreshToken的不同
     */
}
