package ca.cegepgarneau.tp1_raphael_grougnet

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btLogin: Button
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)
        btLogin = findViewById(R.id.btnConnexion)
        btLogin.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnConnexion -> {
                    var nomTextEdit = findViewById<EditText>(R.id.nameText)
                    var nom = nomTextEdit.text.toString()
                    prefs = getSharedPreferences("MonFichierDeSauvegarde", MODE_PRIVATE)
                    var nomStock = prefs.getString(nom, "")
                    val editor = prefs.edit() //pour obtenir une version éditable
                    if (nomStock == "") {

                        editor.putString(nom, "15")

                    }
                    editor.putString("session", nom)
                    editor.apply()
                    val intent = Intent(this, Accueil::class.java)
                    startActivity(intent)


                }
            }
        }
    }
}