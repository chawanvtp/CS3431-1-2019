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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {

    var faculties = ArrayList<Faculty>()
    var gson = Gson()
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycle_view)
        loadFaculty()
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

        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerView!!.setLayoutManager(GridLayoutManager(this, 1))
        val myAdapter = MyAdapter(faculties,this)
        recyclerView!!.setAdapter(myAdapter)

    }
}
