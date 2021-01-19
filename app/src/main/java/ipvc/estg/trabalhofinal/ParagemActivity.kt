package ipvc.estg.trabalhofinal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
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
import ipvc.estg.trabalhofinal.api.models.Paragem
import ipvc.estg.trabalhofinal.recycler.adapter.HorarioAdapter
import ipvc.estg.trabalhofinal.recycler.adapter.OnHorarioClickListener
import ipvc.estg.trabalhofinal.recycler.dataclass.Horario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParagemActivity : AppCompatActivity(), OnMapReadyCallback, OnHorarioClickListener {

    private lateinit var mMap: GoogleMap

    private lateinit var info: Paragem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paragem)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        val id = intent.getIntExtra("id", 0) //ID da paragem passado pelo intent
        Log.d("debugg", "ID: ${id}")
        if(id == 0){ //se o ID for 0, ou seja nao foi passado nenhum ID dá finish
            Toast.makeText(applicationContext,R.string.error_loading, Toast.LENGTH_LONG).show()
            finish()
        }



        fetchInfo(id)

    }

    fun fetchInfo(id: Int){
        val request = ServiceBuilder.buildService(Endpoints::class.java)
        val call = request.getParagemById(id)

        call.enqueue(object : Callback<Paragem>{
            override fun onResponse(call: Call<Paragem>, response: Response<Paragem>){
                if(response.isSuccessful){
                    info = response.body()!!
                    showInfo()
                }
            }

            override fun onFailure(call: Call<Paragem>, t: Throwable){
                Toast.makeText(applicationContext,R.string.error_loading, Toast.LENGTH_LONG).show()
                Log.d("ParagemActivity", "${t.message}")
                finish()
            }
        })
    }

    fun showInfo(){
        findViewById<TextView>(R.id.rua_paragem).text = info.rua
        findViewById<TextView>(R.id.cidade_paragem).text = info.cidade
        findViewById<TextView>(R.id.codpostal_paragem).text = info.cod_postal


        var lista = ArrayList<Horario>()
        for (carreira in info.carreiras) {
            var horario = ""
            for(hora in carreira.horarios){
                horario += "| ${hora} "
            }

            lista.add(Horario(carreira.id,
                    "${carreira.inicio} - ${carreira.fim}",
                    "${carreira.empresa}",
                    "${horario}|"))
        }

        findViewById<RecyclerView>(R.id.lista_horarios).adapter = HorarioAdapter(lista,this)
        findViewById<RecyclerView>(R.id.lista_horarios).layoutManager = LinearLayoutManager(this)

        var loc = LatLng(info.latitude,info.longitude)
        mMap.addMarker(MarkerOptions().position(loc).title(info.rua))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16f))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

    }


    /*quando clica num horario*/
    override fun onHorarioClickListener(horario: Horario) {
        startActivity(
            Intent(this,CarreiraActivity::class.java)
                .putExtra("id",horario.carreira_id)
        )
    }
}