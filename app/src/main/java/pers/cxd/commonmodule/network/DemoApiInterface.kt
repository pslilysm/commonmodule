package pers.cxd.commonmodule.network

import com.google.gson.JsonElement
import retrofit2.http.GET

interface DemoApiInterface {

    @GET("/api/v1/test/get")
    suspend fun testGet(): JsonElement
}