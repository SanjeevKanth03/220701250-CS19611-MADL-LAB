package com.example.storetracker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class DBHelper(context: Context) : SQLiteOpenHelper(context, "store_inventory.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE items (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "price REAL, " +
                    "purchase_date TEXT, " +
                    "warranty_months INTEGER, " +
                    "receipt_image_path TEXT" +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS items")
        onCreate(db)
    }

    fun insertItem(
        name: String,
        price: Double,
        purchaseDate: String,
        warrantyMonths: Int,
        receiptImagePath: String
    ): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name", name)
        contentValues.put("price", price)
        contentValues.put("purchase_date", purchaseDate)
        contentValues.put("warranty_months", warrantyMonths)
        contentValues.put("receipt_image_path", receiptImagePath)
        val result = db.insert("items", null, contentValues)
        return result != -1L
    }

    fun getAllItems(): List<Item> {
        val items = mutableListOf<Item>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM items", null)
        if (cursor.moveToFirst()) {
            do {
                val item = Item(
                    id = cursor.getInt(0),
                    name = cursor.getString(1),
                    price = cursor.getDouble(2),
                    purchaseDate = cursor.getString(3),
                    warrantyMonths = cursor.getInt(4),
                    receiptImagePath = cursor.getString(5)
                )
                items.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return items
    }
}

data class Item(
    val id: Int,
    val name: String,
    val price: Double,
    val purchaseDate: String,
    val warrantyMonths: Int,
    val receiptImagePath: String
)
