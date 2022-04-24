package hu.bme.aut.voter.model

import java.io.Serializable

class EmailUser(displayName: String = "", private val email: String = "") : User(displayName), Serializable {
    override fun hasRightCreatePoll() = true
    override fun getEmail(): String = email
    override fun getPhotoUri(): String? = null
}