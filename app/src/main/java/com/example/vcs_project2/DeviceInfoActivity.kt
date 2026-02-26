package com.example.vcs_project2

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DeviceInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_info)

        val model = findViewById<TextView>(R.id.model)
        val device = findViewById<TextView>(R.id.device)
        val version = findViewById<TextView>(R.id.version)

        model.text = Build.MODEL
        device.text = Build.DEVICE
        version.text = Build.VERSION.RELEASE

        val btnBack = findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }
    }
}
