package tsvan.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_add_question.*
import tsvan.com.infrastructure.DbConnect

class AddQuestionActivity : AppCompatActivity() {

    private var dbConnect : DbConnect? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
        dbConnect = DbConnect(this)


        val packageId = intent.extras!!.getInt("id")

        val editText  = this.findViewById<EditText>(R.id.editText)


        addQuestion.setOnClickListener{
           dbConnect!!.addQuestion(packageId, editText.text.toString() )
        }
    }
}
