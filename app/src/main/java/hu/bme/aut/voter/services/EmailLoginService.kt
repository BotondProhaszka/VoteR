package hu.bme.aut.voter.services

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.voter.activities.LoginActivity
import hu.bme.aut.voter.activities.MainActivity

class EmailLoginService(private val activity: LoginActivity) {
    private var auth: FirebaseAuth = Firebase.auth


    fun getLastSignedInAccount(): EmailAccount = auth.currentUser


    fun registerEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(
            email,
            password
        )
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(MainActivity.TAG_BUGFIX, "createUserWithEmail:success")
                    val user = auth.currentUser
                    activity.emailLoginSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(MainActivity.TAG_BUGFIX, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}

