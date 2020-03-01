package tsvan.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import tsvan.com.models.Game

class GameSliderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_slider)

        Toast.makeText(applicationContext, "singleton instance " + Game.instance.gamePackage!!.id.toString(), Toast.LENGTH_SHORT).show()

        val questionTextView: TextView = findViewById(R.id.currentQuestion) as TextView
        questionTextView.text = "click here"

        questionTextView.setOnClickListener {

            questionTextView.text = Game.instance.getNextQuestion()

        }


    }
}
