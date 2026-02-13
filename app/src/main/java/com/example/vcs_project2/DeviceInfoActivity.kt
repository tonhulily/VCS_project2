package com.example.vcs_project2

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DeviceInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_info)

        val tvModel = findViewById<TextView>(R.id.tvModel)
        val tvDevice = findViewById<TextView>(R.id.tvDevice)
        val tvVersion = findViewById<TextView>(R.id.tvVersion)

        tvModel.text = Build.MODEL
        tvDevice.text = Build.DEVICE
        tvVersion.text = Build.VERSION.RELEASE
    }
}
