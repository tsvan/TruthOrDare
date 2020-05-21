package tsvan.com

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import tsvan.com.models.Game
import tsvan.com.models.Player
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newGame.setOnClickListener {
            Game.instance.clear()
            this.addPlayersAction()
        }

        editPackage.setOnClickListener {
            val intent = Intent(this, AddPackageActivity::class.java)
            startActivity(intent)
        }

        continueGame.setOnClickListener {
            if (Game.instance.questions != null) {
                val intent = Intent(this, GameSliderActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Начните новую игру", Toast.LENGTH_SHORT).show()
            }
        }
        exit.setOnClickListener {
            finish()
            exitProcess(0)
        }

    }

    @SuppressLint("InflateParams")
    private fun addPlayersAction() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle(getString(R.string.addPlayers))
        val dialogLayout = inflater.inflate(R.layout.add_players_dialog, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.playerName)
        builder.setView(dialogLayout)
        builder.setPositiveButton(getString(R.string.Add), null)
        builder.setNegativeButton(getString(R.string.Close)) { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.setNeutralButton(getString(R.string.Next), null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val addButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            addButton.setOnClickListener {
                //dialog.dismiss()
                Toast.makeText(
                    applicationContext,
                    "Добавлен Игрок " + editText.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                Game.instance.players!!.add(Player(editText.text.toString()))
                editText?.text?.clear()
            }

            val nextButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            nextButton.setOnClickListener {
                if (Game.instance.players!!.count() >= 2) {
                    val intent = Intent(this, SelectPackageActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Нужно добавить более двух игроков",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        dialog.show()
    }
}
