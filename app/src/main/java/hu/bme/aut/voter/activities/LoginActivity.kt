package hu.bme.aut.voter.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.ActivityLoginBinding
import hu.bme.aut.voter.dialog.LoginAsGuestDialog
import hu.bme.aut.voter.model.EmailUser
import hu.bme.aut.voter.model.GoogleUser
import hu.bme.aut.voter.services.EmailLoginService
import hu.bme.aut.voter.services.GoogleLoginService


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleLoginService: GoogleLoginService
    private lateinit var emailLoginService: EmailLoginService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoginServices()
        initButtons()
    }

    override fun onStart() {
        super.onStart()
        googleLoginService.getLastSignedInAccount()?.let { googleLoginSuccess(it) }

        emailLoginService.getLastSignedInAccount()?.let { emailLoginSuccess(it) }
    }


    private fun initButtons() {
        binding.btnGoogleLogin.setOnClickListener { googleLoginService.signIn() }
        binding.btnLogInGuest.setOnClickListener { loginAsGuest() }
        binding.btnRegister.setOnClickListener { registerEmail() }
        binding.btnLogin.setOnClickListener { emailLogin() }
        binding.tvForgotPassword.setOnClickListener { forgotPassword() }
        binding.etEmail.setOnFocusChangeListener { _, b ->
            if (!b)
                binding.etEmail.setText(
                    binding.etEmail.text.toString().replace(" ", "")
                )
        }
    }

    private fun initLoginServices() {
        googleLoginService = GoogleLoginService(this)
        emailLoginService = EmailLoginService(this)

    }


    fun googleLoginSuccess(currentUser: GoogleSignInAccount?) {
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

    fun emailLoginSuccess(currentUser: FirebaseUser?) {
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