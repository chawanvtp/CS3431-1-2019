package chawan.cs3431.listofoceanlife

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.listview_row.view.*

class CustomAdapter : BaseAdapter {
    var lifesList = ArrayList<Life>()
    var context: Context? = null
    private val myImageList = intArrayOf(R.drawable.octopus, R.drawable.crab, R.drawable.sea_otter, R.drawable.sea_turtle, R.drawable.shark, R.drawable.killer_whale)

    constructor(context: Context, courseList: ArrayList<Life>) : super() {
        this.context = context
        this.lifesList = courseList
    }

    override fun getCount(): Int {
        return lifesList.size
    }

    override fun getItem(position: Int): Any {
        return lifesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val life = this.lifesList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rowView = inflator.inflate(R.layout.listview_row, null)
        rowView.image.setImageResource(life.image)
        rowView.title.text = life.title!!
        rowView.description.text = life.en!!

        return rowView
    }

}