package net.aulang.account.ticket.serializer;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class KryoRedisSerializer implements RedisSerializer<Object> {
    private static final ThreadLocal<Kryox> KRYO = ThreadLocal.withInitial(() -> {
        Kryox kryo = new Kryox();

        /**
         * 自定义设置
         */

        return kryo;
    });

    public static Kryox getKryo() {
        return KRYO.get();
    }

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        Output output = new Output(2048);
        getKryo().writeClassAndObject(output, obj);
        return output.toBytes();
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        Input input = new Input(bytes);
        return getKryo().readClassAndObject(input);
    }
}
