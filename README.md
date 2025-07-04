# 👨‍💼 Employee Manager App

An Android application to manage employee data with full CRUD functionality and export options. Built using the **MVVM architecture** with Room, LiveData, and a clean UI.

---

## 🏗 Architecture – MVVM

- **Model**: Room Entity (`Employee`) and DAO (`EmployeeDao`)
- **ViewModel**: `EmployeeViewModel` exposes LiveData to the UI
- **View**: Activities (`MainActivity`, `AddEmployee`, `EmployeeDetailActivity`)
- **Repository**: Acts as the single source of truth between ViewModel and DAO

---

## ✨ Features

- Add, update, delete employees with form validation
- View detailed employee info
- Export data to CSV or PDF using system file picker
- Responsive and modern UI
- Live UI updates via Room + LiveData

---

## 📁 Project Structure

```plaintext
com.example.employeemanager/
├── data/
│   ├── dao/              # EmployeeDao.kt
│   ├── db/               # AppDatabase.kt
│   └── model/            # Employee.kt (Room entity + Parcelable)
│
├── repository/           # EmployeeRepository.kt
│
├── ui/
│   ├── adapter/          # EmployeeAdapter.kt
│   ├── view/             # Activities (MainActivity, EmployeeDetailsActivity, AddEmployeeActivity)
│   └── viewmodel/        # EmployeeViewModel.kt
│
├── res/                  # Layouts, drawables, etc.
└── AndroidManifest.xml
