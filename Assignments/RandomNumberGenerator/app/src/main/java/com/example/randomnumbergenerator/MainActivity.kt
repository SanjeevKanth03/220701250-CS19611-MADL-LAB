package com.example.randomnumbergenerator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val minEditText = findViewById<EditText>(R.id.minEditText)
        val maxEditText = findViewById<EditText>(R.id.maxEditText)
        val generateButton = findViewById<Button>(R.id.generateButton)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        generateButton.setOnClickListener {
            val minText = minEditText.text.toString()
            val maxText = maxEditText.text.toString()

            if (minText.isEmpty() || maxText.isEmpty()) {
                Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val min = minText.toInt()
            val max = maxText.toInt()

            if (min >= max) {
                Toast.makeText(this, "Min should be less than Max", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val randomNumber = Random.nextInt(min, max + 1)
            resultTextView.text = "Random Number: $randomNumber"
        }
    }
}
