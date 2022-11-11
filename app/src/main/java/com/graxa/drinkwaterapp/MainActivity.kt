package com.graxa.drinkwaterapp

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.graxa.drinkwaterapp.Model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var et_peso: EditText
    private lateinit var et_idade: EditText
    private lateinit var btn_calcular: Button
    private lateinit var tv_resultadoMl: TextView
    private lateinit var btn_redefinir: ImageView
    private lateinit var btn_lembrete: Button
    private lateinit var btn_alarme: Button
    private lateinit var tv_hora: TextView
    private lateinit var tv_minutos: TextView

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultadoMl = 0.0

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual = 0
    var minutoAtual = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        iniciarComponentes()
        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        btn_calcular.setOnClickListener(){
            if(et_peso.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_informe_peso,Toast.LENGTH_SHORT).show()
            }else if (et_idade.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_informe_idade,Toast.LENGTH_SHORT).show()
            }else {
                val peso = et_peso.text.toString().toDouble()
                val idade = et_idade.text.toString().toInt()
                calcularIngestaoDiaria.CalcularTotalMl(peso, idade)
                resultadoMl = calcularIngestaoDiaria.ResultadoMl()
                val formatText = NumberFormat.getNumberInstance(Locale("pt","BR"))
                formatText.isGroupingUsed = false
                tv_resultadoMl.text = formatText.format(resultadoMl)+" L"
            }
        }

        btn_redefinir.setOnClickListener(){
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton("Ok") { dialogInterface, i ->
                    et_peso.setText("")
                    et_idade.setText("")
                    tv_resultadoMl.text = ""
                }
            alertDialog.setNegativeButton("Cancelar") { dialogInterface, i ->
            }
            val dialog = alertDialog.create()
            dialog.show()
        }

        btn_lembrete.setOnClickListener(){
            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutoAtual = calendario.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this,{timePicker: TimePicker, hourOfDay: Int, minutes: Int ->
                tv_hora.text = String.format("%02d", hourOfDay)
                tv_minutos.text = String.format("%02d", minutes)
            },horaAtual,minutoAtual,true)
            timePickerDialog.show()
        }

        btn_alarme.setOnClickListener(){
            if(!tv_hora.text.toString().isEmpty() && !tv_minutos.text.toString().isEmpty()){
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, tv_hora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, tv_minutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE,getString(R.string.alarme_messge))
                startActivity(intent)

                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }else {
                    Toast.makeText(this,"Ajuste as configurações de Data e Hora para o formato 24hrs",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun iniciarComponentes(){
        et_peso = findViewById(R.id.et_peso)
        et_idade = findViewById(R.id.et_idade)
        btn_calcular = findViewById(R.id.btn_calcular)
        tv_resultadoMl = findViewById(R.id.tv_resultadoMl)
        btn_redefinir = findViewById(R.id.btn_redefinir)
        btn_lembrete = findViewById(R.id.btn_lembrete)
        btn_alarme = findViewById(R.id.btn_alarme)
        tv_hora = findViewById(R.id.txt_hora)
        tv_minutos = findViewById(R.id.txt_minutos)

    }
}