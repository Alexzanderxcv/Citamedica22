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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Llámalo al inicio
        setContentView(R.layout.activity_register) // Configura la vista aquí

        val dbHelper = DBHelper(this)
        enableEdgeToEdge()

        // Configuración del listener para el padding de insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración de los elementos de la interfaz
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.etNombre).text.toString()
            val correo = findViewById<EditText>(R.id.etCorreo).text.toString()
            val password = findViewById<EditText>(R.id.etContrasenia).text.toString()
            val repetirPassword = findViewById<EditText>(R.id.etRepetirContrasenia).text.toString()

            if (password == repetirPassword) {
                val result = dbHelper.insertUser(nombre, correo, password)
                if (result != -1L) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrarse", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }

        val tvGoLogin = findViewById<TextView>(R.id.tv_go_to_login)
        tvGoLogin.setOnClickListener {
            goToLogin()
        }
    }

    private fun goToLogin() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }
}
