package com.example.studentsqliteapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDBHelper(context: Context) : SQLiteOpenHelper(context, "StudentDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Student(roll_no INTEGER PRIMARY KEY, name TEXT, marks INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS Student")
        onCreate(db)
    }

    fun insertStudent(rollNo: Int, name: String, marks: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("roll_no", rollNo)
            put("name", name)
            put("marks", marks)
        }
        val result = db.insert("Student", null, contentValues)
        return result != -1L
    }

    fun updateStudent(rollNo: Int, name: String, marks: Int): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
            put("marks", marks)
        }
        val result = db.update("Student", contentValues, "roll_no=?", arrayOf(rollNo.toString()))
        return result > 0
    }

    fun deleteStudent(rollNo: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete("Student", "roll_no=?", arrayOf(rollNo.toString()))
        return result > 0
    }

    fun getAllStudents(): String {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Student", null)
        val buffer = StringBuffer()
        while (cursor.moveToNext()) {
            buffer.append("Roll No: ${cursor.getInt(0)}\n")
            buffer.append("Name: ${cursor.getString(1)}\n")
            buffer.append("Marks: ${cursor.getInt(2)}\n\n")
        }
        cursor.close()
        return buffer.toString()
    }
}
