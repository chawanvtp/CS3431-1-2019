package chawan.cs3431.listofoceanlife

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.detail_main.*

class DetailActivity : AppCompatActivity() {

    private var lifeList = ArrayList<Life>()
    var adapter: CustomAdapter? = null
    private val myImageList = intArrayOf(R.drawable.octopus, R.drawable.crab, R.drawable.sea_otter, R.drawable.sea_turtle, R.drawable.shark, R.drawable.killer_whale)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_main)

        var title = intent.getStringExtra("title")
        var th = intent.getStringExtra("th")
        var en = intent.getStringExtra("en")
        var img = intent.getIntExtra("image", 0)


        tvHeader.text = title
        tvDes.text = en
        imageView.setImageResource(img)

        Log.d(TAG, "INTENT $title - $en - $img")
        imageView2.setOnClickListener {
            tvDes.text = th
        }

        imageView3.setOnClickListener {
            tvDes.text = en
        }
    }


    companion object {
        private const val TAG = "TAG"
    }
}
