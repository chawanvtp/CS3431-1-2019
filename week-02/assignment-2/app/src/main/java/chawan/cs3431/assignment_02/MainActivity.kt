package chawan.cs3431.assignment_02

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //        BUTTON - LightPurple
        btnLightPurple.setOnClickListener {
            btnColorClicked("Light Purple", "b970e9")
        }

        //        BUTTON - LightPurple
        btnYoungSalmon.setOnClickListener {
            btnColorClicked("Young Salmon", "f5bbba")
        }

        //        BUTTON - LightPurple
        btnRedOrange.setOnClickListener {
            btnColorClicked("Red Orange", "ff3838")
        }

        //        BUTTON - LightPurple
        btnSpiroDiscoBall.setOnClickListener {
            btnColorClicked("Spiro Disco Ball", "24c0eb")
        }

        //        BUTTON - LightPurple
        btnDornYellow.setOnClickListener {
            btnColorClicked("Dorn Yellow", "fdf251")
        }

        //        BUTTON - LightPurple
        btnRadiantYellow.setOnClickListener {
            btnColorClicked("Radiant Yellow", "fd9f1a")
        }

        //        BUTTON - LightPurple
        btnWeirdGreen.setOnClickListener {
            btnColorClicked("Weird Green", "73e080")
        }

        //        BUTTON - LightPurple
        btnBalticSea.setOnClickListener {
            btnColorClicked("Baltic Sea", "3d3d3d")
        }

        //        BUTTON - LightPurple
        btnLightIndigo.setOnClickListener {
            btnColorClicked("Light Indigo", "7158e2")
        }

        //        BUTTON - LightPurple
        btnLightBrown.setOnClickListener {
            btnColorClicked("Light Brown", "986d60")
        }

        //        BUTTON - LightPurple
        btnDepressedGreen.setOnClickListener {
            btnColorClicked("Depressed Green", "578057")
        }

        //        BUTTON - LightPurple
        btnSteelPink.setOnClickListener {
            btnColorClicked("Steel Pink", "d549b5")
        }

    }

    private fun btnColorClicked(name: String, code: String){
        tvColorHexCode.text = "#$code"
        tvColoName.setTextColor(Color.parseColor("#$code"))
        tvColoName.text = "$name"
    }
}
