package com.example.studentsqliteapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var dbHelper: StudentDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = StudentDBHelper(this)

        val etRollNo = findViewById<EditText>(R.id.etRollNo)
        val etName = findViewById<EditText>(R.id.etName)
        val etMarks = findViewById<EditText>(R.id.etMarks)
        val btnInsert = findViewById<Button>(R.id.btnInsert)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnView = findViewById<Button>(R.id.btnView)

        btnInsert.setOnClickListener {
            val success = dbHelper.insertStudent(
                etRollNo.text.toString().toInt(),
                etName.text.toString(),
                etMarks.text.toString().toInt()
            )
            showToast(if (success) "Inserted Successfully" else "Insert Failed")
        }

        btnUpdate.setOnClickListener {
            val success = dbHelper.updateStudent(
                etRollNo.text.toString().toInt(),
                etName.text.toString(),
                etMarks.text.toString().toInt()
            )
            showToast(if (success) "Updated Successfully" else "Update Failed")
        }

        btnDelete.setOnClickListener {
            val success = dbHelper.deleteStudent(etRollNo.text.toString().toInt())
            showToast(if (success) "Deleted Successfully" else "Delete Failed")
        }

        btnView.setOnClickListener {
            val data = dbHelper.getAllStudents()
            showDialog("Student Records", if (data.isEmpty()) "No Records Found" else data)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
