package com.example.employeemanager.repository

import androidx.lifecycle.LiveData
import com.example.employeemanager.data.dao.EmployeeDao
import com.example.employeemanager.data.model.Employee

class EmployeeRepository(private val dao: EmployeeDao) {
    val employees: LiveData<List<Employee>> = dao.getAllEmployees()

    suspend fun addEmployee(employee: Employee) {
        dao.insertEmployee(employee)
    }
}
