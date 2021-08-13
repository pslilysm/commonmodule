package pers.cxd.corelibrary;

public abstract class Singleton<T> {

    volatile T instance;

    protected abstract T create();

    public T getInstance(){
        if (instance == null){
            synchronized (this){
                if (instance == null){
                    instance = create();
                }
            }
        }
        return instance;
    }

}
