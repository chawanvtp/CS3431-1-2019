package chawan.cs3431.aumap

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.stream.JsonReader
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var faculties = ArrayList<Faculty>()
    var facultiesMarker = ArrayList<Marker>()
    lateinit var myMarker: Marker
    lateinit var  myMarkerOptions: MarkerOptions
    var gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        loadFaculty()

        switchBtn.setOnClickListener({
            val context = it.context
            val intent = Intent(context,ListActivity::class.java)
            context.startActivity(intent)
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val client = LatLng(13.612320, 100.836808)
        val clientLoc = Location("Start")
        clientLoc!!.latitude = client!!.latitude
        clientLoc!!.longitude = client!!.longitude

        mMap.addMarker(MarkerOptions().position(client).title("Client"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(client,18F))
        for (faculty in faculties) {
            var latLong = LatLng(faculty.LocationLat, faculty.LocationLong)
            var facultyLoc = Location("Start")
            facultyLoc!!.latitude = latLong!!.latitude
            facultyLoc!!.longitude = latLong!!.longitude

            facultiesMarker.add(
                mMap.addMarker(
                    MarkerOptions().position(latLong).title("${faculty.FacultyName}")
                        .snippet("${faculty.Abbreviation} (${getDistance(clientLoc,facultyLoc)} m.) - Lat: ${faculty.LocationLat} | Lng:${faculty.LocationLong}")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(getImage(faculty.ImageLogoName), 30, 30)))
                )
            )
        }
    }

    private fun getImage(name: String): Int{
        when (name) {
            "vms_logo.png" -> return R.drawable.vms_logo
            "vme_logo.png" -> return R.drawable.vme_logo
            "ca_logo.png" -> return R.drawable.ca_logo
            "bs_logo.png" -> return R.drawable.bs_logo
            "ar_logo.png" -> return R.drawable.ar_logo
            "law_logo.png" -> return R.drawable.law_logo
            "ms_logo.png" -> return R.drawable.ms_logo
            "msme_logo.png" -> return R.drawable.msme_logo
            "ns_logo.png" -> return R.drawable.ns_logo
            "arts_logo.png" -> return R.drawable.arts_logo
        }
        return 0
    }

    private fun getDistance(myLoc: Location?, targetLoc: Location?): Float{
        return myLoc!!.distanceTo(targetLoc)
    }

//    var location: Location? = null
//    inner class MyLocationListener: LocationListener {
//        constructor() {
//            location = Location("Start")
//            location!!.latitude = 13.612320
//            location!!.longitude = 100.836808
//        }
//        override fun onLocationChanged(p0: Location?) {
//            location = p0
//
//            runOnUiThread {
//
//
//                var latLong = LatLng(p0!!.latitude, p0!!.longitude)
//
//                myMarkerOptions = MarkerOptions().position(latLong).title("user name").snippet("Zhuoyu")
//                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.arts_logo,100,100)))
//
//                myMarker = mMap.addMarker(myMarkerOptions)
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 0F))
//
//                var animalIndex = 0
//                for (faculty in faculties) {
//
//                    var latLong = LatLng(faculty.LocationLat, faculty.LocationLong)
////                    if (!faculty.isCatch) {
//                        facultiesMarker.add(
//                            mMap.addMarker(
//                                MarkerOptions().position(latLong).title("${faculty.FacultyName}").snippet("${faculty.Abbreviation}")
//                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.ar_logo, 100, 100)))
//                            )
//                        )
//                }
//            }
//        }
//
//        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
//        }
//        override fun onProviderEnabled(p0: String?) {
//        }
//        override fun onProviderDisabled(p0: String?) {
//        }
//    }

    private fun resizeMapIcons(iconName: Int, width: Int, height: Int): Bitmap {
        val imageBitmap =
            BitmapFactory.decodeResource(resources, iconName)
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
    }

    private fun loadFaculty(){
        faculties.add(Faculty(
                "Vincent Mary School of Science and Technology",
                "VMS",
                "vms_logo.png",
                13.613239,
                100.835531
        ))
        faculties.add(Faculty(
                "Vincent Mary School of Engineering",
                "VME",
                "vme_logo.png",
                13.613145,
                100.835811
        ))
        faculties.add(Faculty(
                "Albert Laurence School of Communication Arts",
                "CA",
                "ca_logo.png",
                13.612227,
                100.835039
        ))
        faculties.add(Faculty(
                "School of Biotechnology",
                "BS",
                "bs_logo.png",
                13.612766,
            100.840549
        ))
        faculties.add(Faculty(
                "Montfort Del Rosario School of Architecture and Design",
                "AR",
                "ar_logo.png",
                13.612125,
                100.835519
            ))
        faculties.add(Faculty(
                 "School of Law",
            "LAW",
                "law_logo.png",
                13.611869,
                100.837477
        ))
        faculties.add(Faculty(
                 "School of Music",
                 "MS",
                 "ms_logo.png",
                13.612264,
                100.837577
        ))
        faculties.add(Faculty(
                 "Martin De Tours School of Management and Economics",
                "MSME",
                "msme_logo.png",
                13.612958,
                 100.836499
        ))
        faculties.add(Faculty(
                "Faculty of Nursing Science",
                "NS",
                "ns_logo.png",
                13.612219,
                100.838038
            ))
        faculties.add(Faculty(
                "Theodore Maria School of Arts",
                "ARTS",
                "arts_logo.png",
                13.611520,
                100.837211
        ))

    }
}
