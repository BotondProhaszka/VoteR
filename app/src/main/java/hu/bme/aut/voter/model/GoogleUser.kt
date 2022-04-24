package hu.bme.aut.voter.model

import android.net.Uri
import java.io.Serializable

class GoogleUser(displayName: String = "", private val email: String = "", private val picUri: String = "") : User(displayName), Serializable {
    override fun hasRightCreatePoll(): Boolean = true
    override fun getEmail(): String = email
    override fun getPhotoUri(): String? = picUri

}