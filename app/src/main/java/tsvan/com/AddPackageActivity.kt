package tsvan.com

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add_package.*
import tsvan.com.infrastructure.DbConnect
import tsvan.com.models.GamePackage

class AddPackageActivity : AppCompatActivity() {

    private var dbConnect: DbConnect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_package)

        dbConnect = DbConnect(this)

        createPackage.setOnClickListener {
            this.addPackage()
        }

        this.renderPackages()
    }

    private fun renderPackages() {
        val packages = dbConnect!!.getAllPackages()

        for (pack in packages) {
            this.addPackageRow(pack)
        }
    }

    private fun addPackageRow(gamePack: GamePackage) {
        val tableLayout = tableLayout.findViewById<TableLayout>(R.id.tableLayout)
        val tableRow = TableRow(this)
        val rowIdText = TextView(this)
        val rowNameText = TextView(this)
        val buttonDelete = Button(this)
        val buttonEdit = Button(this)
        rowIdText.text = gamePack.id.toString()
        rowNameText.text = gamePack.name
        tableRow.addView(rowIdText)
        tableRow.addView(rowNameText)
        buttonDelete.text = getString(R.string.delete)
        buttonDelete.setOnClickListener {

            dbConnect!!.deletePackage(gamePack.id)
        }
        buttonEdit.text = getString(R.string.edit)
        buttonEdit.setOnClickListener {
            val intent = Intent(this, AddQuestionsActivity::class.java)
            intent.putExtra("id", gamePack.id)
            startActivity(intent)
        }
        tableRow.addView(buttonDelete)
        tableRow.addView(buttonEdit)
        tableLayout.addView(tableRow)
    }

    @SuppressLint("InflateParams")
    private fun addPackage() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Add package")
        val dialogLayout = inflater.inflate(R.layout.add_package_dialog, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.packageName)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Add", null)
        builder.setNegativeButton("Close") { dialogInterface, _ -> dialogInterface.dismiss() }
        builder.setNeutralButton("Test db", null)

        val dialog = builder.create()

        dialog.setOnShowListener {
            val addButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            addButton.setOnClickListener {
                //dialog.dismiss()
                dbConnect!!.addPackage(editText.text.toString())
                Toast.makeText(
                    applicationContext,
                    "Add to db- " + editText.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                editText?.text?.clear()
            }

            val nextButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            nextButton.setOnClickListener {
                val list = dbConnect!!.getAllPackages()

                Toast.makeText(applicationContext, list.toString(), Toast.LENGTH_LONG).show()

            }
        }

        dialog.show()
    }
}
