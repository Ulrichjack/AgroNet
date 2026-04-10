package cm.agronet.app

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize the map configuration BEFORE setContentView
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))

        setContentView(R.layout.activity_map)

        // 2. Setup the MapView
        map = findViewById(R.id.mapView)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        // 3. Center the map on Cameroon (Lat: 4.22, Lon: 11.6)
        val mapController = map.controller
        mapController.setZoom(6.5) // Zoom level to see the whole country
        val cameroonCenter = GeoPoint(4.22, 11.6)
        mapController.setCenter(cameroonCenter)

        // 4. Back Button
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // 5. Fetch Data from Firebase!
        loadAlertsFromFirebase()
    }

    private fun loadAlertsFromFirebase() {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "⚠️ Mode hors-ligne : Impossible de charger la carte. Vérifiez votre connexion.", Toast.LENGTH_LONG).show()
            return // Stop the function here so it doesn't crash!
        }

        Toast.makeText(this, "Loading alerts from cloud...", Toast.LENGTH_SHORT).show()

        // Read the "alerts" collection we created earlier
        db.collection("alerts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val lat = document.getDouble("latitude")
                    val lon = document.getDouble("longitude")
                    val disease = document.getString("disease") ?: "Unknown Disease"

                    // If GPS coordinates exist, put a marker on the map!
                    if (lat != null && lon != null) {
                        addMarkerToMap(lat, lon, disease)
                    }
                }
                Toast.makeText(this, "Loaded ${result.size()} alerts!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e("AgroNet", "Error loading map data: ${e.message}")
                Toast.makeText(this, "Failed to load alerts.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addMarkerToMap(lat: Double, lon: Double, title: String) {
        val marker = Marker(map)
        marker.position = GeoPoint(lat, lon)
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = title // Clicking the marker will show the disease name!

        map.overlays.add(marker)
        map.invalidate() // Refresh the map to show the new marker
    }

    // ─── Network Safety Check ────────────────────────────────
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    // Standard Android lifecycle handling for the map
    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}