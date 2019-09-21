package chawan.cs3431.weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import org.json.JSONArray
import com.google.gson.annotations.SerializedName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null
    val sampleJson = """
      [{
          "userId": 1,
          "id": 1,
          "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
          "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
        }]
        """

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://www.metaweather.com/").build()
        val weatherApi = retrofit.create(API::class.java)
        var response = weatherApi.getAllPosts()

        response.observeOn(AndroidSchedulers.mainThread()).subscribeOn(IoScheduler()).subscribe {
            Log.d("response :=> ", it.consolidated_weather[0].toString())
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
            tvCountry.text = "${it.parent.title}"
            tvCity.text = "${it.title}"
            recyclerView = findViewById(R.id.recyclerView) as RecyclerView
            recyclerView!!.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
            recyclerView!!.setLayoutManager(GridLayoutManager(this, 2))
            val myAdapter = MyAdapter(it,this)
            recyclerView!!.setAdapter(myAdapter)
        }
    }



    //  ---------------  TESTING ------------------
    private fun testGSON(){
        var jsonArray = JSONArray(sampleJson)
        var jsonArray2 = Gson().fromJson(sampleJson, JsonArray::class.java)

        for (jsonIndex in 0..(jsonArray2.size() - 1)) {
            Log.d("JSON2", jsonArray.getJSONObject(jsonIndex).getString("body"))
        }
    }
    // ------------------ XXXX ------------------

}



