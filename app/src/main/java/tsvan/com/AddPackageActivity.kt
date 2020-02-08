package tsvan.com

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add_package.*
import tsvan.com.infrastructure.DbConnect

class AddPackageActivity : AppCompatActivity() {

    private var dbConnect : DbConnect? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_package)

        dbConnect = DbConnect(this)

        createPackage.setOnClickListener {
            this.addPackage()
        }
    }

    @SuppressLint("InflateParams")
    private fun addPackage() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle("Add package")
        val dialogLayout = inflater.inflate(R.layout.add_package_dialog, null)
        val editText  = dialogLayout.findViewById<EditText>(R.id.packageName)
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
                Toast.makeText(applicationContext, "Add to db- " + editText.text.toString(), Toast.LENGTH_SHORT).show()
                editText?.text?.clear()
            }

            val nextButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            nextButton.setOnClickListener {
                val list = dbConnect!!.getAllPackages()

                Toast.makeText(applicationContext,list.toString(), Toast.LENGTH_LONG).show()

            }
        }

        dialog.show()
    }
}
