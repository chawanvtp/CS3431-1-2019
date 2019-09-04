package chawan.cs3431.recyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    var lifes = arrayOf(
        "Crab",
        "Killer Whale",
        "Octopus",
        "Sea Otter",
        "Sea Turtle",
        "Shark"
    )
    var arrImg = arrayOf<Int>(
        R.drawable.image1,
        R.drawable.image2,
        R.drawable.image3,
        R.drawable.image4,
        R.drawable.image5,
        R.drawable.image6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        recyclerView!!.setLayoutManager(GridLayoutManager(this, 2))
        val myAdapter = MyAdapter(lifes,arrImg,this)
        recyclerView!!.setAdapter(myAdapter)
    }

}
