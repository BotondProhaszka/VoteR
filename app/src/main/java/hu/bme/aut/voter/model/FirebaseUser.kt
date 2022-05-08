package hu.bme.aut.voter.model

import hu.bme.aut.voter.interfaces.UserInterface
import java.io.Serializable

class FirebaseUser (private val displayName: String = "", private val email : String = "") :
    Serializable, UserInterface{
    override fun hasRightCreatePoll(): Boolean = false
    override fun getDisplayName(): String = displayName
    override fun getEmail(): String = email
    override fun getPhotoUri(): String = ""
}