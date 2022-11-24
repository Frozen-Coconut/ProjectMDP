package id.ac.istts.projectmdp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.ac.istts.projectmdp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    lateinit var cokcokcok: Button
    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth
//    private lateinit var users: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        cokcokcok = findViewById(R.id.btn_register)
        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmailRegister.text.toString()
            val password = binding.edtPasswordRegister.text.toString()

            if(email.isEmpty()){
                binding.edtEmailRegister.error = "email harus diisi"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailRegister.error = "Email tidak valid"
                binding.edtEmailRegister.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                binding.edtPasswordRegister.error = "email harus diisi"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.edtPasswordRegister.error = "Password Minimal 6 Karakter"
                binding.edtPasswordRegister.requestFocus()
                return@setOnClickListener
            }
            registerauth(email, password)
        }
//        val ref = Connection.users
//
//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                users = snapshot.value as ArrayList<User>
//                Log.e("firebase", "Value is: ${snapshot.value}")
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("firebase", "Failed to read value!", error.toException())
//            }
//        })
//
//        val task = ref.get()
//        task.addOnSuccessListener {
//            users = it.value as ArrayList<User>
//            Log.e("firebase", "Value is: ${it.value}")
//        }.addOnFailureListener {
//            Log.e("firebase", "Failed to read value!", it)
//        }
//
//        binding.registerBtnRegister.setOnClickListener {
//            if (binding.registerTxtUsername.text.isEmpty() || binding.registerTxtPassword.text.isEmpty() || binding.registerTxtName.text.isEmpty()) {
//                Toast.makeText(this, "Input fields must not be empty!", Toast.LENGTH_SHORT).show()
//            } else {
//                val user = User(binding.registerTxtUsername.text.toString(), binding.registerTxtPassword.text.toString(), binding.registerTxtName.text.toString())
//                users.add(user)
//                ref.setValue(users)
//            }
//        }
//
//        binding.registerBtnToLogin.setOnClickListener {
//            finish()
//        }
    }
    private fun registerauth(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { 
            if(it.isSuccessful){
                Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}