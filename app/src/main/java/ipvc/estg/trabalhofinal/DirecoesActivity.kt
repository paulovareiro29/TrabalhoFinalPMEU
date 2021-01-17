package ipvc.estg.trabalhofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import ipvc.estg.trabalhofinal.api.DirectionsAPIEndpoints
import ipvc.estg.trabalhofinal.api.DirectionsAPIServiceBuilder
import ipvc.estg.trabalhofinal.api.models.Direction
import ipvc.estg.trabalhofinal.api.models.Leg
import ipvc.estg.trabalhofinal.api.models.Paragem
import ipvc.estg.trabalhofinal.recycler.adapter.DirecaoAdapter
import ipvc.estg.trabalhofinal.recycler.adapter.ParagemAdapter
import ipvc.estg.trabalhofinal.recycler.dataclass.Direcao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class DirecoesActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var start_latlng: LatLng
    private lateinit var end_latlng: LatLng

    private lateinit var rota: Leg
    private lateinit var polylinePoints: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direcoes)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        start_latlng = LatLng(intent.getDoubleExtra("start_latitude", 0.0),intent.getDoubleExtra("start_longitude",0.0))
        end_latlng = LatLng(intent.getDoubleExtra("end_latitude", 0.0),intent.getDoubleExtra("end_longitude",0.0))

        fetchDirection()
    }

    fun fetchDirection(){
        val request = DirectionsAPIServiceBuilder.buildService(DirectionsAPIEndpoints::class.java)
        val call = request.getDirection("${start_latlng.latitude},${start_latlng.longitude}", "${end_latlng.latitude},${end_latlng.longitude}", "AIzaSyCfOqcfH-NgB3LQA6nFOBMqz9mbKz7ByKM", Locale.getDefault().language)

        call.enqueue(object : Callback<Direction> {
            override fun onResponse(call: Call<Direction>, response: Response<Direction>){
                if(response.isSuccessful){
                    rota = response.body()!!.routes[0].legs[0]
                    polylinePoints = response.body()!!.routes[0].overview_polyline.points

                    showInfo()
                }
            }

            override fun onFailure(call: Call<Direction>, t: Throwable){
                Toast.makeText(applicationContext,R.string.error_loading, Toast.LENGTH_LONG).show()
                Log.d("DirectionsAPI", "${t.message}")
            }
        })
    }

    fun showInfo() {
        var tempo = (rota.duration.value.toDouble())/(60*60)
        var hora = tempo.toInt()
        var min = ((tempo - hora)*60).toInt()

        findViewById<TextView>(R.id.morada).text = rota.end_address
        if(hora > 0){
            findViewById<TextView>(R.id.tempo).text = "${hora}h${min}m"
        }else{
            findViewById<TextView>(R.id.tempo).text = "${min}m"
        }

        findViewById<TextView>(R.id.distancia).text = "${rota.distance.text}"

        mMap.addMarker(MarkerOptions().position(start_latlng)
                .title("Origem"))

        mMap.addMarker(MarkerOptions().position(end_latlng)
                .title("Destino")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(end_latlng,15f))

        var decodedPath: List<LatLng> = PolyUtil.decode(polylinePoints)

        mMap.addPolyline(PolylineOptions().addAll(decodedPath))

        var lista = ArrayList<Direcao>()
        for(step in rota.steps){
            lista.add(Direcao(Html.fromHtml(step.html_instructions).toString()))
        }

        findViewById<RecyclerView>(R.id.lista_direcoes).adapter = DirecaoAdapter(lista)
        findViewById<RecyclerView>(R.id.lista_direcoes).layoutManager = LinearLayoutManager(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }
}