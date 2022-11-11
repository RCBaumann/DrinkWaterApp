package com.graxa.drinkwaterapp.Model

class CalcularIngestaoDiaria {

    private val mlJovem = 40.0
    private val mlAdulto = 35.0
    private val mlIdoso = 30.0
    private val mlSessentaMais = 25.0

    private var resultadoMl = 0.0
    private var resultadoTotalMl = 0.0

    fun CalcularTotalMl (peso: Double, idade: Int){
        if(idade <= 17){
            resultadoMl = peso * mlJovem
            resultadoTotalMl = resultadoMl
        }else if(idade <= 55){
            resultadoMl = peso * mlAdulto
            resultadoTotalMl = resultadoMl
        }else if(idade <= 65){
            resultadoMl = peso * mlIdoso
            resultadoTotalMl = resultadoMl
        }else{
            resultadoMl = peso * mlSessentaMais
            resultadoTotalMl = resultadoMl
        }

    }

    fun ResultadoMl(): Double {
        return resultadoTotalMl / 1000
    }

}