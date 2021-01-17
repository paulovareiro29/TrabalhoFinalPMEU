package ipvc.estg.trabalhofinal.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DirectionsAPIServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/directions/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

}