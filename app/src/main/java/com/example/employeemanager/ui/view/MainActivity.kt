package com.example.employeemanager.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.employeemanager.R
import com.example.employeemanager.ui.adapter.EmployeeAdapter
import com.example.employeemanager.ui.viewmodel.EmployeeViewModel
import java.io.File
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EmployeeAdapter
    private lateinit var viewModel: EmployeeViewModel
    private val CREATE_PDF_REQUEST_CODE = 1001
    private val CREATE_CSV_REQUEST_CODE = 1002


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Employee List"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEmployees)
        adapter = EmployeeAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // ViewModel
        viewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]
        viewModel.allEmployees.observe(this) { employees ->
            adapter.updateList(employees)
            findViewById<TextView>(R.id.count).text = employees.size.toString()
        }

        // FAB Add button
        findViewById<ImageButton>(R.id.addEmployee).setOnClickListener {
            val intent = Intent(this, AddEmployee::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.linkCsv).setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/csv"
                putExtra(Intent.EXTRA_TITLE, "employee_data.csv")
            }
            startActivityForResult(intent, CREATE_CSV_REQUEST_CODE)


        }


        findViewById<TextView>(R.id.linkPdf).setOnClickListener {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"
                putExtra(Intent.EXTRA_TITLE, "employee_data.pdf")
            }
            startActivityForResult(intent, CREATE_PDF_REQUEST_CODE)

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val uri = data.data ?: return
            when (requestCode) {
                CREATE_PDF_REQUEST_CODE -> exportPDFToUri(uri)
                CREATE_CSV_REQUEST_CODE -> exportCSVToUri(uri)
            }
        }
    }

    fun exportPDFToUri(uri: Uri) {
        val contentResolver = contentResolver
        val list = viewModel.allEmployees.value ?: return

        val document = PdfDocument()
        val paint = Paint()
        val titlePaint = Paint().apply {
            textSize = 18f
            isFakeBoldText = true
        }

        var y = 50
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = document.startPage(pageInfo)
        val canvas = page.canvas

        canvas.drawText("Employee List", 230f, y.toFloat(), titlePaint)
        y += 40

        paint.textSize = 12f
        for (emp in list) {
            val line1 = "${emp.id}. ${emp.firstName} ${emp.middleName} ${emp.lastName}"
            val line2 = "${emp.phone} | ${emp.email}"
            val line3 = "${emp.department} - ${emp.designation}"

            canvas.drawText(line1, 20f, y.toFloat(), paint)
            y += 20
            canvas.drawText(line2, 20f, y.toFloat(), paint)
            y += 20
            canvas.drawText(line3, 20f, y.toFloat(), paint)
            y += 30
        }

        document.finishPage(page)

        try {
            val out = contentResolver.openOutputStream(uri)
            document.writeTo(out!!)
            out.close()
            Toast.makeText(this, "PDF saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving PDF", Toast.LENGTH_SHORT).show()
        } finally {
            document.close()
        }
    }

    fun exportCSVToUri(uri: Uri) {
        val list = viewModel.allEmployees.value ?: return

        try {
            val out = contentResolver.openOutputStream(uri)
            val writer = out?.bufferedWriter()
            writer?.append("ID,First Name,Middle Name,Last Name,Phone,Email,Department,Designation\n")
            for (emp in list) {
                writer?.append("${emp.id},${emp.firstName},${emp.middleName},${emp.lastName},${emp.phone},${emp.email},${emp.department},${emp.designation}\n")
            }
            writer?.flush()
            writer?.close()
            Toast.makeText(this, "CSV saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error saving CSV", Toast.LENGTH_SHORT).show()
        }
    }

}
