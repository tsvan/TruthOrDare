package tsvan.com

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_package.tableLayout
import kotlinx.android.synthetic.main.activity_add_questions.*
import tsvan.com.infrastructure.DbConnect
import tsvan.com.models.Question


class AddQuestionsActivity : AppCompatActivity() {

    private var dbConnect: DbConnect? = null

    private var packageId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_questions)

        dbConnect = DbConnect(this)

        this.packageId = intent.extras!!.getInt("id")

        createQuestion.setOnClickListener {
            val intent = Intent(this, AddQuestionActivity::class.java)
            intent.putExtra("id", this.packageId)
            startActivity(intent)
        }

        back_button.setOnClickListener {
            val intent = Intent(this, AddPackageActivity::class.java)
            startActivity(intent)
        }
        this.renderQuestions()
    }

    override fun onRestart() {
        super.onRestart()
        this.renderQuestions()
    }

    private fun renderQuestions() {
        val questions = dbConnect!!.getPackageQuestions(this.packageId)
        val tableLayout = tableLayout.findViewById<TableLayout>(R.id.tableLayout)
        tableLayout.removeAllViews()

        for (q in questions) {
            this.addQuestionRow(q)
        }
    }

    private fun addQuestionRow(question: Question) {
        val tableLayout = tableLayout.findViewById<TableLayout>(R.id.tableLayout)
        val tableRow = TableRow(this)
        val rowIdText = TextView(this)
        val rowTextText = TextView(this)
        val buttonDelete = Button(this)
        rowIdText.text = question.id.toString()
        rowTextText.text = question.text
        tableRow.addView(rowIdText)
        tableRow.addView(rowTextText)
        buttonDelete.text = getString(R.string.delete)
        buttonDelete.setOnClickListener {
            dbConnect!!.deleteQuestion(question.id)
            this.renderQuestions()
        }
        tableRow.addView(buttonDelete)
        tableLayout.addView(tableRow)
    }
}
