package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val etEmailId : EditText = findViewById(R.id.etEmailId)
        val etPassWord : EditText = findViewById(R.id.etPassWord)
        val btValidate: Button = findViewById(R.id.btValidate)
        btValidate.setOnClickListener {
            val emailid = etEmailId.text.toString().trim()
            val password= etPassWord.text.toString().trim()
            //to check if the fields are not empty
            if(emailid.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this,"All fields are madatory",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //Accept only alphabets
            if(!emailid.matches(Regex("^[a-zA-Z0-9]+@rajalakshmi\\.edu\\.in\$\n"))){
                Toast.makeText(this,"Invalid UserName",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //accept only 4 digit of pin numbers
            if(!password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$\n"))){
                Toast.makeText(this,"Invalid PinNumber",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //navigate to success page
            val intent: Intent = Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        }
    }
