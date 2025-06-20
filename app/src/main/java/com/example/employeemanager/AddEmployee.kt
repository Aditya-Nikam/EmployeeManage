package com.example.employeemanager

import android.os.Bundle
import android.text.Html
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddEmployee : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_employee)
        supportActionBar?.title = "New Employee"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val firstNameEditText = findViewById<EditText>(R.id.editTextFirstName)
        firstNameEditText.hint = Html.fromHtml("First Name<font color='#FF0000'>*</font>")

        val middleNameEditText = findViewById<EditText>(R.id.editTextMiddleName)
        middleNameEditText.hint = Html.fromHtml("Middle Name<font color='#FF0000'>*</font>")

        val lastNameEditText = findViewById<EditText>(R.id.editTextLastName)
        lastNameEditText.hint = Html.fromHtml("Last Name<font color='#FF0000'>*</font>")

        val phoneEditText = findViewById<EditText>(R.id.editTextPhone)
        phoneEditText.hint = Html.fromHtml("+91XXXXXXX86<font color='#FF0000'>*</font>")

        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        emailEditText.hint = Html.fromHtml("Email ID<font color='#FF0000'>*</font>")

        val departmentEditText = findViewById<EditText>(R.id.editTextDepartment)
        departmentEditText.hint = Html.fromHtml("Department<font color='#FF0000'>*</font>")

        val designationEditText = findViewById<EditText>(R.id.editTextDesignation)
        designationEditText.hint = Html.fromHtml("Designation<font color='#FF0000'>*</font>")



    }
}