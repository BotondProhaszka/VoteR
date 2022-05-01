package hu.bme.aut.voter.model

data class Group(val id: String, val name : String) {

    val members: List<User> = listOf()

}