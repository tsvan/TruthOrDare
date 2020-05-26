package tsvan.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_question.*
import tsvan.com.infrastructure.DbConnect

class AddQuestionActivity : AppCompatActivity() {

    private var dbConnect: DbConnect? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
        dbConnect = DbConnect(this)

        val packageId = intent.extras!!.getInt("id")

        val question = findViewById<EditText>(R.id.questionEditText)

        playerOneButton.setOnClickListener {
            question.setText(StringBuilder(question.text).insert( question.selectionStart, "%PLAYER1%").toString())
        }

        playerTwoButton.setOnClickListener {
            question.setText(StringBuilder(question.text).insert( question.selectionStart, "%PLAYER2%").toString())
        }

        addQuestion.setOnClickListener {
            dbConnect!!.addQuestion(packageId, question.text.toString())
            Toast.makeText(applicationContext, "Вопрос добавлен", Toast.LENGTH_SHORT).show()
            question.text.clear()
        }
    }
}
