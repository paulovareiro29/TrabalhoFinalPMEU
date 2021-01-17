package ipvc.estg.trabalhofinal.api

import ipvc.estg.trabalhofinal.api.models.Direction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DirectionsAPIEndpoints {

    @GET("json")
    fun getDirection(@Query("origin") start_latlng: String,
                     @Query("destination") end_latlng: String,
                     @Query("key") key: String,
                     @Query("language") language: String
    ): Call<Direction>

}