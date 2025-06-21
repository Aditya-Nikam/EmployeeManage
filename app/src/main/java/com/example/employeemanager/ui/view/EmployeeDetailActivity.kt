package com.example.employeemanager.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.employeemanager.R
import com.example.employeemanager.data.model.Employee
import com.example.employeemanager.ui.viewmodel.EmployeeViewModel

class EmployeeDetailActivity : AppCompatActivity() {

    private lateinit var employee: Employee
    private lateinit var viewModel: EmployeeViewModel
    private var employeeId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)

        // Handle edge insets (optional, for full screen support)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Receive Employee from intent
        employee = intent.getParcelableExtra("employee") ?: run {
            Toast.makeText(this, "Employee not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        employeeId = intent.getParcelableExtra<Employee>("employee")?.id ?: -1
        viewModel = ViewModelProvider(this)[EmployeeViewModel::class.java]

        if (employeeId != -1) {
            viewModel.getEmployeeById(employeeId).observe(this) { emp ->
                if (emp != null) {
                    bindEmployeeDetails(emp)
                }
            }
        }

        // Set data to views
        findViewById<TextView>(R.id.textDetailName).text =
            "${employee.firstName} ${employee.middleName} ${employee.lastName}"
        findViewById<TextView>(R.id.textDetailEmail).text = employee.email
        findViewById<TextView>(R.id.textDetailPhone).text = employee.phone
        findViewById<TextView>(R.id.textDetailDepartment).text = employee.department
        findViewById<TextView>(R.id.textDetailDesignation).text = employee.designation

        // Set up buttons
        findViewById<Button>(R.id.buttonUpdate).setOnClickListener {
            val intent = Intent(this, AddEmployee::class.java)
            intent.putExtra("employee", employee) // send full object
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonDelete).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Employee")
                .setMessage("Are you sure you want to delete this employee?")
                .setPositiveButton("Delete") { _, _ ->
                    viewModel.delete(employee)
                    Toast.makeText(this, "Employee deleted", Toast.LENGTH_SHORT).show()
                    finish() // go back to main activity
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

    }
    private fun bindEmployeeDetails(emp: Employee) {
        findViewById<TextView>(R.id.textDetailName).text =
            "${emp.firstName} ${emp.middleName} ${emp.lastName}"
        findViewById<TextView>(R.id.textDetailPhone).text = emp.phone
        findViewById<TextView>(R.id.textDetailEmail).text = emp.email
        findViewById<TextView>(R.id.textDetailDepartment).text = emp.department
        findViewById<TextView>(R.id.textDetailDesignation).text = emp.designation
    }

}
