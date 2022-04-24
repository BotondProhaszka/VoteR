package hu.bme.aut.voter.interfaces

interface UserInterface {
    fun hasRightCreatePoll(): Boolean
    fun getDisplayName(): String
    fun getEmail(): String
    fun getPhotoUri(): String?
}