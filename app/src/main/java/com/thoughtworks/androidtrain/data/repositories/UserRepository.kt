package com.thoughtworks.androidtrain.data.repositories

import com.thoughtworks.androidtrain.data.dao.UserDao
import com.thoughtworks.androidtrain.data.api.Api
import com.thoughtworks.androidtrain.data.model.entity.User

class UserRepository(private val userDao: UserDao){

    suspend fun saveFromRemote(): User? {
        val user = Api().fetchUser() ?: return null
        user.generateAndBindId()
        userDao.insertAll(user)
        return user
    }
}