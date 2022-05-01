package hu.bme.aut.voter.services

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import hu.bme.aut.voter.activities.LoginActivity
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.model.GoogleUser
import hu.bme.aut.voter.model.User

class GoogleLoginService(private val activity: LoginActivity) {
    private var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN: Int = 0


    init {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        googleSignInResult.launch(signInIntent)
    }

    fun getLastSignedInAccount(): GoogleUser? {
        val account = GoogleSignIn.getLastSignedInAccount(activity)
        account?.let {
            return GoogleUser(
                it.displayName.toString(),
                it.email.toString(),
                it.photoUrl.toString()
            )
        }
        return null
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.let {
                MainActivity.firestoreDatabase.addUser(
                    GoogleUser(
                        displayName = it.displayName.toString(),
                        email = it.email.toString()
                    )
                )
                activity.googleLoginSuccess(
                    GoogleUser(
                        it.displayName.toString(),
                        it.email.toString(),
                        it.photoUrl.toString()
                    )
                )
            }


            Log.d(MainActivity.TAG_BUGFIX, "SIGNED IN ${account.displayName}")
        } catch (e: ApiException) {
            Log.w(MainActivity.TAG_BUGFIX, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun handleSignInResult(data: Intent?) {
        Toast.makeText(activity, "Result: $RC_SIGN_IN", Toast.LENGTH_SHORT).show()

        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
        handleSignInResult(task)

    }

    private val googleSignInResult = activity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        handleSignInResult(it.data)
    }
}