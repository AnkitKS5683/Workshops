package com.example.workshops.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(
    context,
    "workshops.db",
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(WorkshopTable.CMD_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}