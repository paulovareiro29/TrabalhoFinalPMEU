package ipvc.estg.trabalhofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.trabalhofinal.recycler.adapter.HorarioAdapter
import ipvc.estg.trabalhofinal.recycler.adapter.ParagemAdapter
import ipvc.estg.trabalhofinal.recycler.dataclass.Horario
import ipvc.estg.trabalhofinal.recycler.dataclass.Paragem

class CarreiraActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carreira)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        /*teste*/

        var lista = ArrayList<Paragem>()

        for (i in 0 until 20) {
            lista.add(Paragem(i,"Praça do Almada", "Povoa de Varzim", "4480-438"))
        }

        findViewById<RecyclerView>(R.id.lista_paragens).adapter = ParagemAdapter(lista)
        findViewById<RecyclerView>(R.id.lista_paragens).layoutManager = LinearLayoutManager(this)

        /**/

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}