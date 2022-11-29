package id.ac.istts.projectmdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

// ACTIVITY INI BUAT COBA HERE API
class MainActivity : AppCompatActivity() {
    private lateinit var txtInput: EditText
    private lateinit var txtOutput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtInput = findViewById(R.id.main_txtInput)
        txtOutput = findViewById(R.id.main_txtOutput)

        txtInput.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                val requestQueue = Volley.newRequestQueue(this)
                val url = txtInput.text.toString()
                val request = StringRequest(
                    Request.Method.GET,
                    url,
                    { response ->
                        txtOutput.text = response
                    },
                    { error ->
                        txtOutput.text = error.toString()
                    }
                )
                requestQueue.add(request)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
}
