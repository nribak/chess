package org.ribak.chesssdk.utils;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer;

import java.util.ArrayList;

/**
 * Created by nribak on 26/02/2017.
 */

public class ObjectUtils {
    private static Kryo kryo;
    static {
        kryo = new Kryo();
        kryo.setDefaultSerializer(CompatibleFieldSerializer.class);
    }
    public static <T> T copy(T object) {
        return kryo.copy(object);
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>() {
            @Override
            public boolean add(T t) {
                return t != null && super.add(t);
            }
        };
    }
}
