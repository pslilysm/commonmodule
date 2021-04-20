//package pers.cxd.rxlibrary;
//
//import io.reactivex.rxjava3.annotations.NonNull;
//import io.reactivex.rxjava3.core.Observer;
//import retrofit2.HttpException;
//
//public abstract class HttpObserverWrapper<D> extends ObserverWrapper<D> {
//
//    public HttpObserverWrapper(Observer<D> mBase) {
//        super(mBase);
//    }
//
//    public abstract void onNetworkError(Throwable e, String errMsg);
//
//    @Override
//    public void onError(@NonNull Throwable e) {
//        String errorClassName = e.getClass().getName();
//        if (e instanceof HttpException
//                || errorClassName.startsWith("java.net")
//                || errorClassName.startsWith("javax.net")) {
//            String errMsg = e.getMessage();
//            if (e instanceof HttpException) {
//                // read the errMsg from server
//                try {
//                    errMsg = ((HttpException) e).response().errorBody().string();
//                } catch (Exception ex) {
//                    // Empty
//                }
//            }
//            onNetworkError(e, errMsg);
//            // retrofit will send a error event but not send complete event
//            onComplete();
//        }else {
//            super.onError(e);
//        }
//    }
//
//}
