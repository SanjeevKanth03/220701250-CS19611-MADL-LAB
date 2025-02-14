package com.example.myapp

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.R

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView)
        val changeButton: Button = findViewById(R.id.changeButton)
        val toastButton: Button = findViewById(R.id.toastButton)


        changeButton.setOnClickListener {
            textView.setTextSize(35f)
            textView.setTextColor(Color.RED)
            Toast.makeText(this, "Text color changed to Red", Toast.LENGTH_SHORT).show()
        }
        toastButton.setOnClickListener {
            Toast.makeText(this, "Text color changed to Red", Toast.LENGTH_SHORT).show()
        }
    }
}
