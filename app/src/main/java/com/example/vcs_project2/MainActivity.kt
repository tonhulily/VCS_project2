package com.example.vcs_project2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ Button từ layout
        val btnDeviceInfo = findViewById<Button>(R.id.btnDeviceInfo)
        val btnSearchApp = findViewById<Button>(R.id.btnSearchApp)

        // Button 1: Thông tin thiết bị
        btnDeviceInfo.setOnClickListener {
            val intent = Intent(this, DeviceInfoActivity::class.java)
            startActivity(intent)
        }

        // Button 2: Tìm kiếm ứng dụng
        btnSearchApp.setOnClickListener {
            val intent = Intent(this, AppSearchActivity::class.java)
            startActivity(intent)
        }
    }
}
