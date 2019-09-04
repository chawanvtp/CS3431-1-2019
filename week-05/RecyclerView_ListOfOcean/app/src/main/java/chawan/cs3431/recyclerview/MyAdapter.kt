package chawan.cs3431.recyclerview

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.model.view.*

class MyAdapter(val items: Array<String>, val imageId: Array<Int>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.model, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val parent = p0 as ViewHolder
        parent?.getNameTxt?.text = items.get(p1)
        parent?.getThumbnail?.setImageResource(imageId.get(p1))
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
    val getNameTxt = view.nameTxt
    val getThumbnail = view.thumbnail

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK! ${v.nameTxt.text} : ${v.thumbnail.drawable}")
        val context = v.context

        val intent = Intent(context,DetailActivity::class.java)

        intent.putExtra("title",v.nameTxt.text)
//        intent.putExtra("th",lifeList[position].th)
//        intent.putExtra("en",lifeList[position].en)
//        intent.putExtra("image",v.thumbnail.resources as Int)

       context.startActivity(intent)
    }

    companion object {
        private val PHOTO_KEY = "PHOTO"
    }

}