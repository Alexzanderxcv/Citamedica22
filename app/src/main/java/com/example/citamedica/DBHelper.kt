package com.example.citamedica

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, "Bdcitas.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE USUARIO "+
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NomApe TEXT, Correo TEXT, Contrasenia TEXT)"
        val citasTabla = "CREATE TABLE citas " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descripcion TEXT, " +
                "especialidad TEXT, " +
                "tipo_consulta TEXT, " +
                "medico TEXT, " +
                "fecha TEXT, " +
                "hora TEXT)"

        Log.d("DBHelper", "Creando tabla USUARIO...")
        db!!.execSQL(ordenCreacion)

        Log.d("DBHelper", "Creando tabla citas...")
        db.execSQL(citasTabla)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS USUARIO"
        val citasBoorado = "DROP TABLE IF EXISTS citas"
        Log.d("DBHelper", "Actualizando base de datos de versión $oldVersion a $newVersion")
        db!!.execSQL(ordenBorrado)
        db.execSQL(citasBoorado)
        onCreate(db)
    }
    //METODO PARA INSERTAR USUAARIO EN EL REGISTRO
    fun insertUser(nomape: String, correo: String, password: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("NomApe", nomape)
            put("Correo", correo)
            put("Contrasenia", password)
        }
        val result = db.insert("USUARIO", null, values)
        db.close()
        return result
    }
    //METODO PARA VERIFICAR EL USUARIO EN EL LOGUEO
    fun checkUser(correo: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM USUARIO WHERE Correo = ? AND Contrasenia = ?",
            arrayOf(correo, password)
        )


        Log.d("DBHelper", "Checking user: Email: $correo, Password: $password, Exists: ${cursor.count > 0}")

        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    //METODO PARA INSERTAR LA CITA, QUE TENGA COMO PARAMETROS LOS DATOS QUE RECIBIRA DE LOS EDITTEXT Y ETC
    fun insertCita(descripcion: String, especialidad: String, tipoConsulta: String, medico: String, fecha: String, hora: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("descripcion", descripcion)
            put("especialidad", especialidad)
            put("tipo_consulta", tipoConsulta)
            put("medico", medico)
            put("fecha", fecha)
            put("hora", hora)
        }

        // LOGS
        Log.d("DBHelper", "Insertando cita: $descripcion, $especialidad, $tipoConsulta, $medico, $fecha, $hora")

        val result = db.insert("citas", null, contentValues)

        if (result == -1L) {
            Log.e("DBHelper", "Error al insertar la cita")
        } else {
            Log.d("DBHelper", "Cita insertada correctamente con ID: $result")
        }

        return result
    }
    //METODO PARA OBTENER LAS CITAS Y LLAMARLO EN EL APPOINTMENT
    fun getAllCitas(): List<Cita> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM citas", null)
        val citas = mutableListOf<Cita>()

        if (cursor.moveToFirst()) {
            do {
                val cita = Cita(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                    especialidad = cursor.getString(cursor.getColumnIndexOrThrow("especialidad")),
                    tipoConsulta = cursor.getString(cursor.getColumnIndexOrThrow("tipo_consulta")),
                    medico = cursor.getString(cursor.getColumnIndexOrThrow("medico")),
                    fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha")),
                    hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"))
                )
                citas.add(cita)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return citas
    }
    //METODO PARA COMPROBAR SI LA TABLA EXISTIA(ME SALIA ERROR QUE LA TABLA NO EXISTIA PERO ERA UNA VAINILLA)
    fun checkTablesExistence() {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null)

        while (cursor.moveToNext()) {
            Log.d("DBHelper", "Table: ${cursor.getString(0)}")
        }

        cursor.close()
    }



}