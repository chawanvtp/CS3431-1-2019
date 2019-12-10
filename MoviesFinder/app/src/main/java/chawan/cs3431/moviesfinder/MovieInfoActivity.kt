package chawan.cs3431.moviesfinder

//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_info.*
import android.content.Context

class MovieInfoActivity : AppCompatActivity() {

//    private var lifeList = ArrayList<Life>()
//    private val myImageList = intArrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_info)

        var name = intent.getStringExtra("name")
        var date = intent.getStringExtra("date")
        var type = intent.getStringExtra("type")
        var des = intent.getStringExtra("description")
        var imageUrl = intent.getStringExtra("imageUrl")

        movieInfoName.text = "$name"
        movieInfoDate.text = "$date"
        movieInfoType.text = "$type"
        movieInfoDescription.text = "$des"
        Picasso.with(this)
            .load("$imageUrl")
            .placeholder(R.drawable.abc_ic_ab_back_material)
            .into(movieInfoImg)
    }

    companion object {
        private const val TAG = "TAG"
    }
}

