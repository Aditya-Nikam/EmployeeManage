package com.example.employeemanager.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.employeemanager.data.db.AppDatabase
import com.example.employeemanager.data.model.Employee
import com.example.employeemanager.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EmployeeRepository
    val allEmployees: LiveData<List<Employee>>

    init {
        val dao = AppDatabase.getDatabase(application).employeeDao()
        repository = EmployeeRepository(dao)
        allEmployees = repository.employees
    }

    fun addEmployee(emp: Employee) = viewModelScope.launch {
        repository.addEmployee(emp)
    }


}
