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

        back_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        this.renderPackages()
    }

    private fun renderPackages() {
        val packages = dbConnect!!.getAllPackages()
        val tableLayout = tableLayout.findViewById<TableLayout>(R.id.tableLayout)
        tableLayout.removeAllViews()

        for (pack in packages) {
            this.addPackageRow(pack)
        }
    }

    private fun addPackageRow(gamePack: GamePackage) {
        val tableLayout = tableLayout.findViewById<TableLayout>(R.id.tableLayout)
        val tableRow = TableRow(this)
        val id = TextView(this)
        val name = TextView(this)
        val buttonDelete = Button(this)
        val buttonEdit = Button(this)
        id.text = gamePack.id.toString()
        name.text = gamePack.name
        tableRow.addView(id)
        tableRow.addView(name)
        buttonDelete.text = getString(R.string.delete)
        buttonDelete.setOnClickListener {
            dbConnect!!.deletePackage(gamePack.id)
            this.renderPackages()
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
        builder.setTitle(R.string.addPackage)
        val dialogLayout = inflater.inflate(R.layout.add_package_dialog, null)
        val packageName = dialogLayout.findViewById<EditText>(R.id.packageName)
        builder.setView(dialogLayout)
        builder.setPositiveButton(R.string.Add, null)
        builder.setNegativeButton(R.string.Close) { dialogInterface, _ -> dialogInterface.dismiss() }

        val dialog = builder.create()

        dialog.setOnShowListener {
            val addButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            addButton.setOnClickListener {
                dbConnect!!.addPackage(packageName.text.toString())
                Toast.makeText(applicationContext, "Добавлен пакет - " + packageName.text.toString(), Toast.LENGTH_SHORT).show()
                packageName?.text?.clear()
                this.renderPackages()
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
