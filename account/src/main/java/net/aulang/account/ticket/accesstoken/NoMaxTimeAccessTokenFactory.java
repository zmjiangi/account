package net.aulang.account.ticket.accesstoken;

import org.apereo.cas.ticket.ExpirationPolicy;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.ticket.accesstoken.DefaultAccessTokenFactory;

public class NoMaxTimeAccessTokenFactory extends DefaultAccessTokenFactory {
    public NoMaxTimeAccessTokenFactory(ExpirationPolicy expirationPolicy) {
        super(expirationPolicy);
    }

    public NoMaxTimeAccessTokenFactory(UniqueTicketIdGenerator refreshTokenIdGenerator, ExpirationPolicy expirationPolicy) {
        super(refreshTokenIdGenerator, expirationPolicy);
    }
}
