package com.test.gambit.utils

import io.reactivex.Observable;
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class Webclient() {

    val apiService:WebAPiService;

    var baseUrl:String = "https://www.balldontlie.io/api/v1/"

    init {
        val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(UserAgentInterceptor())
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl).client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiService = retrofit.create(WebAPiService::class.java)
    }


    fun getService(): WebAPiService {
        return apiService;
    }

    public interface WebAPiService{

        @GET("players")
        fun getPlayerList(@Query("page") page:String, @Query("per_page") per_page:String, @Query("search") search:String) :Observable<PlayerModel>

        @GET("games")
        fun getGamesList(@Query("page") page:String, @Query("per_page") per_page:String, @Query("search") search:String) :Observable<GamesModel>

    }

    inner class UserAgentInterceptor : Interceptor {

        private val userAgent: String

        init {
            this.userAgent = System.getProperty("http.agent")
        }

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originRequest = chain.request()
            val requestWithUserAgent = originRequest.newBuilder()
                    .header("User-Agent", userAgent)
                    .build()
            return chain.proceed(requestWithUserAgent)
        }
    }


}