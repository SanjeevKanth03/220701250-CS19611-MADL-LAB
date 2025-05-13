package com.example.storetracker

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var imageView: ImageView
    private var receiptImagePath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtPrice = findViewById<EditText>(R.id.edtPrice)
        val edtPurchaseDate = findViewById<EditText>(R.id.edtPurchaseDate)
        val edtWarrantyMonths = findViewById<EditText>(R.id.edtWarrantyMonths)
        val btnCaptureImage = findViewById<Button>(R.id.btnCaptureImage)
        val btnSave = findViewById<Button>(R.id.btnSave)
        imageView = findViewById(R.id.imageView)
        val btnViewItems = findViewById<Button>(R.id.btnViewItems)

        btnViewItems.setOnClickListener {
            val items = dbHelper.getAllItems()
            if (items.isEmpty()) {
                Toast.makeText(this, "No items found in the database.", Toast.LENGTH_SHORT).show()
            } else {
                val builder = StringBuilder()
                for (item in items) {
                    builder.append("Name: ${item.name}\n")
                    builder.append("Price: ${item.price}\n")
                    builder.append("Purchase Date: ${item.purchaseDate}\n")
                    builder.append("Warranty (months: ${item.warrantyMonths}\n")
                    builder.append("Receipt Image Path: ${item.receiptImagePath}\n\n")
                }

                AlertDialog.Builder(this)
                    .setTitle("Saved Items")
                    .setMessage(builder.toString())
                    .setPositiveButton("OK", null)
                    .show()
            }
        }


        edtPurchaseDate.setOnClickListener {
            showDatePicker(edtPurchaseDate)
        }

        btnCaptureImage.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 1)
        }

        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val price = edtPrice.text.toString()
            val purchaseDate = edtPurchaseDate.text.toString()
            val warrantyMonths = edtWarrantyMonths.text.toString()

            if (name.isEmpty() || price.isEmpty() || purchaseDate.isEmpty() || warrantyMonths.isEmpty() || receiptImagePath.isEmpty()) {
                Toast.makeText(this, "Please fill all fields and capture receipt", Toast.LENGTH_SHORT).show()
            } else {
                val success = dbHelper.insertItem(
                    name,
                    price.toDouble(),
                    purchaseDate,
                    warrantyMonths.toInt(),
                    receiptImagePath
                )
                if (success) {
                    Toast.makeText(this, "Item saved!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error saving item", Toast.LENGTH_SHORT).show()
                }
            }
        }

        checkExpiringWarranties()
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "${year}-${month + 1}-${dayOfMonth}"
                editText.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
            saveImageToInternalStorage(bitmap)
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap) {
        val filename = "receipt_${System.currentTimeMillis()}.jpg"
        val file = File(filesDir, filename)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        receiptImagePath = file.absolutePath
    }

    private fun checkExpiringWarranties() {
        val items = dbHelper.getAllItems()
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = Calendar.getInstance()

        val expiringItems = items.filter {
            val purchaseDate = sdf.parse(it.purchaseDate)
            val expiryDate = Calendar.getInstance()
            expiryDate.time = purchaseDate!!
            expiryDate.add(Calendar.MONTH, it.warrantyMonths)

            val diff = expiryDate.timeInMillis - today.timeInMillis
            diff in 0..(7 * 24 * 60 * 60 * 1000) // within 7 days
        }

        if (expiringItems.isNotEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Warranty Expiry Alert")
                .setMessage("Some items' warranties are about to expire soon!")
                .setPositiveButton("OK", null)
                .show()
        }
    }
}
