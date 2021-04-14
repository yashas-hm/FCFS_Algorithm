package com.yashas.algorithm.os

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.levitnudi.legacytableview.LegacyTableView


class MainActivity2 : AppCompatActivity() {

    private lateinit var originalTable: LegacyTableView
    private lateinit var calculatedTable: LegacyTableView
    private lateinit var wtAvg: AppCompatTextView
    private lateinit var tatAvg: AppCompatTextView
    private lateinit var process: ArrayList<Int>
    private lateinit var burstTime: ArrayList<Int>
    private lateinit var arrivalTime: ArrayList<Int>
    private lateinit var waitingTime: ArrayList<Int>
    private lateinit var turnAroundTime: ArrayList<Int>
    private lateinit var completionTime: ArrayList<Int>
    private lateinit var algorithm: FCFSAlgorithm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        setUp()
    }

    private fun setUp(){
        initUI()
        getOriginalData()
        createOriginalTable()
        getCalculatedData()
        createCalculatedData()
        getAverageTime()
    }

    private fun getAverageTime(){
        wtAvg.text = algorithm.averageWaitingTime.toString()
        tatAvg.text = algorithm.averageTurnAroundTime.toString()
    }

    private fun initUI(){
        originalTable = findViewById(R.id.original)
        calculatedTable = findViewById(R.id.calculated)
        wtAvg = findViewById(R.id.avgWt)
        tatAvg = findViewById(R.id.avgAt)
    }

    private fun getCalculatedData(){
        turnAroundTime = algorithm.turnAroundTime
        waitingTime = algorithm.waitingTime
        completionTime = algorithm.completionTime
        for(i in 0 until turnAroundTime.size ){
            LegacyTableView.insertLegacyContent("P${process[i]}")
            LegacyTableView.insertLegacyContent("${turnAroundTime[i]}")
            LegacyTableView.insertLegacyContent("${waitingTime[i]}")
            LegacyTableView.insertLegacyContent("${completionTime[i]}")
        }
    }

    private fun createCalculatedData(){
        LegacyTableView.insertLegacyTitle("Process", "Turn Around Time", "Waiting Time", "Completion Time")
        calculatedTable.setTitle(LegacyTableView.readLegacyTitle())
        calculatedTable.setContent(LegacyTableView.readLegacyContent())
        calculatedTable.setZoomEnabled(true)
        calculatedTable.setShowZoomControls(true)
        calculatedTable.setTableContentTextAlignment(2)
        calculatedTable.setContentTextAlignment(2)
        calculatedTable.setTitleTextSize(35)
        calculatedTable.setContentTextSize(40)
        calculatedTable.build()
    }

    private fun getOriginalData(){
        try{
            process = intent.getIntegerArrayListExtra("process")!!
            burstTime = intent.getIntegerArrayListExtra("burst")!!
            arrivalTime = intent.getIntegerArrayListExtra("arrival")!!
            algorithm = FCFSAlgorithm(process.size, process, burstTime, arrivalTime)

            for(i in 0 until process.size){
                LegacyTableView.insertLegacyContent("P${process[i]}")
                LegacyTableView.insertLegacyContent("${burstTime[i]}")
                LegacyTableView.insertLegacyContent("${arrivalTime[i]}")
            }
        }catch (e: Exception){
            println("error $e")
            Toast.makeText(this, "Some error occurred", Toast.LENGTH_LONG).show()
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun createOriginalTable(){
        LegacyTableView.insertLegacyTitle("Process", "Burst Time", "Arrival Time")
        originalTable.setTitle(LegacyTableView.readLegacyTitle())
        originalTable.setContent(LegacyTableView.readLegacyContent())
        originalTable.setZoomEnabled(true)
        originalTable.setShowZoomControls(true)
        originalTable.setTableContentTextAlignment(2)
        originalTable.setContentTextAlignment(2)
        originalTable.setTitleTextSize(35)
        originalTable.setContentTextSize(40)
        originalTable.build()
    }

    override fun onBackPressed() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
        super.onBackPressed()
    }
}