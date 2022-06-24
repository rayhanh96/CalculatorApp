package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput : TextView? = null
    private var numberPressed : Boolean = false
    private var decimalPressed : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun OnDigit(view : View){

        tvInput?.append((view as Button).text)
        numberPressed = true
    }

    fun OnClear(view : View){

        tvInput?.text = ""
        decimalPressed = false
        numberPressed = false
    }

    fun OnDecimal(view : View){

        if(numberPressed && !decimalPressed){
            tvInput?.append(".")
            decimalPressed = true
        }
    }

    fun OnOperator(view : View){
        tvInput?.text?.let{

            if(numberPressed && !OperatorIsEntered((it.toString()))){

                tvInput?.append((view as Button).text)
            }

            numberPressed = false
            decimalPressed = false
        }
    }

    fun OnEqual(view : View){

        if(numberPressed){

            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try{
                if(tvValue.startsWith("-")){
                    tvValue = tvValue.substring(1)
                    prefix = "-"
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = RemoveZeroAfterDecimal((one.toDouble() - two.toDouble()).toString())
                }

                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = RemoveZeroAfterDecimal((one.toDouble() + two.toDouble()).toString())
                }

                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = RemoveZeroAfterDecimal((one.toDouble() * two.toDouble()).toString())
                }

                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvInput?.text = RemoveZeroAfterDecimal((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun RemoveZeroAfterDecimal(result : String) : String{

        var value = result

        if(result.contains(".0")){

            value = result.substring(0, result.length - 2)
        }

        return value
    }

    private fun OperatorIsEntered(operator : String) : Boolean{

        return if(operator.startsWith("-")){
            false
        }else{
            operator.contains("+") || operator.contains("-") || operator.contains("*") || operator.contains("/")
        }
    }
}