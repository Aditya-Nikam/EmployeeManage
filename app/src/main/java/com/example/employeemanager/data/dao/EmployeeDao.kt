package com.example.employeemanager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.employeemanager.data.model.Employee

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insertEmployee(employee: Employee)

    @Query("SELECT * FROM employee_table") fun getAllEmployees(): LiveData<List<Employee>>
}