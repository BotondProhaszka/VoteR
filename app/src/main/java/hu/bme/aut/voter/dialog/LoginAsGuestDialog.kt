package hu.bme.aut.voter.dialog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.databinding.ActivityLoginAsGuestBinding
import hu.bme.aut.voter.model.GuestUser

class LoginAsGuestDialog : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAsGuestBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAsGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnOk.setOnClickListener {
            if (binding.etUsername.text.isNotEmpty())
              login()
        }

        binding.btnCancel.setOnClickListener{
            finish()
        }
    }

    private fun login(){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.TAG_USER, GuestUser(binding.etUsername.text.toString()))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

}