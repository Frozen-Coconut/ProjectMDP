package id.ac.istts.projectmdp

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Connection {
    companion object {
        val database = Firebase.database("https://projectmdp-20523-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val users = Connection.database.getReference("users")
    }
}