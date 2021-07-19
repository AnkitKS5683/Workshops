package com.example.workshops.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import com.example.workshops.models.WorkshopModel

object WorkshopTable {
    private const val TABLE_NAME = "Workshops"

    object Columns {
        const val ID = "id"
        const val IMAGE_ID = "image_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val DATE_N_TIME = "dateNTime"
    }

    val CMD_CREATE_TABLE = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME
        (
        ${Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Columns.IMAGE_ID} INTEGER,
        ${Columns.TITLE} TEXT,
        ${Columns.DESCRIPTION} TEXT,
        ${Columns.DATE_N_TIME} TEXT
        );
        """.trimIndent()

    fun insertWorkshop(db: SQLiteDatabase, workshop: WorkshopModel) {
        val row = ContentValues()
        row.put(Columns.IMAGE_ID, workshop.imageId)
        row.put(Columns.TITLE, workshop.title)
        row.put(Columns.DESCRIPTION, workshop.description)
        row.put(Columns.DATE_N_TIME, workshop.dateNTime)

        db.insert(TABLE_NAME, null, row)
    }

    fun getAllWorkshops(db: SQLiteDatabase): ArrayList<WorkshopModel> {
        val workshops = ArrayList<WorkshopModel>()
        val c = db.query(
            TABLE_NAME,
            null,
            null, null,
            null, null,
            null
        )

        while (c.moveToNext()) {
            val workshop = WorkshopModel(
                c.getInt(1), c.getString(2), c.getString(3), c.getString(4),
                c.getInt(0)
            )
            workshops.add(workshop)
        }

        c.close()
        return workshops
    }

    fun getAppliedWorkShops(
        db: SQLiteDatabase,
        appliedWorkshopsIds: MutableSet<String>
    ): ArrayList<WorkshopModel> {
        val workshops = ArrayList<WorkshopModel>()

        val selectionArgs = appliedWorkshopsIds.toTypedArray()
        val params = ArrayList<String>()

        for (i in 0..selectionArgs.size) {
            params.add("?")
        }

        val selection = "${Columns.ID} in (${TextUtils.join(",", params)})"

        val c = db.query(
            TABLE_NAME,
            null,
            selection, selectionArgs,
            null, null,
            null
        )

        while (c.moveToNext()) {
            val workshop = WorkshopModel(
                c.getInt(1), c.getString(2), c.getString(3), c.getString(4),
                c.getInt(0)
            )
            workshops.add(workshop)
        }

        c.close()
        return workshops
    }
}