package com.example.vcs_project2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AppSearchActivity : AppCompatActivity() {

    private lateinit var edtKeyword: EditText
    private lateinit var btnSearch: Button
    private lateinit var listViewApps: ListView

    private val appNames = mutableListOf<String>()
    private val appPackages = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_search)

        edtKeyword = findViewById(R.id.edtKeyword)
        btnSearch = findViewById(R.id.btnSearch)
        listViewApps = findViewById(R.id.listViewApps)

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            appNames
        )
        listViewApps.adapter = adapter

        btnSearch.setOnClickListener {
            searchApps()
        }

        listViewApps.setOnItemClickListener { _, _, position, _ ->
            openApp(appPackages[position])
        }
    }

    private fun searchApps() {
        val keyword = edtKeyword.text.toString().lowercase()

        appNames.clear()
        appPackages.clear()

        val pm = packageManager
        val apps = pm.getInstalledApplications(0)

        for (app in apps) {
            val appName = pm.getApplicationLabel(app).toString()

            if (appName.lowercase().contains(keyword)) {
                appNames.add(appName)
                appPackages.add(app.packageName)
            }
        }

        adapter.notifyDataSetChanged()
    }

    private fun openApp(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Không mở được app", Toast.LENGTH_SHORT).show()
        }
    }
}
