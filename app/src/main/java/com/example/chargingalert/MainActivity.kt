package com.example.chargingalert

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat

class MainActivity() : AppCompatActivity() {

    lateinit var batteryPercentage:TextView

    lateinit var batterStatus:ImageView

    lateinit var switchRing:SwitchCompat

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        batteryPercentage = findViewById(R.id.batteryPercentage)
        batterStatus = findViewById(R.id.batteryStatus)
        switchRing = findViewById(R.id.switchForRing)

        registerReceiver(MyReceiver(batteryPercentage,batterStatus,switchRing),IntentFilter(Intent.ACTION_BATTERY_CHANGED));


    }
}