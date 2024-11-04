package com.example.apprelogio

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.apprelogio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isChecked = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val windowInsetsController: WindowInsetsControllerCompat =
            WindowInsetsControllerCompat(window, window.decorView)

        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())

        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val bateriaReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, p1: Intent?) {
                if (p1 != null) {
                    val nivel: Int = p1.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
                    //Toast.makeText(applicationContext, nivel.toString(), Toast.LENGTH_SHORT).show()
                    binding.textNivelBateria.text = "${nivel.toString()}%"

                }
            }

        }
        registerReceiver(bateriaReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        binding.checkNivelBateria.setOnClickListener {
            if (isChecked) {
                isChecked = false
                binding.textNivelBateria.visibility = View.GONE
            } else {
                isChecked = true
                binding.textNivelBateria.visibility = View.VISIBLE

            }
            binding.checkNivelBateria.isChecked = isChecked
        }

        binding.layoutMenu.animate().translationY(500F)

        binding.imageViewPreferencias.setOnClickListener {
            binding.layoutMenu.visibility = View.VISIBLE
            binding.layoutMenu.animate().translationY(0F)
                .setDuration(
                    resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()

                )
        }
        binding.imageViewFechar.setOnClickListener {
            binding.layoutMenu.animate().translationY(binding.layoutMenu.measuredHeight.toFloat())
                .setDuration(
                    resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
                )
        }
    }
}