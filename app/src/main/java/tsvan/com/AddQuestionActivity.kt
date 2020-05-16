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

        val editText = findViewById<EditText>(R.id.questionEditText)

        playerOneButton.setOnClickListener {
            val position = editText.selectionStart
            val tmp = StringBuilder(editText.text).insert(position, "%PLAYER1%").toString()
            Toast.makeText(applicationContext, tmp, Toast.LENGTH_SHORT).show()
            editText.setText(tmp)
        }

        playerTwoButton.setOnClickListener {
            val position = editText.selectionStart
            val tmp = StringBuilder(editText.text).insert(position, "%PLAYER2%").toString()
            Toast.makeText(applicationContext, tmp, Toast.LENGTH_SHORT).show()
            editText.setText(tmp)
        }


        addQuestion.setOnClickListener {
            dbConnect!!.addQuestion(packageId, editText.text.toString())
        }
    }
}
