package ca.cegepgarneau.tp1_raphael_grougnet

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var btLogin: Button
    private lateinit var prefs: SharedPreferences
    private lateinit var nomTextEdit: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connexion)
        btLogin = findViewById(R.id.btnConnexion)
        nomTextEdit = findViewById<EditText>(R.id.nameText)
        btLogin.setOnClickListener(this)
        btLogin.isEnabled = false
        nomTextEdit.setOnKeyListener(View.OnKeyListener { _, _, _ ->
            btLogin.isEnabled = nomTextEdit.text.toString() != ""
            false
        })
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnConnexion -> {

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