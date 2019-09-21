package chawan.cs3431.weather

import io.reactivex.Observable
import retrofit2.http.GET

interface API {

    @GET("api/location/1225448/")
    fun getAllPosts(): Observable<WeatherPojo>
}