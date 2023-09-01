package ca.cegepgarneau.tp1_raphael_grougnet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class Roulette : AppCompatActivity(), View.OnClickListener {
    private lateinit var btPlay: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)
        btPlay = findViewById(R.id.btnPlayWheel)
        btPlay.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnPlayWheel -> {

                }
            }
        }
    }

}