package tsvan.com.infrastructure

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//https://demonuts.com/kotlin-sqlite-database/
class DbConnect(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION)
{
    companion object {

        var DATABASE_NAME = "trust_or+dare"
        private val DATABASE_VERSION = 1
        private val TABLE_PACKAGES = "packages"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"

        /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/
        private val CREATE_TABLE_PACKAGES = ("CREATE TABLE "
                + TABLE_PACKAGES + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT );")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_PACKAGES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_PACKAGES'")
        onCreate(db)
    }

    fun addPackage(packageName: String): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(KEY_NAME, packageName)
        // insert row in students table

        return db.insert(TABLE_PACKAGES, null, values)
    }

}