package ipvc.estg.trabalhofinal.api

import ipvc.estg.trabalhofinal.api.models.Carreira
import ipvc.estg.trabalhofinal.api.models.Paragem
import retrofit2.Call
import retrofit2.http.*

interface Endpoints {

    @GET("/paragem/{id}")
    fun getParagemById(@Path("id") id: Int): Call<Paragem>

    @GET("/carreira/{id}")
    fun getCarreiraById(@Path("id") id: Int): Call<Carreira>
}