package hu.bme.aut.voter.services

import hu.bme.aut.voter.model.User

class UserDatabase {
    companion object {
        private val users: MutableList<User> = mutableListOf()

        fun initDatabase() {
            users.clear()
        }

        fun getUsers() : List<User> {
            return users.toList()
        }

        fun addUser(user: User) {
            users.add(user)
        }

        fun removeUser(user: User) {
            users.remove(user)
        }
    }
}