package net.aulang.account.ticket.serializer;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class KryoRedisSerializer implements RedisSerializer<Object> {
    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        try (Output output = new Output(4096)) {
            Kryox.getInstance().writeClassAndObject(output, obj);
            return output.toBytes();
        } catch (Exception e) {
            throw new SerializationException(e.getMessage());
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        try (Input input = new Input(bytes)) {
            return Kryox.getInstance().readClassAndObject(input);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage());
        }
    }
}
