package chawan.cs3431.moviesfinder

import android.content.Context
import android.content.Intent
import android.database.Observable
//import android.support.v4.content.ContextCompat.startActivity
//import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.movie_layout.view.*
import java.util.*

class MyAdapter(val items: MoviesList, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var baseURL = "http://www.majorcineplex.com"

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_layout, p0, false))
    }

    override fun getItemCount(): Int {
        return items.movies.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val parent = p0 as ViewHolder

        parent?.getNameTv?.text = items.movies[p1].title_en
        parent?.getTypeTv?.text = items.movies[p1].genre
        parent?.getDateTv?.text = items.movies[p1].release_date
        parent?.getDesTv?.text = items.movies[p1].synopsis_en
        parent?.getUrl?.text = "${baseURL}${items.movies[p1].poster_ori}"
        parent?.getId?.text = "${items.movies[p1].id}"
        Picasso.with(context)
            .load("${baseURL}${items.movies[p1].poster_ori}")
            .placeholder(R.drawable.abc_ic_ab_back_material)
            .into(parent?.getImageView)
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    val getNameTv = view.tvName!!
    val getTypeTv = view.tvType!!
    val getDateTv = view.tvDate!!
    val getImageView = view.imageView!!
    val getDesTv = view.tvDescription!!
    val getUrl = view.tvImgUrl!!
    val getId = view.tvId!!

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK! ${v.tvName.text} : ")
        val context = v.context

        val intent = Intent(context,MovieInfoActivity::class.java)

        intent.putExtra("name",v.tvName.text)
        intent.putExtra("type",v.tvType.text)
        intent.putExtra("date",v.tvDate.text)
        intent.putExtra("description",v.tvDescription.text)
        intent.putExtra("imageUrl",v.tvImgUrl.text)

        context.startActivity(intent)
    }


}