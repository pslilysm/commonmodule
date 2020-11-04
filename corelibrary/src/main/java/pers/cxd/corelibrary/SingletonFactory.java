package pers.cxd.corelibrary;

import androidx.collection.ArrayMap;

import java.lang.reflect.Constructor;
import java.util.Map;

import pers.cxd.corelibrary.util.reflection.ConstructorKey;
import pers.cxd.corelibrary.util.reflection.ReflectionUtil;

public class SingletonFactory {

    private static final Map<ConstructorKey, Object> sSingletonCache = new ArrayMap<>();

    public static <T> T findOrCreate(Class<T> clazz){
        return findOrCreate(clazz, null);
    }

    public static <T> T findOrCreate(Class<T> clazz, Class<?>[] parameterTypes, Object... args) {
        ConstructorKey constructorKey = ConstructorKey.obtain(clazz, parameterTypes);
        T singleton;
        synchronized (sSingletonCache){
            singleton = (T) sSingletonCache.get(constructorKey);
            if (singleton == null){
                try {
                    Constructor<T> constructor = clazz.getConstructor(parameterTypes);
                    constructor.setAccessible(true);
                    singleton = constructor.newInstance(args);
                    constructorKey.markInUse();
                    sSingletonCache.put(constructorKey, singleton);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }else {
                constructorKey.recycle();
            }
        }
        return singleton;
    }

}
