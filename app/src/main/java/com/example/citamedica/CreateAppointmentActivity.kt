package com.example.citamedica

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar

class CreateAppointmentActivity : AppCompatActivity() {
    private val selectedCalendar = Calendar.getInstance()
    private var selectedRadioButton: RadioButton? = null
    private lateinit var dbHelper: DBHelper
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)
        val btNext2 = findViewById<Button>(R.id.btn_siguiente_dos)
        val btnNext = findViewById<Button>(R.id.btn_siguiente)
        val btnConfirm = findViewById<Button>(R.id.btn_confirmar)
        val cvNext = findViewById<CardView>(R.id.cv_siguiente)
        val cvConfirm = findViewById<CardView>(R.id.cv_confirmar)
        val cvResumen = findViewById<CardView>(R.id.cv_resumen)
        val etDescription = findViewById<EditText>(R.id.et_description)
        val etScheduledDate = findViewById<EditText>(R.id.et_fecha)
        val linearLayoutCreateAppointment = findViewById<LinearLayout>(R.id.linearLayout_create_appointment)
        val spinnerSpecialties = findViewById<Spinner>(R.id.spiiner_especialidades)
        val spinnerDoctor = findViewById<Spinner>(R.id.spinner_medico)
        val radioGroupType = findViewById<RadioGroup>(R.id.radio_group_type)
        dbHelper = DBHelper(this)
        btnNext.setOnClickListener {
            if(etDescription.text.toString().length < 3){
                etDescription.error = "La descripcion es demasiado corta"
            }
            else{
                cvNext.visibility = View.GONE
                cvConfirm.visibility = View.VISIBLE
            }
        }
        btnConfirm.setOnClickListener {
            val description = etDescription.text.toString()
            val date = etScheduledDate.text.toString()
            val specialty = spinnerSpecialties.selectedItem.toString()
            val doctor = spinnerDoctor.selectedItem.toString()
            val time = selectedRadioButton?.text.toString()
            // Obtén el tipo de consulta seleccionado
            val selectedRadioTypeId = radioGroupType.checkedRadioButtonId
            val tipoConsulta = findViewById<RadioButton>(selectedRadioTypeId)?.text.toString()
            // Llamamos a insertCita en DBHelper con todos los parámetros
            dbHelper.checkTablesExistence()
            val rowId = dbHelper.insertCita(description, specialty, tipoConsulta, doctor, date, time)
            if (rowId != -1L) {
                Toast.makeText(applicationContext, "Cita registrada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Error al registrar la cita", Toast.LENGTH_SHORT).show()
            }
        }
        btNext2.setOnClickListener{
            if(etScheduledDate.text.toString().isEmpty()){
                etScheduledDate.error = ""
                Snackbar.make(linearLayoutCreateAppointment, "Debe escoger una fecha para la cita", Snackbar.LENGTH_SHORT).show()
            } else if (selectedRadioButton == null){
                Snackbar.make(linearLayoutCreateAppointment, "Debe selecionar una hora para la cita", Snackbar.LENGTH_SHORT).show()
            } else {
                showAppointmenDataToConfirm()
                cvConfirm.visibility = View.GONE
                cvResumen.visibility = View.VISIBLE
            }

        }
        val optionSpecialties = arrayOf("Cardiologia", "Dermatologia", "Ginecologo", "Pediatria", "Gastroenterologia", "Endocrinologia")
        spinnerSpecialties.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionSpecialties)
        val optionDoctor = arrayOf("Doctor. Garcia ", "Doctor. Hernandez", "Doctor. Rodriguez", "Doctor. Gutierres", "Doctor. Gonzales", "Doctor. Rojas" )
        spinnerDoctor.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionDoctor)
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationDialog()
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
    private fun showAppointmenDataToConfirm(){
        val tvConfirmDescription = findViewById<TextView>(R.id.tv_resumen_sintomas)
        val tvConfirmSpeciality = findViewById<TextView>(R.id.tv_resumen_especialidad)
        val tvConfirmType = findViewById<TextView>(R.id.tv_resumen_tipoconsulta)
        val tvConfirmDoctorName = findViewById<TextView>(R.id.tv_resumen_medico)
        val tvConfirmDate = findViewById<TextView>(R.id.tv_resumen_fecha)
        val tvConfirmTime = findViewById<TextView>(R.id.tv_resumen_hora)
        val etDescription = findViewById<EditText>(R.id.et_description)
        val spinnerSpecialties = findViewById<Spinner>(R.id.spiiner_especialidades)
        val radioGroupType = findViewById<RadioGroup>(R.id.radio_group_type)
        val spinnerDoctor = findViewById<Spinner>(R.id.spinner_medico)
        val etScheduledDate = findViewById<EditText>(R.id.et_fecha)
        tvConfirmDescription.text = etDescription.text.toString()
        tvConfirmSpeciality.text = spinnerSpecialties.selectedItem.toString()
        val selectRadioBtnId = radioGroupType.checkedRadioButtonId
        val selectRadioType = radioGroupType.findViewById<RadioButton>(selectRadioBtnId)
        tvConfirmType.text = selectRadioType.text.toString()
        tvConfirmDoctorName.text = spinnerDoctor.selectedItem.toString()
        tvConfirmDate.text = etScheduledDate.text.toString()
        tvConfirmTime.text = selectedRadioButton?.text.toString()
    }
    fun onClickScheduledDate(v: View?) {
        val etScheduledDate = findViewById<EditText>(R.id.et_fecha)
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)
        val listener = DatePickerDialog.OnDateSetListener{ DatePicker, y, m, d ->
            selectedCalendar.set(y,m,d)
            etScheduledDate.setText(resources.getString(R.string.date_format,
                y,
                (m+1).twoDigits(),
                d.twoDigits()
            ))
            etScheduledDate.error = null
            displayRadioButtons()
        }
       val  datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)
            val datePicker = datePickerDialog.datePicker
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, 1)
            datePicker.minDate = calendar.timeInMillis
            calendar.add(Calendar.DAY_OF_MONTH, 29)
            datePicker.maxDate = calendar.timeInMillis
            datePickerDialog.show()
    }
    private fun displayRadioButtons(){
        val radioGroupLeft = findViewById<LinearLayout>(R.id.radio_group_izq)
        val radioGroupRight = findViewById<LinearLayout>(R.id.radio_group_der)
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()
        selectedRadioButton = null
        var goToLeft = true
        val hours = arrayOf("8:00 AM", "8:30 AM", "9:00 AM", "9:30 AM", "10:00 AM")
        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it
            radioButton.setOnClickListener{view ->
                selectedRadioButton?.isChecked = false
                selectedRadioButton = view as RadioButton?
                selectedRadioButton?.isChecked = true
            }
            if (goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)

            goToLeft = !goToLeft
        }
    }
    private fun showExitConfirmationDialog() {
        val cvNext = findViewById<CardView>(R.id.cv_siguiente)
        val cvConfirm = findViewById<CardView>(R.id.cv_confirmar)
        val cvResumen = findViewById<CardView>(R.id.cv_resumen)
        if(cvResumen.visibility == View.VISIBLE){
            cvResumen.visibility = View.GONE
            cvConfirm.visibility = View.VISIBLE
        }else if(cvConfirm.visibility == View.VISIBLE){
            cvConfirm.visibility = View.GONE
            cvNext.visibility = View.VISIBLE
        } else if(cvNext.visibility == View.VISIBLE){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Está seguro que desea salir?")
            builder.setMessage("Si abandonas el registro, los datos que había ingresado se perderán.")
            builder.setPositiveButton("Salir") { _, _ ->
                finish()
            }
            builder.setNegativeButton("Continuar") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
    private fun Int.twoDigits()
    = if (this>= 10 ) this.toString() else "0$this"
}