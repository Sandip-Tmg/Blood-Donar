package com.example.blood_donar

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: android.content.Context) : SQLiteOpenHelper(context,"DonarDatabase",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS(Name TEXT, Email TEXT PRIMARY KEY, Latitude TEXT, Longitude TEXT, Contact TEXT, age INT, BloodType TEXT,SexType TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}