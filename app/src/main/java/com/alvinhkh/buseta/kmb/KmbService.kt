package com.alvinhkh.buseta.kmb

import com.alvinhkh.buseta.App.Companion.httpClient
import com.alvinhkh.buseta.kmb.model.KmbBBI2
import com.alvinhkh.buseta.kmb.model.KmbEtaRoutes
import com.alvinhkh.buseta.kmb.model.KmbRoutesInStop
import com.alvinhkh.buseta.kmb.model.network.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface KmbService {
    @GET("ajax/BBI/get_BBI2.php?&buscompany=null&jtSorting=sec_routeno%20ASC")
    fun bbi(@Query("routeno") routeno: String?, @Query("bound") bound: String?): Deferred<Response<KmbBBI2>>

    @GET("ajax/BBI/get_BBI2.php?&buscompany=null&jtSorting=sec_routeno%20ASC")
    fun bbi(@Query("routeno") routeno: String?, @Query("bound") bound: String?, @Query("interchangeType") interchangeType: String?): Deferred<Response<KmbBBI2>>

    @GET("FunctionRequest.ashx?action=getRouteBound")
    fun routeBound(@Query("route") route: String?): Call<KmbRouteBoundRes>

    @GET("FunctionRequest.ashx?action=getSpecialRoute")
    fun specialRoute(@Query("route") route: String?, @Query("bound") bound: String?): Call<KmbSpecialRouteRes>

    @GET("FunctionRequest.ashx?action=getStops")
    fun stops(@Query("route") route: String?, @Query("bound") bound: String?, @Query("serviceType") serviceType: String?): Call<KmbStopsRes>

    @GET("FunctionRequest.ashx?action=getRoutesInStop")
    fun routesInStop(@Query("bsiCode") bsiCode: String?): Call<KmbRoutesInStop>

    @GET("Function/FunctionRequest.ashx?action=getAnnounce")
    fun announce(@Query("route") route: String?, @Query("bound") bound: String?): Deferred<Response<KmbAnnounceRes>>

    @GET("Function/FunctionRequest.ashx?action=getschedule")
    fun schedule(@Query("route") route: String?, @Query("bound") bound: String?): Deferred<Response<KmbScheduleRes>>

    @GET("AnnouncementPicture.ashx")
    fun announcementPicture(@Query("url") url: String?): Deferred<Response<ResponseBody>>

    @GET("?action=geteta")
    fun eta(@Query("route") route: String?, @Query("bound") bound: String?,
            @Query("stop") stop: String?, @Query("stop_seq") stop_seq: String?,
            @Query("serviceType") serviceType: String?, @Query("lang") lang: String?,
            @Query("updated") updated: String?): Call<KmbEtaRes>

    @GET("GetData.ashx?type=ETA_R")
    fun etaRoutes(): Call<List<KmbEtaRoutes>>

    companion object {
        const val ANNOUNCEMENT_PICTURE = "http://search.kmb.hk/KMBWebSite/AnnouncementPicture.ashx?url="
        val gson: Gson = GsonBuilder()
                .serializeNulls()
                .create()
        val webCoroutine: Retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://www.kmb.hk/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .build()
        val webSearch: Retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://search.kmb.hk/KMBWebSite/Function/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val webSearchCoroutine: Retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://search.kmb.hk/KMBWebSite/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .build()
        val webSearchHtmlCoroutine: Retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://search.kmb.hk/KMBWebSite/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
                .build()
        val etav3: Retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://etav3.kmb.hk")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        val etadatafeed: Retrofit = Retrofit.Builder()
                .client(httpClient)
                .baseUrl("http://etadatafeed.kmb.hk:1933")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
}