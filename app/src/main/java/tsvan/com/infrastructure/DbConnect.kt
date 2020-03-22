package tsvan.com.infrastructure

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import tsvan.com.models.GamePackage
import tsvan.com.models.Question

class DbConnect(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        var DATABASE_NAME = "trust_or_dare"
        private const val DATABASE_VERSION = 1
        private const val TABLE_PACKAGES = "packages"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"

        private const val TABLE_QUESTIONS = "questions"
        private const val KEY_PACKAGE_ID = "package_id"
        private const val KEY_QUESTION = "text"

        private const val CREATE_TABLE_QUESTIONS = ("CREATE TABLE "
                + TABLE_QUESTIONS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_QUESTION + " TEXT ," + KEY_PACKAGE_ID + " INTEGER );")

        private const val CREATE_TABLE_PACKAGES = ("CREATE TABLE "
                + TABLE_PACKAGES + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT );")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_PACKAGES)
        db.execSQL(CREATE_TABLE_QUESTIONS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_PACKAGES'")
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_QUESTIONS'")
        onCreate(db)
    }

    fun addPackage(packageName: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, packageName)
        return db.insert(TABLE_PACKAGES, null, values)
    }

    fun deletePackage(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_PACKAGES, "id = ?", arrayOf(id.toString()))
    }

    @SuppressLint("Recycle")
    fun getAllPackages(): ArrayList<GamePackage> {
        val questionPackages = ArrayList<GamePackage>()
        var name: String
        var id: String
        val selectQuery = "SELECT  * FROM $TABLE_PACKAGES"
        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                name = c.getString(c.getColumnIndex(KEY_NAME))
                id = c.getString(c.getColumnIndex(KEY_ID))
                questionPackages.add(GamePackage(id.toInt(), name))
            } while (c.moveToNext())
        }
        return questionPackages
    }

    //----------------------------------

    fun addQuestion(packageId: Int, question: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_QUESTION, question)
        values.put(KEY_PACKAGE_ID, packageId)
        return db.insert(TABLE_QUESTIONS, null, values)
    }

    fun deleteQuestion(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_QUESTIONS, "id = ?", arrayOf(id.toString()))
    }

    @SuppressLint("Recycle")
    fun getPackageQuestions(packageId: Int): ArrayList<Question> {
        val questionPackages = ArrayList<Question>()
        var text: String
        var id: String
        val selectQuery = "SELECT  * FROM $TABLE_QUESTIONS WHERE $KEY_PACKAGE_ID = $packageId"
        val db = this.readableDatabase
        val c = db.rawQuery(selectQuery, null)
        if (c.moveToFirst()) {
            do {
                text = c.getString(c.getColumnIndex(KEY_QUESTION))
                id = c.getString(c.getColumnIndex(KEY_ID))
                questionPackages.add(Question(id.toInt(), text, packageId))
            } while (c.moveToNext())
        }
        return questionPackages
    }
}
