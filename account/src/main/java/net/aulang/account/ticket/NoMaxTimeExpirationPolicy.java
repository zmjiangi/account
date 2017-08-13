package net.aulang.account.ticket;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apereo.cas.ticket.TicketState;
import org.apereo.cas.ticket.support.AbstractCasExpirationPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class NoMaxTimeExpirationPolicy extends AbstractCasExpirationPolicy {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(NoMaxTimeExpirationPolicy.class);

    private long timeToKillInSeconds;

    public NoMaxTimeExpirationPolicy(@JsonProperty("timeToIdle") final long timeToKill) {
        this.timeToKillInSeconds = timeToKill;
    }

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

    @Override
    public Long getTimeToLive() {
        return 0L;
    }

    @Override
    public Long getTimeToIdle() {
        return this.timeToKillInSeconds;
    }
}
