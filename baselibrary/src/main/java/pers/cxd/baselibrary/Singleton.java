package pers.cxd.baselibrary;

public abstract class Singleton<T> {

    T instance;

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
