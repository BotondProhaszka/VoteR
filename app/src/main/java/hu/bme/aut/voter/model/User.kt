package hu.bme.aut.voter.model
abstract class User (var displayName: String){

    abstract fun hasRightCreatePoll(): Boolean
}