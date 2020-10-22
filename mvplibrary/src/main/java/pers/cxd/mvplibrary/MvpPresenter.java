package pers.cxd.mvplibrary;

public class MvpPresenter<V extends MvpView, M extends MvpModel> {

    protected V mView;
    protected M mModel;

    protected void attach(V v, M m){
        mView = v;
        mModel = m;
    }

    protected void detach(){
        mView = null;
        mModel = null;
    }

}
