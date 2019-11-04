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
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var sharedPreference:SharedPreference
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
        sharedPreference = SharedPreference(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var a = sharedPreference.getValueInt("animalLoaded")
        if(a !== null){
            animalLoaded = a
        }
        checkPermission()
        loadAnimals()

        autoBtn.setOnClickListener {
            autoBtnClicked()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
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

            runOnUiThread {

                if(isClear) {
                    mMap.clear()
                    isClear = false
                }

                var latLong = LatLng(p0!!.latitude, p0!!.longitude)
                latLong = setCurrentLocation(latLong)

                myMarkerOptions = MarkerOptions().position(latLong).title("user name").snippet("Zhuoyu")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.user,100,100)))

                myMarker = mMap.addMarker(myMarkerOptions)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 0F))

                var animalIndex = 0
                for (animal in animals) {

                    var latLong = LatLng(animal.lat, animal.long)
                    if (!animal.isCatch) {
                        animalsMarker.add(
                            mMap.addMarker(
                                MarkerOptions().position(latLong).title("${animal.name}-$animalIndex").snippet("animal's description")
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(animal.picture, 100, 100)))
                            )
                        )
                    }else{
                        animalsMarker.add(
                            mMap.addMarker(
                                MarkerOptions().position(latLong).title("${animal.name}-$animalIndex").snippet("animal's description")
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.heart, 100, 100)))
                            )
                        )
                    }

                        var animalLocation = Location("")
                        animalLocation.latitude = animal.lat
                        animalLocation.longitude = animal.long
                        if (location!!.distanceTo(animalLocation) < 10) {
                            animal.isCatch = true
                            Toast.makeText(applicationContext,
                                "You catch the ${animal.name}",Toast.LENGTH_LONG).show()
                        }

                    animalIndex += 1
                }
            }
//            autoWalk()
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

    private fun autoBtnClicked(){
            autoWalk()
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
            var name = sharedPreference.getValueString("$x-animal-name")
            var picture = sharedPreference.getValueInt("$x-animal-picture")
            var lat = sharedPreference.getValueString("$x-animal-lat")
            var long = sharedPreference.getValueString("$x-animal-long")
            var isCatch = sharedPreference.getValueBoolien("$x-animal-isCatch", false)

            if(name !== null && picture !== null && lat !== null && long !== null && isCatch !== null){
                animals.add(Animal(
                    name,
                    picture.toInt(),
                    lat.toDouble(),
                    long.toDouble(),
                    isCatch
                ))
                sharedPreference.save("$x-animal-isCatch",isCatch)
            }else {
                animals.add(
                    Animal(
                        lifes[randomIndex[x]],
                        images[randomIndex[x]],
                        randomLat[x],
                        randomLong[x]
                    ))
                sharedPreference.save("$x-animal-name",lifes[randomIndex[x]])
                sharedPreference.save("$x-animal-picture",images[randomIndex[x]])
                sharedPreference.save("$x-animal-lat","${randomLat[x]}")
                sharedPreference.save("$x-animal-long","${randomLong[x]}")
                sharedPreference.save("$x-animal-isCatch",false)
            }


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

        mMap.addMarker(MarkerOptions().position(latLong).title("user name").snippet("Zhuoyu")
            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.user,30,30))))

        for(x in 0..8){
            latLong = LatLng(latLong!!.latitude + newLoc!!.latitude, latLong!!.longitude + newLoc!!.longitude)
            currentLoc!!.latitude = latLong!!.latitude
            currentLoc!!.longitude = latLong!!.longitude
            setCurrentLocation(latLong)
            myMarker.position = latLong

            mMap.addMarker(MarkerOptions().position(latLong).title("user name").snippet("Zhuoyu")
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.user,30,30))))

            if (currentLoc!!.distanceTo(animalLocation) < 10) {

                animals[targetIndex].isCatch = true
                sharedPreference.save("$targetIndex-animal-isCatch",true)
                animalsMarker[targetIndex].setIcon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.heart,100,100)))
                Toast.makeText(applicationContext,
                    "You catch the ${animals[targetIndex].name}-$targetIndex",Toast.LENGTH_LONG).show()
                animalCatched += 1

                sharedPreference.save("$targetIndex-animal-isCatch",true)
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
        return nearestIndex
    }

    private fun getDistance(myLoc: Location?, targetLoc: Location?): Float{
        return myLoc!!.distanceTo(targetLoc)
    }

}