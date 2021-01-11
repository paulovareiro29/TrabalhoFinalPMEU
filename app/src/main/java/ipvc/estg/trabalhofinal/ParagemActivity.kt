package ipvc.estg.trabalhofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.trabalhofinal.recycler.adapter.HorarioAdapter
import ipvc.estg.trabalhofinal.recycler.adapter.OnHorarioClickListener
import ipvc.estg.trabalhofinal.recycler.dataclass.Horario

class ParagemActivity : AppCompatActivity(), OnMapReadyCallback, OnHorarioClickListener {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paragem)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        /*teste*/

        var lista = ArrayList<Horario>()

        for (i in 0 until 20) {
            lista.add(Horario(i,"Porto - Viana do Castelo", "AV Minho", "7:45 | 8:15 | 8:40 | 9:30 | 10:55 | 13:00 | 14:40 | 16:10 | 18:00 | 19:00 | 19:40"))
        }

        findViewById<RecyclerView>(R.id.lista_horarios).adapter = HorarioAdapter(lista,this)
        findViewById<RecyclerView>(R.id.lista_horarios).layoutManager = LinearLayoutManager(this)

        /**/

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    /*quando clica num horario*/
    override fun onHorarioClickListener(horario: Horario) {
        startActivity(
            Intent(this,CarreiraActivity::class.java)
                .putExtra("id",horario.carreira_id)
        )
    }
}