package com.graxa.drinkwaterapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.graxa.drinkwaterapp.Model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var et_peso: EditText
    private lateinit var et_idade: EditText
    private lateinit var btn_calcular: Button
    private lateinit var tv_resultadoMl: TextView
    private lateinit var btn_redefinir: ImageView

    private lateinit var calcularIngestaoDiaria: CalcularIngestaoDiaria
    private var resultadoMl = 0.0

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

    }

    private fun iniciarComponentes(){
        et_peso = findViewById(R.id.et_peso)
        et_idade = findViewById(R.id.et_idade)
        btn_calcular = findViewById(R.id.btn_calcular)
        tv_resultadoMl = findViewById(R.id.tv_resultadoMl)
        btn_redefinir = findViewById(R.id.btn_redefinir)

    }
}