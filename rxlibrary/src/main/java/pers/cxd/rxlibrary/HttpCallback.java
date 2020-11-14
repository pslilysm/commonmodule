package pers.cxd.rxlibrary;

import retrofit2.HttpException;

public abstract class HttpCallback<D> extends RxCallbackWrapper<D> {

    public HttpCallback() {
        super();
    }

    public HttpCallback(RxCallback<D> mBase) {
        super(mBase);
    }

    protected abstract void onNetworkError(Throwable e, String errMsg);

    protected boolean handleAnotherError(Throwable e){
        return true;
    }

    @Override
    public final boolean handleError(Throwable e) {
        String errorClassName = e.getClass().getName();
        if (e instanceof HttpException
                || errorClassName.startsWith("java.net")
                || errorClassName.startsWith("javax.net")){
            String errMsg = e.getMessage();
            if (e instanceof HttpException){
                // read the errMsg from server
                try {
                    errMsg = ((HttpException) e).response().errorBody().string();
                } catch (Exception ex) {
                    // Empty
                }
            }
            onNetworkError(e, errMsg);
            return true;
        }else {
            return handleAnotherError(e);
        }
    }

}
