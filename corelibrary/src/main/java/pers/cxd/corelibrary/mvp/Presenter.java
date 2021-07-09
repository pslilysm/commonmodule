package pers.cxd.corelibrary.mvp;

public class Presenter<V, M> {

    protected final String TAG = "DEBUG_CXD_" + this.getClass().getSimpleName();
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

    protected boolean notDetach(){
        return mView != null && mModel != null;
    }

}
