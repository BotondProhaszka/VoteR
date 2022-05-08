package hu.bme.aut.voter.model

import android.net.Uri
import hu.bme.aut.voter.interfaces.UserInterface
import java.io.Serializable

class GoogleUser(
    private val displayName: String = "",
    private val email: String = "",
    private val picUri: String = ""
) : UserInterface, Serializable {
    override fun hasRightCreatePoll(): Boolean = true
    override fun getDisplayName(): String = displayName
    override fun getEmail(): String = email
    override fun getPhotoUri(): String = picUri

}