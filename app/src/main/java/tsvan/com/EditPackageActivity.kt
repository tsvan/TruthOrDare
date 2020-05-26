package tsvan.com

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditPackageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_package)

        val extra = intent.extras
        val id = extra!!.getInt("id")

        Toast.makeText(applicationContext, "id- $id", Toast.LENGTH_SHORT).show()

    }
}
