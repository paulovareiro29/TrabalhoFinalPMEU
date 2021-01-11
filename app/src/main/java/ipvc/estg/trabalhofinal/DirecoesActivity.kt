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
import ipvc.estg.trabalhofinal.recycler.adapter.DirecaoAdapter
import ipvc.estg.trabalhofinal.recycler.dataclass.Direcao

class DirecoesActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direcoes)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        /*teste*/
        var lista = ArrayList<Direcao>()

        for (i in 0 until 20) {
            lista.add(Direcao("Siga por ali a esquerda bota que tem"))
        }

        findViewById<RecyclerView>(R.id.lista_direcoes).adapter = DirecaoAdapter(lista)
        findViewById<RecyclerView>(R.id.lista_direcoes).layoutManager = LinearLayoutManager(this)

        /**/
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}