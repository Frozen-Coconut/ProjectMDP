package id.ac.istts.projectmdp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import id.ac.istts.projectmdp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var users: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val ref = Connection.users

        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                users = snapshot.value as ArrayList<User>
                Log.e("firebase", "Value is: ${snapshot.value}")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", "Failed to read value!", error.toException())
            }
        })

        users = ArrayList()
        val task = ref.get()
        task.addOnSuccessListener {
            users = it.value as ArrayList<User>
            Log.e("firebase", "Value is: ${it.value}")
        }.addOnFailureListener {
            Log.e("firebase", "Failed to read value!", it)
        }.addOnCompleteListener {
            users = it.result.value as ArrayList<User>
            Log.e("firebase", "Value is: ${it.result.value}")
        }.addOnCanceledListener {
            Log.e("firebase", "Failed to read value!")
        }
//        ref.setValue(arrayListOf(User("admin", "admin", "Admin")))
//        ref.setValue(null)

        binding.registerBtnRegister.setOnClickListener {
            if (binding.registerTxtUsername.text.isEmpty() || binding.registerTxtPassword.text.isEmpty() || binding.registerTxtName.text.isEmpty()) {
                Toast.makeText(this, "Input fields must not be empty!", Toast.LENGTH_SHORT).show()
            } else {
                val user = User(binding.registerTxtUsername.text.toString(), binding.registerTxtPassword.text.toString(), binding.registerTxtName.text.toString())
                users.add(user)
                ref.setValue(users)
                binding.registerTxtUsername.text.clear()
                binding.registerTxtPassword.text.clear()
                binding.registerTxtName.text.clear()
            }
        }

        binding.registerBtnToLogin.setOnClickListener {
            finish()
        }
    }
}