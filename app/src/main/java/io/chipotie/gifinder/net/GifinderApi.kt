package io.chipotie.gifinder.net

import io.chipotie.gifinder.model.GifResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * @author savirdev on 25/03/19
 */

interface GifinderApi{

    @GET("trending")
    fun trending(@Query("api_key") apiKey: String, @Query("limit") limit: Int, @Query("offset") offset: Long): Call<GifResponse>

    @GET("search")
    fun search(@Query("api_key") apiKey: String, @Query("q") query: String, @Query("limit") limit: Int, @Query("offset") offset: Long): Call<GifResponse>
}