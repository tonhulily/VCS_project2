package com.example.vcs_project2

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DeviceInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_info)

        val tvDeviceInfo = findViewById<TextView>(R.id.tvDeviceInfo)

        val manufacturer = android.os.Build.MANUFACTURER
        val model = android.os.Build.MODEL
        val device = android.os.Build.DEVICE
        val androidVersion = android.os.Build.VERSION.RELEASE

        val info = """
            Loại máy: $manufacturer $model
            Tên máy: $device
            Phiên bản Android: $androidVersion
        """.trimIndent()

        tvDeviceInfo.text = info
    }
}
