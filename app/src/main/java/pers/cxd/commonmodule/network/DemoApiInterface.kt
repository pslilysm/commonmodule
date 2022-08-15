package pers.cxd.commonmodule.network;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DemoApiInterface {

    @POST("api/v1/demo")
    Observable<Object> postSample(@Body Map map);

    @GET("api/v1/account/register")
    Observable<Void> register(@Query("accountName") String accountName,
                              @Query("password") String password);

}
