package chawan.cs3431.moviesfinder

import io.reactivex.Observable
import retrofit2.http.GET

interface API {

    @GET("apis/get_movie_avaiable")
    fun getAllPosts(): Observable<MoviesList>
}