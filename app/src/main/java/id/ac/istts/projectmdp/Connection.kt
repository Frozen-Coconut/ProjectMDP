package id.ac.istts.projectmdp

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Connection {
    companion object {
        val database = Firebase.database("https://projectmdp-60c85-default-rtdb.firebaseio.com/")
        val users = database.getReference("/")
    }
}