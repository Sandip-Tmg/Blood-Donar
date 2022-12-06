package com.example.blood_donar

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: android.content.Context) : SQLiteOpenHelper(context,"DonarDatabase",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS(Name TEXT, Email TEXT PRIMARY KEY, Latitude TEXT, Longitude TEXT, Contact TEXT, age INT, BloodType TEXT,SexType TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    @SuppressLint("Range")
    fun getDetails() : ArrayList<DonarModel> {
        var list: ArrayList<DonarModel> = ArrayList()
        var name:String
        var email:String
        var latitude:String
        var longitude: String
        var contact: String
        var age: Int
        var bloodType: String
        var sexType: String

        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM USERS",null)
        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    name = cursor.getString(cursor.getColumnIndex("Name"))
                    email = cursor.getString(cursor.getColumnIndex("Email"))
                    latitude = cursor.getString(cursor.getColumnIndex("Latitude"))
                    longitude = cursor.getString(cursor.getColumnIndex("Longitude"))
                    contact = cursor.getString(cursor.getColumnIndex("Contact"))
                    age = cursor.getInt(cursor.getColumnIndex("age"))
                    bloodType = cursor.getString(cursor.getColumnIndex("BloodType"))
                    sexType = cursor.getString(cursor.getColumnIndex("SexType"))

                    val donarModel = DonarModel(name,email,latitude,longitude,contact,age,bloodType,sexType)

                    list.add(donarModel)

                }while (cursor.moveToNext())
            }
        }
        return list
    }

    @SuppressLint("Range")
    fun getDetailsWithBloodType(blood: String?) : ArrayList<DonarModel> {
        var list: ArrayList<DonarModel> = ArrayList()
        var name:String
        var email:String
        var latitude:String
        var longitude: String
        var contact: String
        var age: Int
        var bloodType: String
        var sexType: String

        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM USERS WHERE BloodType='$blood'",null)
        if (cursor != null) {
            if(cursor.moveToFirst()){
                do {
                    name = cursor.getString(cursor.getColumnIndex("Name"))
                    email = cursor.getString(cursor.getColumnIndex("Email"))
                    latitude = cursor.getString(cursor.getColumnIndex("Latitude"))
                    longitude = cursor.getString(cursor.getColumnIndex("Longitude"))
                    contact = cursor.getString(cursor.getColumnIndex("Contact"))
                    age = cursor.getInt(cursor.getColumnIndex("age"))
                    bloodType = cursor.getString(cursor.getColumnIndex("BloodType"))
                    sexType = cursor.getString(cursor.getColumnIndex("SexType"))

                    val donarModel = DonarModel(name,email,latitude,longitude,contact,age,bloodType,sexType)

                    list.add(donarModel)

                }while (cursor.moveToNext())
            }
        }

        return list
    }


}