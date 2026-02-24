package com.example.vcs_project2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDeviceInfo = findViewById<Button>(R.id.btnDeviceInfo)
        val btnSearchApp = findViewById<Button>(R.id.btnAppSearch)

        btnDeviceInfo.setOnClickListener {
            val intent = Intent(this, DeviceInfoActivity::class.java)
            startActivity(intent)
        }
        btnSearchApp.setOnClickListener {
            val intent = Intent(this, AppSearchActivity::class.java)
            startActivity(intent)
        }
    }
}
