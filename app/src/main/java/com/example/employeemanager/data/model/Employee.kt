package com.example.employeemanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Update

@Parcelize
@Entity(tableName = "employee_table")
data class Employee(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val department: String,
    val designation: String
) : Parcelable



