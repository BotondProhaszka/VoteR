package hu.bme.aut.voter.dialog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.databinding.DialogLoginAsGuestBinding
import hu.bme.aut.voter.model.GuestUser

class LoginAsGuestDialog : DialogFragment() {

    private lateinit var binding: DialogLoginAsGuestBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogLoginAsGuestBinding.inflate(layoutInflater)
        binding.btnOk.setOnClickListener {
            if (binding.etUsername.text.isNotEmpty())
                login()
        }

        binding.btnCancel.setOnClickListener{
            this.dismiss()
        }
        return binding.root
    }


    private fun login(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra(MainActivity.TAG_USER, GuestUser(binding.etUsername.text.toString()))
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.dismiss()
    }

}