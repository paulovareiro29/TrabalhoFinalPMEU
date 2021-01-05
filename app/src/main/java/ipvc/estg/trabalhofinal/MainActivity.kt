package ipvc.estg.trabalhofinal

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
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

    private var popupInfo: PopupWindow? = null //popup da informaçao

    private lateinit var directionsPoint: LatLng  //Variavel do ponto das direçoes, definido quando o utilizador clica no mapa


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /*Quando se carrega no beacon scanner*/
    fun onBeaconScannerClick(v: View?)  {
        scanning = !scanning

        val fab: FloatingActionButton = findViewById(R.id.fab_beacon_scanner)

        if(scanning){
            Toast.makeText(this,"${getString(R.string.searching)}...",Toast.LENGTH_SHORT).show()
            fab.setColorFilter(ContextCompat.getColor(this, R.color.red_200), android.graphics.PorterDuff.Mode.SRC_IN)
            showInfoPopup()
        }else{
            dismissPopup()
            fab.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN)
        }
    }

    fun dismissPopup(){
        if(popupInfo != null){
            popupInfo!!.dismiss()
            popupInfo = null
        }
    }

    fun showInfoPopup(){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popup = inflater.inflate(R.layout.popup_info, null)

        val width: Int = LinearLayout.LayoutParams.MATCH_PARENT
        val height: Int = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = false

        val popupWindow = PopupWindow(popup, width, height, focusable)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10F
        }

        popupWindow.showAtLocation(popup, Gravity.TOP, 20, 0)

        popupInfo = popupWindow //associa o popupwindow local ao popupinfo global

        popup.findViewById<Button>(R.id.cancelInfoPopupButton).setOnClickListener {
            onBeaconScannerClick(null)
            dismissPopup()
        }

        popup.findViewById<Button>(R.id.goToInfoButton).setOnClickListener {
            Toast.makeText(this,"Going...",Toast.LENGTH_SHORT).show()
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
    fun cancelDirections(v: View?){
        mMap.clear()
        findViewById<RelativeLayout>(R.id.directions).visibility = View.GONE
    }

    /*Botão de calcular direções*/
    fun goToDirections(v: View?){
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