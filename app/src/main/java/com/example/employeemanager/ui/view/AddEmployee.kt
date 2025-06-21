package com.example.employeemanager.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.employeemanager.R
import com.example.employeemanager.data.model.Employee
import com.example.employeemanager.ui.viewmodel.EmployeeViewModel

class AddEmployee : AppCompatActivity() {

    private var existingEmployee: Employee? = null

    @SuppressLint("CutPasteId", "SetTextI18n")
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

        val saveButton = findViewById<Button>(R.id.saveButton) // Add id to your button
        val viewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]

        existingEmployee = intent.getParcelableExtra("employee")

        existingEmployee?.let { employee ->
            // Pre-fill form fields
            findViewById<EditText>(R.id.editTextFirstName).setText(employee.firstName)
            findViewById<EditText>(R.id.editTextMiddleName).setText(employee.middleName)
            findViewById<EditText>(R.id.editTextLastName).setText(employee.lastName)
            findViewById<EditText>(R.id.editTextPhone).setText(employee.phone)
            findViewById<EditText>(R.id.editTextEmail).setText(employee.email)
            findViewById<EditText>(R.id.editTextDepartment).setText(employee.department)
            findViewById<EditText>(R.id.editTextDesignation).setText(employee.designation)

            findViewById<Button>(R.id.saveButton).text = "UPDATE"
        }


        saveButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString().trim()
            val middleName = middleNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val dept = departmentEditText.text.toString().trim()
            val designation = designationEditText.text.toString().trim()

            if (firstName.isEmpty() || middleName.isEmpty() || lastName.isEmpty() ||
                phone.length != 10 || !email.contains("@") ||
                dept.isEmpty() || designation.isEmpty()) {

                Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show()

            } else {
                // Check if editing
                val isEditMode = existingEmployee != null

                val emp = Employee(
                    id = existingEmployee?.id ?: 0, // Preserve original ID if updating
                    firstName = firstName,
                    middleName = middleName,
                    lastName = lastName,
                    phone = phone,
                    email = email,
                    department = dept,
                    designation = designation
                )

                if (isEditMode) {
                    viewModel.update(emp)
                    Toast.makeText(this, "Employee updated", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addEmployee(emp)
                    Toast.makeText(this, "Employee added", Toast.LENGTH_SHORT).show()
                }

                finish() // Go back to MainActivity
            }
        }



    }
}