package chawan.cs3431.exercise01

import android.graphics.Color.parseColor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.exp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCalculate.setOnClickListener {
            var weight = etWeight.text.toString()
            var height = etHeight.text.toString()

            if(weight == "" || height == ""){
                tvOutput.text = "Invalid Input !"

            }else {
                calculate(weight.toInt(), height.toInt())
            }

        }

    }

    private fun calculate(weight: Int, height: Int){

        var bmi = weight.toDouble() / Math.pow(2.0,(height.toDouble()/100))

//        Log.d(TAG, "\n(height/100).toDouble(): ${(height.toDouble()/100)}\nheightx2: ${Math.pow(2.0,(height/100).toDouble())}")
        tvOutput.setTextColor((parseColor("#FBFAFA")))
        tvOutput.text = bmi.toString()
//        Log.d(TAG, "\nweight: ${weight.toString()} \nheight: ${height.toString()}\n bmi: ${bmi.toString()}")

        when {
            bmi <= 25 -> {
                tvOutput.setBackgroundColor(parseColor("#2ED144"))
                tvOutput.text = "Normal"
            }
            bmi <= 30 -> {
                tvOutput.setBackgroundColor(parseColor("#DEDE24"))
                tvOutput.text = "Average"
            }
            bmi <= 40 -> {
                tvOutput.setBackgroundColor(parseColor("#F08B2D"))
                tvOutput.text = "Important"
            }
            else -> {
                tvOutput.setBackgroundColor(parseColor("#F03F2D"))
                tvOutput.text = "Severe"
            }
        }


    }

    companion object {
        private const val TAG = "Invalid Input"
    }

}
