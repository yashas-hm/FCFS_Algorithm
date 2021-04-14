package com.yashas.algorithm.os

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText


class MainActivity : AppCompatActivity() {

    private lateinit var submit: AppCompatButton
    private lateinit var btEt: AppCompatEditText
    private lateinit var atEt: AppCompatEditText
    private val process = arrayListOf<Int>()
    private val burstTime = arrayListOf<Int>()
    private val arrivalTime = arrayListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
    }

    private fun setUp(){
        initUI()
        listener()
    }

    private fun initUI(){
        submit = findViewById(R.id.submit)
        btEt = findViewById(R.id.btEt)
        atEt = findViewById(R.id.atEt)
    }

    private fun listener(){
        submit.setOnClickListener {
            validate()
        }
    }

    private fun validate(){
        val arrival = atEt.text.toString().split(",").map { it.trim() }
        val burst = btEt.text.toString().split(",").map { it.trim() }
        if(arrival.size != burst.size){
            AlertDialog.Builder(this)
                .setTitle("Size error")
                .setMessage("The numbers entered do not match")
                .setPositiveButton("ok"){_,_->
                    atEt.requestFocus()
                }
                .create()
                .show()
        }else{

            try{
                for(i in arrival.indices){
                    arrivalTime.add(arrival[i].toInt())
                    burstTime.add(burst[i].toInt())
                    process.add(i+1)
                }
                val intent = Intent(this@MainActivity,MainActivity2::class.java)
                intent.putIntegerArrayListExtra("process", process)
                intent.putIntegerArrayListExtra("arrival", arrivalTime)
                intent.putIntegerArrayListExtra("burst", burstTime)
                finish()
                startActivity(intent)
            }catch (e: Exception){
                println(e)
                AlertDialog.Builder(this)
                    .setTitle("Parse Error")
                    .setMessage("Some error occurred")
                    .setPositiveButton("ok"){_,_->
                        atEt.requestFocus()
                    }
                    .create()
                    .show()
            }
        }
    }
}
