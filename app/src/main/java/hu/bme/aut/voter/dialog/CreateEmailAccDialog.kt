package hu.bme.aut.voter.dialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.voter.activities.LoginActivity
import hu.bme.aut.voter.databinding.ActivityCreateEmailAccDialogBinding

class CreateEmailAccDialog: AppCompatActivity() {

    private lateinit var binding: ActivityCreateEmailAccDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEmailAccDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnCancel.setOnClickListener { finish() }


    }

    override fun onResume() {
        super.onResume()
        binding.btnOk.setOnClickListener {
            if (checkFields()) {
                (parent as LoginActivity).emailReg(
                    binding.etDisplayName.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }

        }
    }

    private fun checkFields(): Boolean {
        return binding.etDisplayName.text.isNotEmpty() &&
                binding.etEmail.text != null &&
                binding.etPassword.text != null &&
                binding.etPassword.text == binding.etPasswordRepeat.text
    }

}