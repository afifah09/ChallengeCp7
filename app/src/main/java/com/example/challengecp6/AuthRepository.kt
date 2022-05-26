package com.example.challengecp5

import androidx.lifecycle.asLiveData
import com.example.challengecp6.UserDataStoreManager
import kotlinx.coroutines.coroutineScope


class AuthRepository (private val myDatabase: MyDatabase, private val userPref: UserDataStoreManager) {
    //private val myDatabase by inject<MyDatabase>()
    suspend fun login(username: String, password: String): User? = coroutineScope {
        return@coroutineScope myDatabase.userDao().login(username, password)
    }
    suspend fun insertUser(user: User):Long = coroutineScope {
        return@coroutineScope myDatabase.userDao().insertUser(user)
    }
    suspend fun update(user: User):Int = coroutineScope {
        return@coroutineScope myDatabase.userDao().updateuser(user)
    }
    suspend fun getuser(email:String):User? = coroutineScope {
        return@coroutineScope myDatabase.userDao().getuser(email)
    }
    suspend fun setEmailPreference(email: String){
        coroutineScope {
            userPref.setEmail(email)
        }
    }
    suspend fun setNamaPreference(nama:String){
        coroutineScope {
            userPref.setNama(nama)
        }
    }
    fun emailPreferences() = userPref.getEmail.asLiveData()
    suspend fun delatePreference(){
        coroutineScope {
            userPref.deletePref()
        }
    }
}