package chawan.cs3431.weather

import android.content.Context
import android.content.Intent
import android.database.Observable
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.weather_list_layout.view.*
import java.util.*

class MyAdapter(val items: WeatherPojo, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var arrImg = arrayOf<Int>(
        R.drawable.hr,
        R.drawable.lr,
        R.drawable.lc
    )

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.weather_list_layout, p0, false))
    }

    override fun getItemCount(): Int {
        return items.consolidated_weather.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val parent = p0 as ViewHolder
        parent?.getDateTv?.text = items.consolidated_weather[p1].applicable_date
        parent?.getStateTv?.text = items.consolidated_weather[p1].weather_state_name
        parent?.getPercentTv?.text = "${items.consolidated_weather[p1].predictability}%"
        parent?.getTempTv?.text = "Average: ${items.consolidated_weather[p1].the_temp}"
        parent?.getTempMinTv?.text = "Min: ${items.consolidated_weather[p1].min_temp}"
        parent?.getTempTvMax?.text = "Max: ${items.consolidated_weather[p1].max_temp}"
        parent?.getImageView?.setImageResource(getImageIdState(items.consolidated_weather[p1].weather_state_abbr))
    }

    private fun getImageIdState(state: String): Int {
        return when (state) {
            "hr" -> R.drawable.hr
            "lr" -> R.drawable.lr
            else -> R.drawable.lc
        }
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    val getDateTv = view.tvDate!!
    val getStateTv = view.tvState!!
    val getPercentTv = view.tvPercent!!
    val getTempTv = view.tvTemp!!
    val getTempMinTv = view.tvTempMin!!
    val getTempTvMax = view.tvTempMax!!
    val getImageView = view.imageView!!

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK! ${v.tvTemp.text} : ")
        val context = v.context

//        val intent = Intent(context,DetailActivity::class.java)

//        intent.putExtra("title",v.nameTxt.text)
//        intent.putExtra("th",lifeList[position].th)
//        intent.putExtra("en",lifeList[position].en)
//        intent.putExtra("image",v.thumbnail.resources as Int)

//        context.startActivity(intent)
    }


}