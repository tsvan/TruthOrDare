package tsvan.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import tsvan.com.models.Game

class GameSliderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_slider)

        val questionTextView: TextView = findViewById(R.id.currentQuestion)
        questionTextView.text = getString(R.string.InitQuestion)

        questionTextView.setOnClickListener {
            questionTextView.text = Game.instance.getNextQuestion()
        }
    }
}
