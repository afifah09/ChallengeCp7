package com.example.challengecp5

import android.content.Context
import androidx.lifecycle.asLiveData
import com.example.challengecp6.UserDataStoreManager
import kotlinx.coroutines.coroutineScope

class AuthRepository (context: Context) {
    val myDatabase = MyDatabase.getInstance(context)
    private val userPref=UserDataStoreManager(context)
    suspend fun login(username: String, password: String): User? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.login(username, password)
    }
    suspend fun insertUser(user: User):Long? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.insertUser(user)
    }
    suspend fun update(user: User):Int? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.updateuser(user)
    }
    suspend fun getuser(email:String):User? = coroutineScope {
        return@coroutineScope myDatabase?.userDao()?.getuser(email)
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
    val emailPreferences = userPref.getEmail.asLiveData()
    suspend fun delatePreference(){
        coroutineScope {
            userPref.deletePref()
        }
    }
}