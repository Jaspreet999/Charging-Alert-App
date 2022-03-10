package com.example.chargingalert

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.speech.tts.TextToSpeech
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.isVisible
import java.util.*

class MyReceiver(private val batteryLevel: TextView, private val batteryStatus: ImageView,private val switchRing:SwitchCompat)
    : BroadcastReceiver(){

    lateinit var textToSpeech: TextToSpeech


    @SuppressLint("UnsafeProtectedBroadcastReceiver", "SetTextI18n")
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.


        //level refers to the current battery status
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1)

        if(level!=0){
            batteryLevel.text = "$level%"
        }


        //it calculates the battery percentage
        when(intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1)){


            BatteryManager.BATTERY_STATUS_CHARGING-> {
                batteryStatus.isVisible = true

                //it alerts after every 10 percentage
                if(level%10 == 0 && level!=100 && switchRing.isChecked){
                    textToSpeech = TextToSpeech(context,TextToSpeech.OnInitListener {
                        textToSpeech.isLanguageAvailable(Locale.US)
                        textToSpeech.setSpeechRate(1.0f)
                        textToSpeech.speak("Battery is " + level.toString() + "percentage charged",
                                TextToSpeech.QUEUE_ADD, null)
                        })


                }

                batteryStatus.setOnClickListener{
                    Toast.makeText(context,"Battery is charging",Toast.LENGTH_SHORT).show()
                }
            }

            BatteryManager.BATTERY_STATUS_FULL-> {

                    batteryStatus.setImageResource(R.drawable.ic_baseline_battery_full_24)
                    textToSpeech = TextToSpeech(context,TextToSpeech.OnInitListener {
                    textToSpeech.isLanguageAvailable(Locale.US)
                    textToSpeech.setSpeechRate(2.0f)
                    textToSpeech.speak("Battery is charged, remove charger",TextToSpeech.QUEUE_ADD,null)
                })

                batteryStatus.setOnClickListener{
                    Toast.makeText(context,"Battery charged",Toast.LENGTH_SHORT).show()
                }
            }

            else ->
                batteryStatus.isVisible = false
        }
    }
}