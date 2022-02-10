package com.example.roblemejorado.databases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {


    private val TABLE_USERS="CREATE TABLE USERS(id INTEGER PRIMARY KEY, name TEXT, last_name TEXT, username Text, password TEXT," +
            "email TEXT)"



    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL(TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
        db?.execSQL("DROP TABLE IF EXISTS USER")

        db?.execSQL(TABLE_USERS)
    }
}
