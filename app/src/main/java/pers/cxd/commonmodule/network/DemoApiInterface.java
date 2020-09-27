package pers.cxd.commonmodule.network;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DemoApiInterface {

    @POST("api/v1/demo")
    Observable<Object> postSample(@Body Map map);

}
