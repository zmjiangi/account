package net.aulang.account.ticket.serializer;

import com.esotericsoftware.kryo.Kryo;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

public class Kryox extends Kryo {
    private static final ReflectionFactory REFLECTION_FACTORY = ReflectionFactory.getReflectionFactory();
    private static final ConcurrentHashMap<Class<?>, Constructor<?>> CONSTRUCTORS = new ConcurrentHashMap<>();

    @Override
    public <T> T newInstance(Class<T> type) {
        try {
            return super.newInstance(type);
        } catch (Exception e) {
            return (T) newInstanceFromReflectionFactory(type);
        }
    }

    private Object newInstanceFrom(Constructor<?> constructor) {
        try {
            return constructor.newInstance();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T newInstanceFromReflectionFactory(Class<T> type) {
        Constructor<?> constructor = CONSTRUCTORS.get(type);
        if (constructor == null) {
            constructor = newConstructorForSerialization(type);
            Constructor<?> saved = CONSTRUCTORS.putIfAbsent(type, constructor);
            if (saved != null) {
                constructor = saved;
            }
        }
        return (T) newInstanceFrom(constructor);
    }

    private <T> Constructor<?> newConstructorForSerialization(
            Class<T> type) {
        try {
            Constructor<?> constructor = REFLECTION_FACTORY.newConstructorForSerialization(type,
                    Object.class.getDeclaredConstructor());
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
