package com.example.vcs_project2

import android.content.Intent
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDeviceInfo = findViewById<MaterialButton>(R.id.btnDeviceInfo)
        val btnSearchApp = findViewById<MaterialButton>(R.id.btnAppSearch)

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
