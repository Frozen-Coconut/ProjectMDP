package id.ac.istts.projectmdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.ac.istts.projectmdp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var type = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.rgType.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.rbUser -> {
                    type = 0
                    binding.registerTxtDate.visibility = View.VISIBLE
                    binding.registerTxtPhone.visibility = View.GONE
                }
                R.id.rbHealthcare -> {
                    type = 1
                    binding.registerTxtDate.visibility = View.GONE
                    binding.registerTxtPhone.visibility = View.VISIBLE
                }
            }
        }

        binding.registerBtnRegister.setOnClickListener {
            if (binding.registerTxtEmail.text.isEmpty() || binding.registerTxtPassword.text.isEmpty() || binding.registerTxtPasswordConfirmation.text.isEmpty() || binding.registerTxtName.text.isEmpty() || binding.registerTxtAddress.text.isEmpty() || (type == 0 && binding.registerTxtDate.text.isEmpty()) || (type == 1 && binding.registerTxtPhone.text.isEmpty())) {
                Toast.makeText(this, "Semua input harus diisi!", Toast.LENGTH_SHORT).show()
            } else if (binding.registerTxtPassword != binding.registerTxtPasswordConfirmation) {
                Toast.makeText(this, "Password dan konfirmasi password tidak sama!", Toast.LENGTH_SHORT).show()
            } else {

            }
        }

        binding.registerBtnBack.setOnClickListener {
            finish()
        }
    }
}