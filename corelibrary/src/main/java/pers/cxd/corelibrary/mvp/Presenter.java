package pers.cxd.corelibrary.mvp;

/**
 * Presenter is responsible for the interaction between the view and the model
 *
 * @author pslilysm
 * @since 1.0.0
 * @param <V> the view provide interface to touch ui
 * @param <M> the model provide data source to view
 */
public class Presenter<V, M> {

    protected final String TAG = "DEBUG_" + this.getClass().getSimpleName();
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
