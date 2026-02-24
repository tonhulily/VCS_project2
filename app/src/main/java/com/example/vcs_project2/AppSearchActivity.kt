package com.example.vcs_project2

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AppSearchActivity : AppCompatActivity() {

    private lateinit var edtSearch: EditText
    private lateinit var listViewApps: ListView
    private lateinit var tvEmpty: TextView

    private lateinit var installedApps: List<ApplicationInfo>
    private val resultList = mutableListOf<ApplicationInfo>()
    private lateinit var adapter: AppAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_search)

        edtSearch = findViewById(R.id.edtSearch)
        listViewApps = findViewById(R.id.listViewApps)
        tvEmpty = findViewById(R.id.tvEmpty)

        installedApps = loadLaunchableApps()

        adapter = AppAdapter()
        listViewApps.adapter = adapter

        listViewApps.setOnItemClickListener { _, _, position, _ ->
            openApp(resultList[position])
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchApps(s.toString())
            }
        })

        val btnBack = findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadLaunchableApps(): List<ApplicationInfo> {

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val resolveInfos: List<ResolveInfo> =
            packageManager.queryIntentActivities(intent, 0)

        val apps = resolveInfos.map {
            it.activityInfo.applicationInfo
        }

        return apps.sortedBy {
            packageManager.getApplicationLabel(it)
                .toString()
                .lowercase()
        }
    }

    private fun searchApps(keywordInput: String) {

        val keyword = keywordInput.trim().lowercase()
        resultList.clear()

        if (keyword.isEmpty()) {
            tvEmpty.visibility = View.GONE
            adapter.notifyDataSetChanged()
            return
        }

        val filtered = installedApps.filter { app ->
            val appName = packageManager
                .getApplicationLabel(app)
                .toString()
                .lowercase()

            appName.contains(keyword)
        }

        resultList.addAll(filtered)

        adapter.notifyDataSetChanged()

        tvEmpty.visibility =
            if (resultList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun openApp(app: ApplicationInfo) {

        val intent = packageManager
            .getLaunchIntentForPackage(app.packageName)

        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(
                this,
                "Không thể mở ứng dụng này",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    inner class AppAdapter : BaseAdapter() {

        override fun getCount(): Int = resultList.size

        override fun getItem(position: Int): Any = resultList[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View {

            val view = convertView ?: LayoutInflater
                .from(this@AppSearchActivity)
                .inflate(R.layout.item_app, parent, false)

            val imgIcon = view.findViewById<ImageView>(R.id.imgIcon)
            val tvName = view.findViewById<TextView>(R.id.tvName)

            val app = resultList[position]

            val appName = packageManager
                .getApplicationLabel(app)
                .toString()

            imgIcon.setImageDrawable(
                packageManager.getApplicationIcon(app)
            )

            tvName.text = appName

            return view
        }
    }
}