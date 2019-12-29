package com.zszurman.notatkazs

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns
import android.widget.Toast

object TableInfo : BaseColumns {

    const val DATABASE_NAME = "MyNotes"
    const val TABLE_NAME = "Notes"
    const val COL_ID = "ID"
    const val COL_TITLE = "Title"
    const val COL_DES = "Description"
    const val DATABASE_VERSION = 1
}

object BasicCommand {

    const val createTable: String =
        "CREATE TABLE IF NOT EXISTS " + TableInfo.TABLE_NAME + " (" + TableInfo.COL_ID +
                " INTEGER PRIMARY KEY, " +
                TableInfo.COL_TITLE + " TEXT, " +
                TableInfo.COL_DES + " TEXT);"

    //   var sqlDb:SQLiteDatabase? = null

    const val deleteTable = "DROP TABLE IF EXISTS " + TableInfo.TABLE_NAME
}

class DbHelper(val context: Context) :
    SQLiteOpenHelper(context, TableInfo.DATABASE_NAME, null, TableInfo.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(BasicCommand.createTable)
        Toast.makeText(this.context, "Baza Gotowa", Toast.LENGTH_SHORT).show()

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(BasicCommand.deleteTable)

    }

    fun insert(values: ContentValues): Long {

        val db = this.writableDatabase
        val ID = db!!.insert(TableInfo.TABLE_NAME, "", values)
        return ID
    }

    fun qery(
        projection: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        sortOrder: String
    ): Cursor {
        val db = this.writableDatabase
        val qb = SQLiteQueryBuilder()
        qb.tables = TableInfo.TABLE_NAME
        val cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        return cursor

    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        val db = this.writableDatabase
        val count = db!!.delete(TableInfo.TABLE_NAME, selection, selectionArgs)
        return count
    }
    fun update(values: ContentValues, selection:String, selectionArgs: Array<String>):Int {

        val db = this.writableDatabase

        val count = db!!.update(TableInfo.TABLE_NAME, values, selection, selectionArgs)
        return count
    }

}