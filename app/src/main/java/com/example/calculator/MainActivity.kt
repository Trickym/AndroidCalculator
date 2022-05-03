package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var resultInput:TextView?=null
    private var lastNumeric:Boolean = false
    private var lastDot:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultInput = findViewById(R.id.resultView)
    }
    fun onDigit(view: View){
        lastNumeric=true
        resultInput?.append((view as Button).text)
    }
    fun onClear(view: View){
        resultInput?.text="";
    }
    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            resultInput?.append((view as Button).text)
            lastNumeric=false
            lastDot=true
        }
    }
    fun onOperator(view:View){
        resultInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                resultInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false

            }
        }
    }
    private fun isOperatorAdded(value: String):Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
    fun onEqual(view: View){
        if(lastNumeric){
            var value = resultInput?.text.toString()
            var prefix=""
            try{
                if(value.startsWith("-")){
                    prefix = "-"
                    value = value.substring(1)
                }
                if(value.contains("-")){
                    val splitted = value.split('-')
                    var one = splitted[0]
                    if(prefix.isNotEmpty()){
                        one += prefix
                    }
                    var two = splitted[1]
                    resultInput?.text = removeZero((one.toDouble() - two.toDouble()).toString())
                }
                else if(value.contains("+")){
                    val splitted = value.split('+')
                    var one = splitted[0]
                    if(prefix.isNotEmpty()){
                        one += prefix
                    }
                    var two = splitted[1]
                    resultInput?.text = removeZero((one.toDouble() + two.toDouble()).toString())
                }
                else if(value.contains("*")){
                    val splitted = value.split('*')
                    var one = splitted[0]
                    if(prefix.isNotEmpty()){
                        one += prefix
                    }
                    var two = splitted[1]
                    resultInput?.text = removeZero((one.toDouble() * two.toDouble()).toString())
                }
                else if(value.contains("/")){
                    val splitted = value.split('/')
                    var one = splitted[0]
                    if(prefix.isNotEmpty()){
                        one += prefix
                    }
                    var two = splitted[1]
                    resultInput?.text = removeZero((one.toDouble() / two.toDouble()).toString())
                }
            }
            catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZero(value:String):String{
        var res = value
        if(value.contains(".0")){
            res = value.substring(0,value.length-2)
        }
        return  res
    }
}