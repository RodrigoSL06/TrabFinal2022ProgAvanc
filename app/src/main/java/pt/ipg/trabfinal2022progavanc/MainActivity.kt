package pt.ipg.trabfinal2022progavanc

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import pt.ipg.trabfinal2022progavanc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonEntrar = findViewById<Button>(R.id.entrar_button)
        buttonEntrar.setOnClickListener{entrar()}
    }

    private fun entrar() {

        val editTextUsername = findViewById<EditText>(R.id.username_edit_text)

        val username = editTextUsername.text.toString()
        if (username.isBlank()) {
            editTextUsername.error = "Field mandatory"
            editTextUsername.requestFocus()
            return
        }

        if (username != "admin") {
            editTextUsername.error = "Username Incorrect"
            editTextUsername.requestFocus()
            return
        }

        val editTextPassword = findViewById<EditText>(R.id.password_edit_text)

        val password = editTextPassword.text.toString()

        if (password.isBlank()) {
            editTextPassword.error = "Field mandatory"
            editTextPassword.requestFocus()
            return
        }
        if (password != "1234") {
            editTextPassword.error = "Password Incorrect"
            editTextPassword.requestFocus()
            return
        }

        val intent = Intent(this, FirstFragment::class.java)
        startActivity(intent)


    }
}