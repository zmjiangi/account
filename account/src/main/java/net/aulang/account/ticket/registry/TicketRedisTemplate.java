package net.aulang.account.ticket.registry;

import net.aulang.account.ticket.serializer.KryoRedisSerializer;
import org.apereo.cas.ticket.Ticket;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class TicketRedisTemplate extends RedisTemplate<String, Ticket> {
    public TicketRedisTemplate() {
        KryoRedisSerializer kryo = new KryoRedisSerializer();
        RedisSerializer<String> string = new StringRedisSerializer();
        setKeySerializer(string);
        setValueSerializer(kryo);
        setHashKeySerializer(string);
        setHashValueSerializer(kryo);
    }

    public TicketRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        setConnectionFactory(connectionFactory);
        afterPropertiesSet();
    }
}
