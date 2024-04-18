package com.thoughtworks.androidtrain.data.repositories

import com.thoughtworks.androidtrain.androidassignment.data.dao.UserDao
import com.thoughtworks.androidtrain.data.api.Api
import com.thoughtworks.androidtrain.data.api.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(private val userDao: UserDao){
    fun fetchUsers(): Flow<List<User>> = flow {
        emit(userDao.getAll())
    }

    suspend fun saveFromRemote(): User? {
        val user = Api().fetchUser() ?: return null
        user.generateAndBindId()
        userDao.insertAll(user)
        return user
    }
}