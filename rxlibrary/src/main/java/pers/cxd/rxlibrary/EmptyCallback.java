package pers.cxd.rxlibrary;

import pers.cxd.corelibrary.SingletonFactory;

public abstract class EmptyCallback implements RxCallback {

    public static EmptyCallback create(){
        return SingletonFactory.findOrCreate(EmptyCallback.class);
    }

}
