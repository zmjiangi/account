package net.aulang.account.ticket;

import org.apereo.cas.ticket.TicketState;
import org.apereo.cas.ticket.accesstoken.OAuthAccessTokenExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class NoMaxTimeToLiveExpirationPolicy extends OAuthAccessTokenExpirationPolicy {
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(NoMaxTimeToLiveExpirationPolicy.class);

    @Override
    public boolean isExpired(TicketState ticketState) {
        final ZonedDateTime currentSystemTime = ZonedDateTime.now(ZoneOffset.UTC);

        ZonedDateTime expirationTime = ticketState.getLastTimeUsed().plus(getTimeToLive(), ChronoUnit.SECONDS);
        if (currentSystemTime.isAfter(expirationTime)) {
            LOGGER.debug("Access token is expired because the time since last use is greater than timeToKillInSeconds");
            return true;
        }
        return false;
    }
}
