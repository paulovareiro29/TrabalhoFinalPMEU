package ipvc.estg.trabalhofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.trabalhofinal.api.Endpoints
import ipvc.estg.trabalhofinal.api.ServiceBuilder
import ipvc.estg.trabalhofinal.api.models.Carreira
import ipvc.estg.trabalhofinal.recycler.adapter.HorarioAdapter
import ipvc.estg.trabalhofinal.recycler.adapter.ParagemAdapter
import ipvc.estg.trabalhofinal.recycler.dataclass.Horario
import ipvc.estg.trabalhofinal.recycler.dataclass.Paragem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarreiraActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var info: Carreira

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carreira)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val id = intent.getIntExtra("id", 0) //ID da paragem passado pelo intent
        if(id == 0){ //se o ID for 0, ou seja nao foi passado nenhum ID dá finish
            Toast.makeText(applicationContext,R.string.error_loading, Toast.LENGTH_LONG).show()
            finish()
        }


        fetchInfo(id)

    }

    fun fetchInfo(id: Int){
        val request = ServiceBuilder.buildService(Endpoints::class.java)
        val call = request.getCarreiraById(id)

        call.enqueue(object : Callback<Carreira> {
            override fun onResponse(call: Call<Carreira>, response: Response<Carreira>){
                if(response.isSuccessful){
                    info = response.body()!!
                    showInfo()
                }
            }

            override fun onFailure(call: Call<Carreira>, t: Throwable){
                Toast.makeText(applicationContext,R.string.error_loading, Toast.LENGTH_LONG).show()
                Log.d("ParagemActivity", "${t.message}")
                finish()
            }
        })
    }

    fun showInfo(){
        var tempo = (info.tempo_medio.toDouble())/60
        var hora = tempo.toInt()
        var min = ((tempo - hora)*60).toInt()

        var distancia = (info.distancia/1000)

        findViewById<TextView>(R.id.sentido).text = "${info.inicio} - ${info.fim}"
        findViewById<TextView>(R.id.tempo).text = "${hora}h${min}m"
        findViewById<TextView>(R.id.distancia).text = "${distancia}km"

        var lastMarkerPos: LatLng? = null

        var lista = ArrayList<Paragem>()
        for (paragem in info.paragens) {
            var horario = ""
            for(hora in paragem.horarios){
                horario += "| ${hora} "
            }

            lista.add(Paragem(paragem.id,
                    "${paragem.rua}",
                    "${paragem.cidade}",
                    "${paragem.cod_postal}"))

            lastMarkerPos = LatLng(paragem.latitude,paragem.longitude)
            mMap.addMarker(MarkerOptions().position(lastMarkerPos).title("${paragem.rua} - ${paragem.cidade}"))
        }


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastMarkerPos, 10f))

        findViewById<RecyclerView>(R.id.lista_paragens).adapter = ParagemAdapter(lista)
        findViewById<RecyclerView>(R.id.lista_paragens).layoutManager = LinearLayoutManager(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }

}