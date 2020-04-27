package tsvan.com

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_add_package.*
import tsvan.com.infrastructure.DbConnect
import tsvan.com.models.Game
import tsvan.com.models.GamePackage

class SelectPackageActivity : AppCompatActivity() {

    private var dbConnect : DbConnect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_package)

        dbConnect = DbConnect(this)

        this.renderPackages()
    }

    private fun renderPackages() {
        val packages = dbConnect!!.getAllPackages()

        for( pack in packages){
            this.addPackageRow(pack)
        }
    }


    private fun addPackageRow(gamePack: GamePackage) {
        val tableLayout = tableLayout.findViewById<TableLayout>(R.id.tableLayout)
        val tableRow  = TableRow(this)
        val rowIdText = TextView(this)
        val rowNameText = TextView(this)
        val buttonEdit = Button(this)
        rowIdText.text = gamePack.id.toString()
        rowNameText.text = gamePack.name
        tableRow.addView(rowIdText)
        tableRow.addView(rowNameText)

        buttonEdit.text = getString(R.string.select)
        buttonEdit.setOnClickListener {
            Game.instance.gamePackage = GamePackage(gamePack.id, "")
            Game.instance.questions = dbConnect!!.getPackageQuestions(gamePack.id)

            val intent = Intent(this, GameSliderActivity::class.java)
            startActivity(intent)
        }
        tableRow.addView(buttonEdit)
        tableLayout.addView(tableRow)
    }
}
