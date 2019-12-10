package chawan.cs3431.assignment_05

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.R
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import android.view.LayoutInflater
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class CustomAdapter : BaseAdapter() {
    lateinit var mContext: Context
    lateinit var strName: Array<String>
    lateinit var resId: IntArray

    fun CustomAdapter(context: Context, strName: Array<String>, resId: IntArray): ??? {
        this.mContext = context
        this.strName = strName
        this.resId = resId
    }

    override fun getCount(): Int {
        return 0
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View, parent: ViewGroup): View? {
        val mInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (view != null) {
            view = mInflater.inflate(R.layout.listview_row, parent, false)
        }

        val textView = view.findViewById(R.id.textView1) as TextView
        textView.text = strName[position]

        val imageView = view.findViewById(R.id.imageView1) as ImageView
        imageView.setBackgroundResource(resId[position])

        return view
    }
}
