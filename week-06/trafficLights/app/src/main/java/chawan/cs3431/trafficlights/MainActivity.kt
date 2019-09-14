package chawan.cs3431.trafficlights

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    private val TrafficLightID = arrayOf( R.id.imageView, R.id.imageView2, R.id.imageView3, R.id.imageView4 )
    private val ImageIDList = arrayOf( R.drawable.green, R.drawable.yellow, R.drawable.red_yellow, R.drawable.red )
    private var CurrentStates = arrayOf(3,3,3,3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLight()

        imageView.setOnClickListener {
            Log.d("CurrentStates-0 => ", " ${CurrentStates[0]}, ${CurrentStates[1]}, ${CurrentStates[2]}, ${CurrentStates[3]}")
            var gl = getGreenLight()
            if(isRed(0) === true){
                if(gl<400){
                    greenLightClicked(gl)
                }
                redLightClicked(0)
            }else{
                if(gl<400){
                    greenLightClicked(gl)
                }
            }
        }

        imageView2.setOnClickListener {
            Log.d("CurrentStates-1 => ", " ${CurrentStates[0]}, ${CurrentStates[1]}, ${CurrentStates[2]}, ${CurrentStates[3]}")
            var gl = getGreenLight()
            if(isRed(1) === true){
                if(gl<400){
                    greenLightClicked(gl)
                }
                redLightClicked(1)
            }else{
                if(gl<400){
                    greenLightClicked(gl)
                }
            }
        }

        imageView3.setOnClickListener {
            Log.d("CurrentStates-2 => ", " ${CurrentStates[0]}, ${CurrentStates[1]}, ${CurrentStates[2]}, ${CurrentStates[3]}")
            var gl = getGreenLight()
            if(isRed(2) === true){
                if(gl<400){
                    greenLightClicked(gl)
                }
                redLightClicked(2)
            }else{
                if(gl<400){
                    greenLightClicked(gl)
                }
            }
        }

        imageView4.setOnClickListener {
            Log.d("CurrentStates-3 => ", " ${CurrentStates[0]}, ${CurrentStates[1]}, ${CurrentStates[2]}, ${CurrentStates[3]}")
            var gl = getGreenLight()
            if(isRed(3) === true){
                if(gl<400){
                    greenLightClicked(gl)
                }
                redLightClicked(3)
            }else{
                if(gl<400){
                    greenLightClicked(gl)
                }
            }
        }

        val currentStateRef = database.child("currentState")
        currentStateRef.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                Log.d("DataSnapshot: ", ""+p0.value)
                var key = p0.key!!.toInt()
                var value = p0.value.toString()
                CurrentStates[key] = value.toInt()
                setLight(key, value.toInt())
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        })

    }

    private fun setLight(index: Int, state: Int){
        when(state){
            1 -> setToYellow(index)
            2 -> setToYellowRed(index)
            3 -> setToRed(index)
            0 -> setToGreen(index)
        }
    }

    private fun getGreenLight(): Int{
        for(i in 0 until CurrentStates.size){
            if(CurrentStates[i] === 0){
                return i
            }
        }
        return 401
    }

    private fun setToGreen(index: Int) {
        val view = findViewById<ImageView>(TrafficLightID[index])
        view.setImageResource(ImageIDList[0])
        when(index){
            0 -> tvMain.text = "North"
            1 -> tvMain.text = "West"
            2 -> tvMain.text = "East"
            3 -> tvMain.text = "South"
        }
    }

    private fun setToYellow(index: Int) {
        val view = findViewById<ImageView>(TrafficLightID[index])
        view.setImageResource(ImageIDList[1])
    }

    private fun setToYellowRed(index: Int) {
        val view = findViewById<ImageView>(TrafficLightID[index])
        view.setImageResource(ImageIDList[2])
    }

    private fun setToRed(index: Int) {
        val view = findViewById<ImageView>(TrafficLightID[index])
        view.setImageResource(ImageIDList[3])
    }

    private fun isRed(index: Int): Boolean{
        return (CurrentStates[index] === 3)
    }

    private fun initLight(){
        for(i in 0 until CurrentStates.size-1){
            val view = findViewById<ImageView>(TrafficLightID[i])
            view.setImageResource(ImageIDList[CurrentStates[i]])
        }
    }

    private fun redLightClicked(index: Int){
        val timer = object: CountDownTimer((5.toLong()*1000), 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                Log.d("", "GREEN - $index")
                CurrentStates[index] = 0
                setToGreen(index)
                database.child("currentState/$index").setValue(CurrentStates[index])
            }
        }
        timer.start()
    }

    private fun greenLightClicked(index: Int){
        val timer = object: CountDownTimer((4.toLong()*1000), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var sec = (millisUntilFinished/1000).toInt()
                when(sec){
                    1 -> {
                        setToYellowRed(index)
                        CurrentStates[index] = 1
                    }
                    2 -> {
                        setToYellow(index)
                        CurrentStates[index] = 2
                    }
                }
            }

            override fun onFinish() {
                Log.d("", "RED")
                setToRed(index)
                CurrentStates[index] = 3
                database.child("currentState/$index").setValue(CurrentStates[index])
            }

        }
        timer.start()
    }


}
