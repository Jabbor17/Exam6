package com.masterandroid.exam6.networking

import com.masterandroid.exam6.model.Card
import retrofit2.Call
import retrofit2.http.*

interface HomeService {


    @GET("card")
    fun listCards(): Call<ArrayList<Card>>


    @POST("card")
    fun addCard(@Body card: Card): Call<Card>





}