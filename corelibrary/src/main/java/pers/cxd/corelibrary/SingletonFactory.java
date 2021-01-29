package pers.cxd.corelibrary;

import androidx.collection.ArrayMap;

import java.lang.reflect.Constructor;
import java.util.Map;

import pers.cxd.corelibrary.util.Pair;
import pers.cxd.corelibrary.util.reflection.ArgsKey;
import pers.cxd.corelibrary.util.reflection.ConstructorKey;

public class SingletonFactory {

    private static final Map<Pair<ConstructorKey, ArgsKey>, Object> sSingletonCache = new ArrayMap<>();

    public static <T> T findOrCreate(Class<T> clazz){
        return findOrCreate(clazz, null);
    }

    public static <T> T findOrCreate(Class<T> clazz, Class<?>[] parameterTypes, Object... args) {
        Pair<ConstructorKey, ArgsKey> pair = Pair.obtain(ConstructorKey.obtain(clazz, parameterTypes), ArgsKey.obtain(args));
        T singleton = (T) sSingletonCache.get(pair);
        if (singleton == null){
            synchronized (sSingletonCache){
                if ((singleton = (T) sSingletonCache.get(pair)) == null){
                    try {
                        Constructor<T> constructor = clazz.getDeclaredConstructor(parameterTypes);
                        constructor.setAccessible(true);
                        singleton = constructor.newInstance(args);
                        sSingletonCache.put(pair, singleton);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    pair.recycle();
                }
            }
        }else {
            pair.recycle();
        }
        return singleton;
    }

}
