package chawan.cs3431.trafficlight

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
//import android.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_lights.*

class MainActivity : AppCompatActivity() {

//    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()
    private val TrafficLightID = arrayOf( R.id.imageView, R.id.imageView2, R.id.imageView3, R.id.imageView4 )
    private val ImageIDList = arrayOf( R.drawable.green, R.drawable.yellow, R.drawable.red_yellow, R.drawable.red )
    private var CurrentStates = arrayOf(3,3,0,3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lights)

        initLight()

//        var dbRoot = database.reference
//        dbRoot.setValue(CurrentStates)
        imageView.setOnClickListener {
            Log.d("CurrentStates-0 => ", " ${CurrentStates[0]}, ${CurrentStates[1]}, ${CurrentStates[2]}, ${CurrentStates[3]}")
            Log.d("", ""+isRed(0))
            if(isRed(0)){
                greenLightClicked(getGreenLight())
                redLightClicked(0)
            }
        }

        imageView2.setOnClickListener {
            Log.d("CurrentStates-1 => ", " ${CurrentStates[0]}, ${CurrentStates[1]}, ${CurrentStates[2]}, ${CurrentStates[3]}")
            if(isRed(1) === true){
                greenLightClicked(getGreenLight())
                redLightClicked(1)
            }
        }

        imageView3.setOnClickListener {
            Log.d("CurrentStates-2 => ", " ${CurrentStates[0]}, ${CurrentStates[1]}, ${CurrentStates[2]}, ${CurrentStates[3]}")
            if(isRed(2) === true){
                greenLightClicked(getGreenLight())
                redLightClicked(2)
            }
        }

        imageView4.setOnClickListener {
            Log.d("CurrentStates-3 => ", " ${CurrentStates[0]}, ${CurrentStates[1]}, ${CurrentStates[2]}, ${CurrentStates[3]}")
            if(isRed(3) === true){
                greenLightClicked(getGreenLight())
                redLightClicked(3)
            }
        }
    }

    private fun getGreenLight(): Int{
        for(i in 0 until CurrentStates.size-1){
            if(CurrentStates[i] === 0){
                return i
            }
        }
        return 0
    }

    private fun setToGreen(index: Int) {
        val view = findViewById<ImageView>(TrafficLightID[index])
        view.setImageResource(ImageIDList[0])
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
            }

        }
        timer.start()
    }

}
