package hu.bme.aut.voter.model

import hu.bme.aut.voter.interfaces.UserInterface
import java.io.Serializable

class GuestUser(private val displayName: String = "") : UserInterface, Serializable {
    override fun hasRightCreatePoll(): Boolean = false
    override fun getDisplayName(): String = displayName
    override fun getEmail(): String = ""
    override fun getPhotoUri(): String? = null
}