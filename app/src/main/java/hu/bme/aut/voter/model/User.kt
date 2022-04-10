package hu.bme.aut.voter.model
abstract class User (var displayName: String, var email: String = "Anonymous"){

    abstract fun hasRightCreatePoll(): Boolean
}