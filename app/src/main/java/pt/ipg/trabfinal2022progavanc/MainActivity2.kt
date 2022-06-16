package pt.ipg.trabfinal2022progavanc

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import pt.ipg.trabfinal2022progavanc.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttonEquipa = findViewById<Button>(R.id.button_Equipa)
        buttonEquipa.setOnClickListener{entrarEquipa()}

        val buttonTreinador = findViewById<Button>(R.id.button_Treinador)
        buttonTreinador.setOnClickListener{entrarTreinador()}

        val buttonJogador = findViewById<Button>(R.id.button_Jogador)
        buttonJogador.setOnClickListener{entrarJogador()}

    }

    private fun entrarJogador() {

        val intent = Intent(this, MainActivity3::class.java)
        startActivity(intent)
    }

    private fun entrarTreinador() {

        val intent = Intent(this, MainActivity4::class.java)
        startActivity(intent)
    }

    private fun entrarEquipa() {

        val intent = Intent(this, MainActivity5::class.java)
        startActivity(intent)
    }
}