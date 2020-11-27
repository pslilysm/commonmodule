package pers.cxd.commonmodule.presenters;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import pers.cxd.mvplibrary.Presenter;
import pers.cxd.rxlibrary.RxCallback;
import pers.cxd.rxlibrary.RxCallbackWrapper;

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