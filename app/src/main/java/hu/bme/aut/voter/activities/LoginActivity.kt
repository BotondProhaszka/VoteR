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
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.ActivityLoginBinding
import hu.bme.aut.voter.dialog.LoginAsGuestDialog
import hu.bme.aut.voter.model.EmailUser
import hu.bme.aut.voter.model.GoogleUser


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
        initLoginServices()
        initButtons()
    }

    override fun onStart() {
        super.onStart()
        val googleAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (googleAccount != null) {
            googleLoginSuccess(googleAccount)
        }

        val emailAccount = auth.currentUser
        if (emailAccount != null) {
            emailLoginSuccess(emailAccount)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            googleHandleSignInResult(task)
        }
    }

    private fun initButtons() {
        binding.btnGoogleLogin.setOnClickListener { googleSignIn() }
        binding.btnLogInGuest.setOnClickListener { loginAsGuest() }
        binding.btnRegister.setOnClickListener { registerEmail() }
        binding.btnLogin.setOnClickListener { emailLogin() }
        binding.tvForgotPassword.setOnClickListener { forgotPassword() }
        binding.etEmail.setOnFocusChangeListener{ view, b ->
            if(!b)
                binding.etEmail.setText(
                    binding.etEmail.text.toString().replace(" ", "")
                )
        }
    }

    private fun initLoginServices() {
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
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
        intent.putExtra(
            MainActivity.TAG_USER,
            GoogleUser(
                currentUser.displayName.toString(),
                currentUser.email.toString(),
                currentUser.photoUrl.toString()
            )
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun emailLoginSuccess(currentUser: FirebaseUser?) {
        if (currentUser == null)
            return
        val username = resources.getStringArray(R.array.usernames).random()
        Toast.makeText(this, "Logged in as $username", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.TAG_USER, EmailUser(username, currentUser.email.toString()))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun loginAsGuest() {
        startActivity(Intent(this, LoginAsGuestDialog::class.java))
    }

    private fun registerEmail() {
        if (checkEmailFields())
            onEmailUserCreated()
    }

    private fun googleSignIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    private fun onEmailUserCreated() {
        auth.createUserWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    emailLoginSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun checkEmailFields(): Boolean {
        val result = binding.etEmail.text != null &&
                binding.etPassword.text != null
        if (result)
            return true
        else
            Toast.makeText(this, "Type your email and password", Toast.LENGTH_SHORT).show()
        return false
    }

    private fun emailLogin() {
        auth.signInWithEmailAndPassword(
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    emailLoginSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun forgotPassword() {
        if (binding.etEmail.text.isNullOrEmpty())
            Toast.makeText(this, "Please, type your email address!", Toast.LENGTH_SHORT).show()
        else {
            Firebase.auth.sendPasswordResetEmail(binding.etEmail.text.toString())
            Toast.makeText(this, "Email send!", Toast.LENGTH_SHORT).show()
        }
    }
}