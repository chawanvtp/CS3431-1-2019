package chawan.cs3431.googlemap

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.SystemClock
import com.google.android.gms.maps.model.Marker


//        ----- SharedPreference -----
//        var sharedPreference:SharedPreference = SharedPreference(this)
//        sharedPreference.save("test","Chawan")
//        Toast.makeText(this@MapsActivity,"Data Stored - ${sharedPreference.getValueString("test")}", Toast.LENGTH_SHORT).show()


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
//    var sharedPreference:SharedPreference = SharedPreference(this)
    var animals = ArrayList<Animal>()
    var curLoc = LatLng(0.0,0.0)
    var isClear = true
    var animalLoaded = 0
    var animalCatched = 0
    var animalsMarker = ArrayList<Marker>()
    lateinit var myMarker: Marker
    lateinit var  myMarkerOptions: MarkerOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermission()

        loadAnimals()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
                return
            }
        }
        getUserLocation()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation()
            } else {
// Show the toast say "We cannot access to your location"
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @SuppressLint("MissingPermission")
    fun getUserLocation() {
        var myLocation = MyLocationListener()
        var locationManager = getSystemService(Context.LOCATION_SERVICE)
                as LocationManager
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,3,3f,myLocation)
    }

    var location: Location? = null
    inner class MyLocationListener: LocationListener {
        constructor() {
            location = Location("Start")
            location!!.latitude = 0.0
            location!!.longitude = 0.0
        }
        override fun onLocationChanged(p0: Location?) {
            location = p0
//            var latLong = LatLng(p0!!.latitude,p0!!.longitude)
//            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong,14f))
//            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong))
            runOnUiThread {

                if(isClear) {
                    mMap.clear()
                    isClear = false
                }

                var latLong = LatLng(p0!!.latitude, p0!!.longitude)
                latLong = setCurrentLocation(latLong)

                myMarkerOptions = MarkerOptions().position(latLong).title("user name").snippet("Zhuoyu")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.user,100,100)))

//                mMap.addMarker(
//                    MarkerOptions().position(latLong).title("user name").snippet("Zhuoyu")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.user)))
                myMarker = mMap.addMarker(myMarkerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 0F))

                var animalIndex = 0
                for (animal in animals) {
                    if (!animal.isCatch) {
                        var latLong = LatLng(animal.lat, animal.long)
//                        Log.d("RANDOM - ","$latLong")
//                        if(animalLoaded < 49){
                        animalsMarker.add( mMap.addMarker(
                            MarkerOptions().position(latLong).title("${animal.name}-$animalIndex").snippet("animal's description")
                                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(animal.picture,100,100)))
                        ))
//                        animalLoaded += 1 }
                        var animalLocation = Location("")
                        animalLocation.latitude = animal.lat
                        animalLocation.longitude = animal.long
                        if (location!!.distanceTo(animalLocation) < 10) {
                            animal.isCatch = true
                            Toast.makeText(applicationContext,
                                "You catch the ${animal.name}",Toast.LENGTH_LONG).show()
                        }
                    }
                    animalIndex += 1
                }
            }
            autoWalk()
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        }
        override fun onProviderEnabled(p0: String?) {
        }
        override fun onProviderDisabled(p0: String?) {
        }
    }

    fun resizeMapIcons(iconName: Int, width: Int, height: Int): Bitmap {
        val imageBitmap =
            BitmapFactory.decodeResource(resources, iconName)
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
    }

    fun resetAll(){

    }

    private fun loadAnimals() {
        var lifes = arrayOf(
            "Crab",
            "Octopus",
            "Star Fish",
            "Ancient Fish",
            "Cutie Fish",
            "Tuna",
            "Whale",
            "Sea Otter"
        )
        var images = arrayOf<Int>(
            R.drawable.crab,
            R.drawable.octopus,
            R.drawable.starfish,
            R.drawable.fish01,
            R.drawable.fish02,
            R.drawable.tuna,
            R.drawable.whale,
            R.drawable.seaotter
        )
        var animalToBeLoaded = 50 - animals.size

        val randomIndex = List(animalToBeLoaded) { Random.nextInt(0, 7) }
        val randomLat = List(animalToBeLoaded) { Random.nextDouble(0.0,120.00) }
        val randomLong = List(animalToBeLoaded) { Random.nextDouble(0.0,120.00) }
//        Log.d("Inner-animals.size : ","${randomLat}")

        for(x in 0 until animalToBeLoaded){
            animals.add(Animal(
                lifes[randomIndex[x]],
                images[randomIndex[x]],
                randomLat[x],
                randomLong[x]
                )
            )
//            Log.d("New-animals($x) : ","Lat:${randomLat[x]}, Long:${randomLong[x]} ")
        }
//        autoWalk()
    }

    private fun autoWalk(){
        var startLoc = Location("Start")
        startLoc!!.latitude = curLoc!!.latitude
        startLoc!!.longitude = curLoc!!.longitude
        var nearestIndex = findNearest(startLoc)
        var myLoc: LatLng = LatLng(startLoc!!.latitude, startLoc!!.longitude)
        var targetLoc: LatLng = LatLng(animals[nearestIndex].lat, animals[nearestIndex].long)
        var stepDistance = getStepDistance(myLoc, targetLoc)
        moveTo(stepDistance, myLoc, targetLoc, nearestIndex)
    }

    private fun getStepDistance(myLoc: LatLng, targetLoc: LatLng): LatLng{

        var lat = targetLoc!!.latitude - myLoc!!.latitude
        var long = targetLoc!!.longitude - myLoc!!.longitude
        return LatLng(lat/10, long/10)
    }

    fun setCurrentLocation(newLoc: LatLng): LatLng{
        curLoc = LatLng(newLoc!!.latitude, newLoc!!.longitude)
        return curLoc
    }

    private fun moveTo(moveDistance: LatLng,myLoc: LatLng, targetLoc: LatLng, targetIndex: Int){

        var newLoc = Location("Start")
        newLoc!!.latitude = moveDistance!!.latitude
        newLoc!!.longitude = moveDistance!!.longitude
        var latLong = LatLng(myLoc!!.latitude + newLoc!!.latitude, myLoc!!.longitude + newLoc!!.longitude)
        setCurrentLocation(latLong)

        var currentLoc = Location("Start")
        currentLoc!!.latitude = latLong!!.latitude
        currentLoc!!.longitude = latLong!!.longitude

        var animalLocation = Location("")
        animalLocation.latitude = animals[targetIndex].lat
        animalLocation.longitude = animals[targetIndex].long

//        myMarker.position = latLong
//            .title("user name").snippet("Zhuoyu")
//            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.user,100,100)))
        mMap.addMarker(MarkerOptions().position(latLong).title("user name").snippet("Zhuoyu")
            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.user,30,30))))

        for(x in 0..8){
            latLong = LatLng(latLong!!.latitude + newLoc!!.latitude, latLong!!.longitude + newLoc!!.longitude)
            currentLoc!!.latitude = latLong!!.latitude
            currentLoc!!.longitude = latLong!!.longitude
            setCurrentLocation(latLong)
            myMarker.position = latLong
//                MarkerOptions().position(latLong)
//                .title("user name").snippet("Zhuoyu-$x")
//                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.user,100,100)))
            mMap.addMarker(MarkerOptions().position(latLong).title("user name").snippet("Zhuoyu")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.user,30,30))))

            if (currentLoc!!.distanceTo(animalLocation) < 10) {

                animals[targetIndex].isCatch = true
                animalsMarker[targetIndex].setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.heart,100,100)))
                Toast.makeText(applicationContext,
                    "You catch the ${animals[targetIndex].name}-$targetIndex",Toast.LENGTH_LONG).show()
                animalCatched += 1
                if(animalCatched < 10){
                    return autoWalk()
                }
                return
            }

        }

    }

    private fun findNearest(myLoc: Location?): Int{
        var nearestIndex = 0
        var nearestDistance = 10000000000F
        var curIndex = 0
        for(animal in animals){
            var targetLoc = Location("")
            targetLoc!!.latitude = animals[curIndex].lat
            targetLoc!!.longitude = animals[curIndex].long
            var distance = getDistance(myLoc, targetLoc)
            if(distance < nearestDistance && !animal.isCatch){
                nearestIndex = curIndex
                nearestDistance = distance
            }
            curIndex += 1
        }
//        Log.d("nearestIndex: ","$nearestIndex")
        return nearestIndex
    }

    private fun getDistance(myLoc: Location?, targetLoc: Location?): Float{
        return myLoc!!.distanceTo(targetLoc)
    }

}