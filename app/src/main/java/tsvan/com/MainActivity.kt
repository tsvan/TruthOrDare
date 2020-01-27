package tsvan.com

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGame.setOnClickListener {
            this.addPlayersAction()
        }
    }

    @SuppressLint("InflateParams")
    private fun addPlayersAction() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Add players")
        val dialogLayout = inflater.inflate(R.layout.add_players_dialog, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.playerName)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Add", null)
        builder.setNegativeButton("Close") { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.setNeutralButton("Next", null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val addButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            addButton.setOnClickListener {
                //dialog.dismiss()
                Toast.makeText(applicationContext, "Add - " + editText.text.toString(), Toast.LENGTH_SHORT).show()
                editText?.text?.clear()
            }

            val nextButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            nextButton.setOnClickListener {
                val intent = Intent(this, AddQuestionsActivity::class.java)
                startActivity(intent)
            }
        }

        dialog.show()
    }
}
