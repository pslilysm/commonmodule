package pers.cxd.commonmodule.presenters;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import pers.cxd.corelibrary.mvp.Presenter;

public class RxPresenter<V, M> extends Presenter<V, M> {

    protected CompositeDisposable mSubscription;

    @Override
    public void attach(V v, M m) {
        super.attach(v, m);
        mSubscription = new CompositeDisposable();
    }

    @Override
    public void detach() {
        super.detach();
        if (mSubscription != null){
            mSubscription.dispose();
        }
        mSubscription = null;
    }

    @Override
    public boolean notDetach() {
        return super.notDetach() && mSubscription != null;
    }

}
