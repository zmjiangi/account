package net.aulang.account.configuration;

import net.aulang.account.ticket.registry.RedisTicketRegistry;
import net.aulang.account.ticket.registry.TicketRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class TicketRegistryConfiguration {
    /**
     * Redis TicketRegistry
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TicketRedisTemplate ticketRedisTemplate() {
        return new TicketRedisTemplate(redisConnectionFactory);
    }

    @Bean
    public RedisTicketRegistry ticketRegistry() {
        return new RedisTicketRegistry(ticketRedisTemplate());
    }

    /**
     * MongoDB TicketRegistry
     */
    /*@Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public TicketRegistry ticketRegistry() throws Exception {
        return new MongoDbTicketRegistry(mongoTemplate);
    }

    @Bean
    public TicketRegistryCleaner ticketRegistryCleaner() {
        return new NoOpTicketRegistryCleaner();
    }*/
}
