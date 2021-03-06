package com.masterandroid.exam6.networking

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitHttp {
    val IS_TESTER = true
    val SERVER_DEVELOPMENT = "https://6232ae4e8364d63035c17e3a.mockapi.io/api/"
    val SERVER_PRODUCTION = "https://6232ae4e8364d63035c17e3a.mockapi.io/api/"

    private val client = getClient()
    private val retrofit = getRetrofit(client)

    fun getRetrofit(client: OkHttpClient):Retrofit{
        val build = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(SERVER_DEVELOPMENT)
            .client(client)
            .build()
        return build
    }

    fun <T> createService(service:Class<T>):T{
        return retrofit.create(service)
    }

    fun getClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        })
        .build()

    fun <T> createServiceWithAuth(service: Class<T>?): T {

        val newClient =
            client.newBuilder().addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    var request = chain.request()
                    val builder = request.newBuilder()
                    request = builder.build()
                    return chain.proceed(request)
                }
            }).build()
        val newRetrofit = retrofit.newBuilder().client(newClient).build()
        return newRetrofit.create(service!!)
    }

    val apiService: HomeService = retrofit.create(HomeService::class.java)

    //...

}