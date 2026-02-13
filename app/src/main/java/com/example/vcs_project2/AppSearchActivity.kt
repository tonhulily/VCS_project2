package com.example.vcs_project2

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AppSearchActivity : AppCompatActivity() {

    private lateinit var edtSearch: EditText
    private lateinit var btnSearch: Button
    private lateinit var listViewApps: ListView

    private lateinit var installedApps: List<ApplicationInfo>
    private lateinit var adapter: ArrayAdapter<String>
    private val resultList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_search)

        // Ánh xạ view
        edtSearch = findViewById(R.id.edtSearch)
        btnSearch = findViewById(R.id.btnSearch)
        listViewApps = findViewById(R.id.listViewApps)

        // Lấy danh sách ứng dụng đã cài
        installedApps = packageManager.getInstalledApplications(0)

        // Khởi tạo adapter
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            resultList
        )
        listViewApps.adapter = adapter

        // Sự kiện click nút tìm kiếm
        btnSearch.setOnClickListener {
            searchApps()
        }

        // Sự kiện click mở ứng dụng
        listViewApps.setOnItemClickListener { _, _, position, _ ->
            openApp(resultList[position])
        }
    }

    private fun searchApps() {
        val keyword = edtSearch.text.toString().trim().lowercase()

        resultList.clear()

        if (keyword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập từ khóa", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
            return
        }

        for (app in installedApps) {

            // Bỏ qua app hệ thống nếu muốn (có thể bỏ đoạn này nếu muốn hiển thị tất cả)
            if (packageManager.getLaunchIntentForPackage(app.packageName) == null) {
                continue
            }

            val appName = packageManager
                .getApplicationLabel(app)
                .toString()

            if (appName.lowercase().contains(keyword)) {
                resultList.add(appName)
            }
        }

        if (resultList.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy ứng dụng", Toast.LENGTH_SHORT).show()
        }

        adapter.notifyDataSetChanged()
    }

    private fun openApp(appName: String) {
        for (app in installedApps) {
            val name = packageManager
                .getApplicationLabel(app)
                .toString()

            if (name == appName) {
                val intent: Intent? =
                    packageManager.getLaunchIntentForPackage(app.packageName)

                if (intent != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "Không thể mở ứng dụng này",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                break
            }
        }
    }
}
