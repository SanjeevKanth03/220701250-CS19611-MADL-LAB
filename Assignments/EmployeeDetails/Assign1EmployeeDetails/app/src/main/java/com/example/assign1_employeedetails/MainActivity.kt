package com.example.assign1_employeedetails

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val etEmpId: EditText =findViewById(R.id.etEmpId)
        val etName : EditText=findViewById(R.id.etName)
        val etSalary : EditText=findViewById(R.id.etSalary)
        val btSave : Button =findViewById(R.id.btSave)
        val btLoad : Button=findViewById(R.id.btLoad)

        btSave.setOnClickListener {
            val empid = etEmpId.text.toString()
            val name = etName.text.toString()
            val salary = etSalary.text.toString()
            val file= File(getExternalFilesDir(null),"employee.txt")
            val writer= FileWriter(file)
            writer.write("$empid\n$name\n$salary")
            writer.close()

            etSalary.text.clear()
            etName.text.clear()
            etEmpId.text.clear()

            Toast.makeText(this,"Saved Successfully...!",Toast.LENGTH_LONG).show()
        }

        btLoad.setOnClickListener {
            val file=File(getExternalFilesDir(null),"employee.txt")
            val reader = BufferedReader(FileReader(file))
            val empid=reader.readLine()
            val name=reader.readLine()
            val salary=reader.readLine()
            etEmpId.setText(empid)
            etName.setText(name)
            etSalary.setText(salary)
            reader.close()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
