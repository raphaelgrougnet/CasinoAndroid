package ca.cegepgarneau.tp1_raphael_grougnet

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class Guichet : AppCompatActivity(), View.OnClickListener {
    private lateinit var btBuy: Button
    private lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guichet)
        btBuy = findViewById(R.id.btnBuyCounter)
        btBuy.setOnClickListener(this)
        prefs = getSharedPreferences("MonFichierDeSauvegarde", MODE_PRIVATE)

    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnBuyCounter -> {
                    //ajouter les jetons entré par l'utilisateur à son compte
                    var nom = prefs.getString("session", "Sans nom")
                    var jetons = prefs.getString(nom, "0")
                    var jetonsInt = jetons?.toInt()
                    var jetonsAjout = findViewById<EditText>(R.id.edtTokenCounter)
                    var jetonsAjoutInt = jetonsAjout.text.toString().toInt()
                    jetonsInt = jetonsInt?.plus(jetonsAjoutInt)
                    val intent  = intent
                    intent.putExtra("jetons", jetonsInt.toString())
                    setResult(RESULT_OK, intent)
                    finish()


                }
            }
        }
    }
}