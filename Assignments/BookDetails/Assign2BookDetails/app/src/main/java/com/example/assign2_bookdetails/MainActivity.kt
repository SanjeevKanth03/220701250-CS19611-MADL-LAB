package com.example.assign2_bookdetails

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

        val etTitle:EditText=findViewById(R.id.etTitle)
        val etAuthor : EditText=findViewById(R.id.etAuthor)
        val etPublisher : EditText=findViewById(R.id.etPublisher)
        val etPrice : EditText =findViewById(R.id.etPrice)
        val btSave : Button =findViewById(R.id.btSave)
        val btLoad : Button=findViewById(R.id.btLoad)

        btSave.setOnClickListener {
            val title = etTitle.text.toString()
            val author = etAuthor.text.toString()
            val publisher = etPublisher.text.toString()
            val price = etPrice.text.toString()
            val file= File(getExternalFilesDir(null),"book_details.txt")
            val writer= FileWriter(file)
            writer.write("$title\n$author\n$publisher\n$price")
            writer.close()

            etTitle.text.clear()
            etAuthor.text.clear()
            etPublisher.text.clear()
            etPrice.text.clear()

            Toast.makeText(this,"Saved Successfully...!",Toast.LENGTH_LONG).show()
        }

        btLoad.setOnClickListener {
            val file=File(getExternalFilesDir(null),"book_details.txt")
            val reader = BufferedReader(FileReader(file))
            val title=reader.readLine()
            val author=reader.readLine()
            val publisher=reader.readLine()
            val price=reader.readLine()
            etTitle.setText(title)
            etAuthor.setText(author)
            etPublisher.setText(publisher)
            etPrice.setText(price)
            reader.close()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}