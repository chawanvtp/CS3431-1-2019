package chawan.cs3431.moviesfinder

data class  MoviesList (
    val movies: Array<Movie>
)

data class Movie(
    val actor: String,
    val advance_ticket: String,
    val date_update: String,
    val director: String,
    val duration: Int,
    val genre: String,
    val id: Int,
    val is_hot: Boolean,
    val is_silver: Boolean,
    val movieCode: List<String>,
    val movie_cover: Any,
    val movie_image: Any,
    val now_showing: String,
    val poster_ori: String,
    val priority: String,
    val rating: String,
    val rating_id: Int,
    val release_date: String,
    val show_buyticket: String,
    val synopsis_en: String,
    val synopsis_th: String,
    val title_en: String,
    val title_th: String,
    val tr_hd: String,
    val tr_ios: String,
    val tr_mp4: String,
    val tr_sd: String,
    val trailer: String
)