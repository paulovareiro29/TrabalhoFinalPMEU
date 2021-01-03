package ipvc.estg.trabalhofinal

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap

    private var scanning: Boolean = false //Variavel de estado do scanner

    private lateinit var directionsPoint: LatLng  //Variavel do ponto das direçoes, definido quando o utilizador clica no mapa


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /*Quando se carrega no beacon scanner*/
    fun onBeaconScannerClick(v: View)  {
        scanning = !scanning

        val fab: FloatingActionButton = findViewById(R.id.fab_beacon_scanner)

        if(scanning){
            Toast.makeText(this,"${getString(R.string.searching)}...",Toast.LENGTH_SHORT).show()
            fab.setColorFilter(ContextCompat.getColor(this, R.color.red_200), android.graphics.PorterDuff.Mode.SRC_IN)
        }else{
            fab.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN)
        }
    }

    /*Listener aos clicks no mapa*/
    override fun onMapClick(point: LatLng) {
        mMap.clear()

        directionsPoint = point
        mMap.addMarker(MarkerOptions().position(directionsPoint))
        findViewById<RelativeLayout>(R.id.directions).visibility = View.VISIBLE
    }

    /*Botão de cancelar as direçoes*/
    fun cancelDirections(v: View){
        mMap.clear()
        findViewById<RelativeLayout>(R.id.directions).visibility = View.GONE
    }

    /*Botão de calcular direções*/
    fun goToDirections(v: View){
        Toast.makeText(this,"Calculando direções",Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener(this)
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


}