package com.example.employeemanager.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.employeemanager.R
import com.example.employeemanager.data.model.Employee

class EmployeeAdapter(private var employeeList: List<Employee>) :
    RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    inner class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.textEmployeeId);
        val fullName: TextView = itemView.findViewById(R.id.textName)
        val phone: TextView = itemView.findViewById(R.id.textPhone)
        val email: TextView = itemView.findViewById(R.id.textEmail)
        val department: TextView = itemView.findViewById(R.id.textDepartment)
        val designation: TextView = itemView.findViewById(R.id.textDesignation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_employee_card, parent, false)
        return EmployeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employeeList[position]
        holder.id.text = "${employee.id}"
        holder.fullName.text = "${employee.firstName} ${employee.middleName} ${employee.lastName}"
        holder.phone.text = "${employee.phone}"
        holder.email.text = "${employee.email}"
        holder.department.text = "${employee.department}"
        holder.designation.text = "${employee.designation}"
    }

    override fun getItemCount(): Int = employeeList.size

    fun updateList(newList: List<Employee>) {
        employeeList = newList
        notifyDataSetChanged()
    }
}
