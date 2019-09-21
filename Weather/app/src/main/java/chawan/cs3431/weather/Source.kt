package chawan.cs3431.weather

data class Source(
    val crawl_rate: Int,
    val slug: String,
    val title: String,
    val url: String
)