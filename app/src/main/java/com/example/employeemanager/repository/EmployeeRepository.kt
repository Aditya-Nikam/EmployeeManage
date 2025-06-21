package com.example.employeemanager.repository

import androidx.lifecycle.LiveData
import com.example.employeemanager.data.dao.EmployeeDao
import com.example.employeemanager.data.model.Employee

class EmployeeRepository(private val dao: EmployeeDao) {
    val employees: LiveData<List<Employee>> = dao.getAllEmployees()

    suspend fun addEmployee(employee: Employee) {
        dao.insertEmployee(employee)
    }

    suspend fun update(employee: Employee) = dao.update(employee)
    suspend fun delete(employee: Employee) = dao.delete(employee)

    fun getEmployeeById(id: Int): LiveData<Employee> = dao.getEmployeeById(id)



}
