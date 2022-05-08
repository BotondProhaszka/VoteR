package hu.bme.aut.voter.model

import hu.bme.aut.voter.interfaces.UserInterface
import java.io.Serializable

class EmailUser(private val displayName: String = "", private val email: String = "") :
    UserInterface, Serializable {
    override fun hasRightCreatePoll() = true
    override fun getDisplayName(): String = displayName
    override fun getEmail(): String = email
    override fun getPhotoUri(): String? = null
}