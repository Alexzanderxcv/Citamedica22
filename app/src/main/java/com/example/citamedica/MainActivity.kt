package com.example.citamedica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.citamedica.util.PreferenceHelper
import com.example.citamedica.util.PreferenceHelper.get
import com.example.citamedica.util.PreferenceHelper.set

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvGoRegister = findViewById<TextView>(R.id.tv_go_to_register)
        tvGoRegister.setOnClickListener {
            goToRegister()
        }

        val btnGoMenu = findViewById<Button>(R.id.btn_go_to_menu)
        btnGoMenu.setOnClickListener {
            handleLogin()
        }

        val preferences = PreferenceHelper.defaultPrefs(this)
        if (preferences["session", false])
            goToMenu()
    }

    private fun goToRegister() {
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }

    private fun goToMenu() {
        val i = Intent(this, MenuActivity::class.java)
        createSessionPreferences()
        startActivity(i)
        finish()
    }

    private fun createSessionPreferences() {
        val preferences = PreferenceHelper.defaultPrefs(this)
        preferences["session"] = true
    }

    // LOGIN APLICAR
    private fun handleLogin() {
        val dbHelper = DBHelper(this)

        // Obtener los datos del login
        val correo = findViewById<EditText>(R.id.etCorreo).text.toString()
        val password = findViewById<EditText>(R.id.etContrasenia).text.toString()

        // Validamos
        if (dbHelper.checkUser(correo, password)) {
            goToMenu()  // mandar al menu verificado el logueo
        } else {
            Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
}
