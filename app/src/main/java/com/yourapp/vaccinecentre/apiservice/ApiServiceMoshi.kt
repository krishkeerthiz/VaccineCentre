package com.yourapp.vaccinecentre.apiservice

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.yourapp.vaccinecentre.schemamodel.SessionsModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://cdn-api.co-vin.in/api/"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ApiServiceMoshi{

    @GET("v2/appointment/sessions/public/findByPin?")
    suspend fun getCentres(@Query("pincode") pincode : String, @Query("date") date : String) : SessionsModel

}

object SessionsApi{
    val service : ApiServiceMoshi by lazy{ retrofit.create(ApiServiceMoshi::class.java)}
}
