package hu.bme.aut.voter.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.ActivityLoginBinding
import hu.bme.aut.voter.dialog.LoginAsGuestDialog
import hu.bme.aut.voter.model.EmailUser
import hu.bme.aut.voter.model.GoogleUser
import hu.bme.aut.voter.model.GuestUser
import hu.bme.aut.voter.services.EmailLoginService
import hu.bme.aut.voter.services.GoogleLoginService


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleLoginService: GoogleLoginService
    private lateinit var emailLoginService: EmailLoginService
    private fun email() = binding.etEmail.text.toString()
    private fun password() = binding.etPassword.text.toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLoginServices()
        initButtons()
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.TAG_USER, GuestUser(resources.getStringArray(R.array.usernames).random()))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
        googleLoginService.getLastSignedInAccount()?.let { googleLoginSuccess(it) }
        emailLoginService.getLastSignedInAccount()?.let { emailLoginSuccess(it) }
    }

    private fun initLoginServices() {
        googleLoginService = GoogleLoginService(this)
        emailLoginService = EmailLoginService(this)
    }

    private fun initButtons() {
        binding.btnGoogleLogin.setOnClickListener { googleLoginService.signIn() }
        binding.btnLogInGuest.setOnClickListener { loginAsGuest() }
        binding.btnRegister.setOnClickListener { emailLoginService.registerEmail(email(), password()) }
        binding.btnLogin.setOnClickListener { emailLoginService.emailLogin(email(), password()) }
        binding.tvForgotPassword.setOnClickListener { emailLoginService.forgotPassword(email()) }
        binding.etEmail.setOnFocusChangeListener { _, b ->
            if (!b)
                binding.etEmail.setText(
                    binding.etEmail.text.toString().replace(" ", "")
                )
        }
    }

    fun googleLoginSuccess(currentUser: GoogleUser) {
        Toast.makeText(this, "Logged in as ${currentUser.getDisplayName()}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.TAG_USER, currentUser)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    fun emailLoginSuccess(currentUser: EmailUser) {
        Toast.makeText(this, "Logged in as ${currentUser.getDisplayName()}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.TAG_USER, currentUser)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    private fun loginAsGuest() {
        startActivity(Intent(this, LoginAsGuestDialog::class.java))
    }

}