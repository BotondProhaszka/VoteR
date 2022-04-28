package hu.bme.aut.voter.services

import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.voter.R
import hu.bme.aut.voter.activities.LoginActivity
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.model.EmailUser

class EmailLoginService(private val activity: LoginActivity) {
    private var auth: FirebaseAuth = Firebase.auth


    fun getLastSignedInAccount(): EmailUser? {
        val user = auth.currentUser
        user?.let {
            val username = activity.resources.getStringArray(R.array.usernames).random()
            return EmailUser(username, it.email.toString())
        }
        return null
    }


    fun registerEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task -> listener(task) }
    }

    fun emailLogin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task -> listener(task) }
    }

    fun forgotPassword(email: String) {
        if (email.isEmpty())
            Toast.makeText(activity, "Please, type your email address!", Toast.LENGTH_SHORT).show()
        else {
            Firebase.auth.sendPasswordResetEmail(email)
            Toast.makeText(activity, "Email send!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listener(task: Task<AuthResult>){
        if (task.isSuccessful) {
            Log.d(MainActivity.TAG_BUGFIX, "signInWithEmail:success")
            val user = auth.currentUser
            val username = activity.resources.getStringArray(R.array.usernames).random()
            user?.let {
                activity.emailLoginSuccess(EmailUser(username, it.email.toString()))
            }

        } else {
            Log.w(MainActivity.TAG_BUGFIX, "signInWithEmail:failure", task.exception)
            Toast.makeText(
                activity, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

