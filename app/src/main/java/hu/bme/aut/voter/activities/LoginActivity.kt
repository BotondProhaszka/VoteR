package hu.bme.aut.voter.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.voter.databinding.ActivityLoginBinding
import hu.bme.aut.voter.dialog.CreateEmailAccDialog
import hu.bme.aut.voter.dialog.LoginAsGuestDialog


class LoginActivity : AppCompatActivity() {

    private var RC_SIGN_IN: Int = 0
    private val TAG = "BUGFIX"

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.btnGoogleLogin.setOnClickListener { googleSignIn() }
        binding.btnLogInGuest.setOnClickListener { loginAsGuest() }
        binding.tvCreateAcc.setOnClickListener{ registrateEmail() }

    }

    override fun onStart() {
        super.onStart()
        val googleAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleAccount != null) {
            googleLoginSuccess(googleAccount)
        }

        val emailAccount = auth.currentUser
        if(emailAccount != null){
            emailLoginSuccess(emailAccount);
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            googleHandleSignInResult(task)
        }
    }

    private fun googleHandleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            googleLoginSuccess(account)
            Log.d(TAG, "SIGNED IN ${account.displayName}")
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun googleLoginSuccess(currentUser: GoogleSignInAccount?) {
        if (currentUser == null)
            return
        Toast.makeText(this, "Logged in as ${currentUser.displayName}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.TAG_IS_ANONYMOUS_USER, false)
        intent.putExtra(MainActivity.TAG_DISPLAY_NAME, currentUser.displayName.toString())
        intent.putExtra(MainActivity.TAG_EMAIL, currentUser.email.toString())
        intent.putExtra(MainActivity.TAG_PROFILE_PIC_URL, currentUser.photoUrl.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun emailLoginSuccess(currentUser: FirebaseUser?) {
        if (currentUser == null)
            return
        Toast.makeText(this, "Logged in as ${currentUser.displayName}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.TAG_IS_ANONYMOUS_USER, false)
        intent.putExtra(MainActivity.TAG_DISPLAY_NAME, currentUser.displayName.toString())
        intent.putExtra(MainActivity.TAG_EMAIL, currentUser.email.toString())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun loginAsGuest() {
        startActivity(Intent(this, LoginAsGuestDialog::class.java))
    }

    private fun registrateEmail(){
        startActivity(Intent(this, CreateEmailAccDialog::class.java))
    }

    private fun googleSignIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    fun emailReg(displayName: String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    emailLoginSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}