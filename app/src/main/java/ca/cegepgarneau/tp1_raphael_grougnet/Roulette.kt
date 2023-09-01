package ca.cegepgarneau.tp1_raphael_grougnet

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.google.android.material.snackbar.Snackbar

class Roulette : AppCompatActivity(), View.OnClickListener {
    private lateinit var btPlay: Button
    private lateinit var prefs: SharedPreferences
    private lateinit var editMise: EditText
    private lateinit var editPrediction: EditText
    private lateinit var rdoPair: RadioButton
    private lateinit var rdoImpair: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)
        btPlay = findViewById(R.id.btnPlayWheel)
        editMise = findViewById(R.id.edtBetWheel)
        editPrediction = findViewById(R.id.edtPredictionWheel)
        rdoPair = findViewById(R.id.radioEven)
        rdoImpair = findViewById(R.id.radioOdd)

        btPlay.setOnClickListener(this)

        rdoPair.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editPrediction.text.clear()
                editPrediction.isEnabled = false
                btPlay.isEnabled = editMise.text.toString() != ""
            }
        }
        rdoImpair.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editPrediction.text.clear()
                editPrediction.isEnabled = false
                btPlay.isEnabled = editMise.text.toString() != ""
            }
        }
        editMise.setOnKeyListener(View.OnKeyListener { _, _, _ ->
            btPlay.isEnabled = (editPrediction.text.toString() != "" && editPrediction.text.toString() != "0" && editMise.text.toString() != "") || rdoPair.isChecked || rdoImpair.isChecked
            false
        })

        editPrediction.setOnKeyListener(View.OnKeyListener { _, _, _ ->
            btPlay.isEnabled = (editPrediction.text.toString() != "" && editPrediction.text.toString() != "0" && editMise.text.toString() != "") || rdoPair.isChecked || rdoImpair.isChecked
            false
        })



    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnPlayWheel -> {
                    prefs = getSharedPreferences("MonFichierDeSauvegarde", MODE_PRIVATE)
                    var mise = editMise.text.toString().toInt()
                    var gain = 0
                    var nombreAleatoire = (1..36).random()
                    var nom = prefs.getString("session", "Sans nom")
                    var jetons = prefs.getString(nom, "0")
                    var predictionString = editPrediction.text.toString()

                    if (mise > jetons?.toInt()!!){
                        var intent = intent
                        intent.setClass(this, Guichet::class.java)
                        startActivity(intent)
                    }
                    else{
                        if (rdoPair.isChecked || rdoImpair.isChecked){

                            if (nombreAleatoire % 2 == 0 && rdoPair.isChecked){
                                Snackbar.make(findViewById(R.id.btnPlayWheel), getString(R.string.win), Snackbar.LENGTH_LONG).show()
                                gain = mise * 2
                            }
                            else if (nombreAleatoire % 2 != 0 && rdoImpair.isChecked){
                                Snackbar.make(findViewById(R.id.btnPlayWheel), getString(R.string.win), Snackbar.LENGTH_LONG).show()
                                gain = mise * 2
                            }
                            else{
                                Snackbar.make(findViewById(R.id.btnPlayWheel), getString(R.string.lose), Snackbar.LENGTH_LONG).show()
                            }
                        }

                        else if (predictionString.toInt() == nombreAleatoire){
                            Snackbar.make(findViewById(R.id.btnPlayWheel), getString(R.string.win), Snackbar.LENGTH_LONG).show()
                            gain = mise *36
                        }
                        else{
                            Snackbar.make(findViewById(R.id.btnPlayWheel), getString(R.string.lose), Snackbar.LENGTH_LONG).show()

                        }
                        jetons = (jetons?.toInt()?.minus(mise)).toString()
                        jetons = (jetons?.toInt()?.plus(gain)).toString()
                        val editor = prefs.edit() //pour obtenir une version éditable
                        editor.putString(nom, jetons.toString())
                        editor.apply()
                        editMise.text.clear()
                        editPrediction.text.clear()
                        btPlay.isEnabled = false
                        rdoImpair.isChecked = false
                        rdoPair.isChecked = false
                    }


                }
            }
        }
    }

}