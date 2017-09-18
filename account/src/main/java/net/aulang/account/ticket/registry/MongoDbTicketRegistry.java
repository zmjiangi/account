package net.aulang.account.ticket.registry;

import net.aulang.account.ticket.serializer.KryoTicketSerializer;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.registry.AbstractTicketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MongoDbTicketRegistry extends AbstractTicketRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbTicketRegistry.class);

    private boolean dropCollection;
    private MongoTemplate mongoTemplate;

    public MongoDbTicketRegistry(MongoTemplate mongoTemplate) {
        this(mongoTemplate, false);
    }

    public MongoDbTicketRegistry(MongoTemplate mongoTemplate, boolean dropCollection) {
        this.dropCollection = dropCollection;
        this.mongoTemplate = mongoTemplate;
    }


    @PostConstruct
    public void initialize() {
        Assert.notNull(this.mongoTemplate, "MongoTemplate must not be null!");

        if (this.dropCollection) {
            this.mongoTemplate.dropCollection(TicketHolder.class);
        }

        if (!this.mongoTemplate.collectionExists(TicketHolder.class)) {
            this.mongoTemplate.createCollection(TicketHolder.class);
        }
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        try {
            TicketHolder holder = KryoTicketSerializer.serializeTicket(ticket);
            mongoTemplate.updateFirst(new Query(Criteria.where(TicketHolder.FIELD_TICKETID).is(holder.getTicketId())),
                    Update.update(TicketHolder.FIELD_DATA, holder.getData()), TicketHolder.class);
        } catch (Exception e) {
            LOGGER.error("Failed updating [{}]: [{}]", ticket, e);
        }
        return ticket;
    }

    @Override
    public void addTicket(Ticket ticket) {
        try {
            mongoTemplate.insert(KryoTicketSerializer.serializeTicket(ticket));
        } catch (Exception e) {
            LOGGER.error("Failed adding [{}]: [{}]", ticket, e);
        }
    }

    @Override
    public Ticket getTicket(String ticketId) {
        try {
            String encTicketId = encodeTicketId(ticketId);
            if (encTicketId == null) {
                LOGGER.error("Ticket ticketId [{}] could not be found", ticketId);
                return null;
            }
            TicketHolder holder = this.mongoTemplate.findOne(new Query(Criteria.where(TicketHolder.FIELD_TICKETID).is(encTicketId)),
                    TicketHolder.class);
            if (holder != null) {
                return KryoTicketSerializer.deserializeTicket(holder);
            }
        } catch (Exception e) {
            LOGGER.error("Failed fetching [{}]: [{}]", ticketId, e);
        }
        return null;
    }

    @Override
    public Collection<Ticket> getTickets() {
        List<TicketHolder> list = this.mongoTemplate.findAll(TicketHolder.class);
        return list.stream().map(KryoTicketSerializer::deserializeTicket).collect(Collectors.toSet());
    }

    @Override
    public long sessionCount() {
        return 0;
    }

    @Override
    public long serviceTicketCount() {
        return 0;
    }

    @Override
    public boolean deleteSingleTicket(String ticketId) {
        try {
            this.mongoTemplate.remove(new Query(Criteria.where(TicketHolder.FIELD_TICKETID).is(ticketId)), TicketHolder.class);
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed deleting [{}]: [{}]", ticketId, e);
        }
        return false;
    }

    @Override
    public long deleteAll() {
        Query query = new BasicQuery("{}");
        long count = this.mongoTemplate.count(query, TicketHolder.class);
        mongoTemplate.remove(query, TicketHolder.class);
        return count;
    }
}