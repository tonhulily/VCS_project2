package com.example.vcs_project2

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AppSearchActivity : AppCompatActivity() {

    private lateinit var edtSearch: EditText
    private lateinit var listViewApps: ListView
    private lateinit var emptyResult: TextView

    private lateinit var installedApps: List<ApplicationInfo>
    private val resultList = mutableListOf<ApplicationInfo>()
    private lateinit var adapter: AppAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_search)

        initViews()
        installedApps = loadApps()
        adapter = AppAdapter()
        listViewApps.adapter = adapter

        listViewApps.setOnItemClickListener { _, _, position, _ ->
            openApp(resultList[position])
        }
        searchListener()
        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
    private fun initViews() {
        edtSearch = findViewById(R.id.edtSearch)
        listViewApps = findViewById(R.id.listViewApps)
        emptyResult = findViewById(R.id.empty_result)
    }
    private fun loadApps(): List<ApplicationInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        return packageManager.queryIntentActivities(intent, 0)
            .map { it.activityInfo.applicationInfo }
            .distinctBy { it.packageName }
            .sortedBy { packageManager.getApplicationLabel(it).toString().lowercase() }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun searchListener() {
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchApps(s.toString())
            }
        })

        edtSearch.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = edtSearch.compoundDrawables[2]
                if (drawableEnd != null) {
                    if (event.rawX >= (edtSearch.right - edtSearch.compoundDrawables[2].bounds.width() - edtSearch.paddingEnd)) {
                        searchApps(edtSearch.text.toString())
                        v.performClick()
                        return@setOnTouchListener true
                    }
                }
            }
            false
        }
    }
    private fun searchApps(keywordInput: String) {
        val keyword = keywordInput.trim().lowercase()
        resultList.clear()

        if (keyword.isEmpty()) {
            emptyResult.visibility = View.GONE
            adapter.notifyDataSetChanged()
            return
        }

        val startsWithList = installedApps.filter { app ->
            packageManager.getApplicationLabel(app).toString().lowercase().startsWith(keyword)
        }

        val containsList = installedApps.filter { app ->
            val appName = packageManager.getApplicationLabel(app).toString().lowercase()
            appName.contains(keyword) && !appName.startsWith(keyword)
        }

        resultList.addAll(startsWithList)
        resultList.addAll(containsList)

        adapter.notifyDataSetChanged()
        emptyResult.visibility = if (resultList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun openApp(app: ApplicationInfo) {
        val intent = packageManager.getLaunchIntentForPackage(app.packageName)
        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Không thể mở ứng dụng này", Toast.LENGTH_SHORT).show()
        }
    }

    private class ViewHolder(view: View) {
        val imgIcon: ImageView = view.findViewById(R.id.imgIcon)
        val tvName: TextView = view.findViewById(R.id.tvName)
    }
    inner class AppAdapter : BaseAdapter() {
        override fun getCount(): Int = resultList.size
        override fun getItem(position: Int): Any = resultList[position]
        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val holder: ViewHolder

            if (convertView == null) {
                view = LayoutInflater.from(this@AppSearchActivity).inflate(R.layout.item_app, parent, false)
                holder = ViewHolder(view)
                view.tag = holder
            } else {
                view = convertView
                holder = view.tag as ViewHolder
            }

            val app = resultList[position]
            holder.tvName.text = packageManager.getApplicationLabel(app).toString()
            holder.imgIcon.setImageDrawable(packageManager.getApplicationIcon(app))

            return view
        }
    }
}