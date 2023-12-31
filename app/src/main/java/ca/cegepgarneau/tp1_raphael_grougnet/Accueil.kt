package ca.cegepgarneau.tp1_raphael_grougnet

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class Accueil :  AppCompatActivity(), View.OnClickListener{

    private lateinit var btPlay: Button
    private lateinit var btCounter: Button
    private lateinit var prefs: SharedPreferences
    private lateinit var txtWelcome : TextView
    private lateinit var txtBalance : TextView
    var activiteResultatGuichet: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        //Récupération des SharedPreferences
        prefs = getSharedPreferences("MonFichierDeSauvegarde", MODE_PRIVATE)

        //Récupération des boutons
        btPlay = findViewById(R.id.btnPlay)
        btCounter = findViewById(R.id.btnBankCounter)
        btPlay.setOnClickListener(this)
        btCounter.setOnClickListener(this)

        //Récupération du nom de l'utilisateur
        var nom = prefs.getString("session", "Sans nom")

        //Récuppération des jetons dans les SharedPreferences
        var jetons = prefs.getString(nom, "0")

        //Récupération des TextView
        txtWelcome = findViewById(R.id.txtWelcomeHome)
        txtBalance = findViewById(R.id.txtBalanceHome)

        //Mettre le nom de l'utilisateur dans le titre de l'activité
        txtWelcome.text = getString(R.string.welcome_player) + " " + nom

        //Mettre le nombre de jetons de l'utilisateur dans le bouton de la banque
        txtBalance.text = jetons

        //Création de l'activité du guichet
        activiteResultatGuichet = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                // Récupération des données reçues : contenues dans l'intent renvoyé
                val data = result.data
                var jetons = data!!.getStringExtra("jetons")
                var nom = prefs.getString("session", "Sans nom")

                val editor = prefs.edit() //pour obtenir une version éditable
                editor.putString(nom, jetons.toString())
                editor.apply()
                txtBalance.text = jetons
                Snackbar.make(findViewById(R.id.btnBankCounter), getString(R.string.balance_updated), Snackbar.LENGTH_LONG).show()
            }
        }
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnPlay -> {
                    //Lancer l'activité de la roulette
                    if (prefs.getString(prefs.getString("session", "Sans nom"),"0") == "0"){
                        intent = Intent(applicationContext, Guichet::class.java)

                        activiteResultatGuichet!!.launch(intent)
                    }
                    else {
                        val intent = Intent()
                        intent.setClass(this, Roulette::class.java)
                        startActivity(intent)
                    }
                }
                //Lancer l'activité du guichet
                R.id.btnBankCounter -> {
                    //Lancer l'activité du guichet
                    intent = Intent(applicationContext, Guichet::class.java)
                    // Méthode de classe Launcher
                    activiteResultatGuichet!!.launch(intent)



                }
            }
        }
    }


}