package tsvan.com.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import tsvan.com.models.GamePackage
import tsvan.com.models.Question

class QuestionRepository(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION)
{
    companion object {

        var DATABASE_NAME = "trust_or_dare"
        private val DATABASE_VERSION = 1
        private val TABLE_QUESTIONS = "questions"
        private val KEY_ID = "id"
        private val KEY_PACKAGE_ID = "package_id"
        private val KEY_QUESTION = "text"

        private val CREATE_TABLE_QUESTIONS = ("CREATE TABLE "
                + TABLE_QUESTIONS + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_QUESTION + " TEXT ," + KEY_PACKAGE_ID + " INTEGER );")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_QUESTIONS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS '$TABLE_QUESTIONS'")
        onCreate(db)
    }

    fun addQuestion(packageId: Int, question: String): Long {
        val db = this.writableDatabase
        // Creating content values
        val values = ContentValues()
        values.put(KEY_QUESTION, question)
        values.put(KEY_PACKAGE_ID, packageId)
        // insert row in students table

        return db.insert(TABLE_QUESTIONS, null, values)
    }

    fun deleteQuestion(id: Int): Int {
        val db = this.writableDatabase
        // Creating content values
        return db.delete(TABLE_QUESTIONS, "id = ?", arrayOf(id.toString()))
    }

    fun getPackageQuestions(packageId: Int) : ArrayList<Question> {
        val questionPackages = ArrayList<Question>()
        var text = ""
        var id = ""
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