package tsvan.com

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.android.synthetic.main.activity_add_package.tableLayout
import kotlinx.android.synthetic.main.activity_add_questions.*
import tsvan.com.infrastructure.DbConnect
import tsvan.com.models.Question
import tsvan.com.repository.QuestionRepository

class AddQuestionsActivity : AppCompatActivity() {

    private var dbConnect : DbConnect? = null

    private var packageId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_questions)

        dbConnect = DbConnect(this)

        this.packageId = intent.extras!!.getInt("id")

        createQuestion.setOnClickListener{
            val intent = Intent(this, AddQuestionActivity::class.java)
            intent.putExtra("id", this.packageId)
            startActivity(intent)
        }
        this.renderQuestions()
    }

    private fun renderQuestions() {
        val questions = dbConnect!!.getPackageQuestions(this.packageId)

        for( q in questions){
            this.addQuestionRow(q)
        }
    }

    private fun addQuestionRow(question: Question) {
        var tableLayout = tableLayout.findViewById<TableLayout>(R.id.tableLayout)
        var tableRow  = TableRow(this)
        var rowIdText = TextView(this)
        var rowTextText = TextView(this)
        var buttonDelete = Button(this)
        rowIdText.setText(question.id.toString())
        rowTextText.setText(question.text)
        tableRow.addView(rowIdText)
        tableRow.addView(rowTextText)
        buttonDelete.setText("delete")
        buttonDelete.setOnClickListener {

            dbConnect!!.deleteQuestion(question.id)
        }
        tableRow.addView(buttonDelete)
        tableLayout.addView(tableRow)
    }
}
