package hu.bme.aut.voter.model

class GuestUser(displayName: String) : User(displayName) {
    override fun hasRightCreatePoll(): Boolean = false
    override fun getEmail(): String = ""
    override fun getPhotoUri(): String? = null
}