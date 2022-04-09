package hu.bme.aut.voter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import hu.bme.aut.voter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val TAG_IS_ANONYMOUS_USER = "TAG_IS_ANONYMOUS_USER"
        const val TAG_DISPLAY_NAME = "TAG_DISPLAY_NAME"
        const val TAG_EMAIL = "TAG_EMAIL"
        const val TAG_PROFILE_PIC_URL = "TAG_PROFILE_PIC_URL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(
            this, "Logged in as ${intent.getStringExtra(TAG_DISPLAY_NAME)}, ano: ${
                intent.getBooleanExtra(
                    TAG_IS_ANONYMOUS_USER, true
                )
            }", Toast.LENGTH_SHORT
        ).show()

        binding.btnSignOut.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            var mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
        }
    }
}