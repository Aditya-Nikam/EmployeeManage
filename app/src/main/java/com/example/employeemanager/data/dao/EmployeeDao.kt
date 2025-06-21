package com.example.employeemanager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.employeemanager.data.model.Employee

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insertEmployee(employee: Employee)

    @Query("SELECT * FROM employee_table") fun getAllEmployees(): LiveData<List<Employee>>

    @Update
    suspend fun update(employee: Employee)

    @Delete
    suspend fun delete(employee: Employee)

    @Query("SELECT * FROM employee_table WHERE id = :id")
    fun getEmployeeById(id: Int): LiveData<Employee>

}