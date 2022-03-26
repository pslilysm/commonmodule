package pers.cxd.corelibrary;

/**
 * Lazy load singleton pattern
 *
 * @param <T> the type of the singleton instance
 * @author pslilysm
 * @since 1.0.0
 */
public abstract class Singleton<T> {

    volatile T instance;

    protected abstract T create();

    public T getInstance() {
        if (instance == null) {
            synchronized (this) {
                if (instance == null) {
                    instance = create();
                }
            }
        }
        return instance;
    }

}
