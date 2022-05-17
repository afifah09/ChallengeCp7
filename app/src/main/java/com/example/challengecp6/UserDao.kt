package com.example.challengecp5

import androidx.room.*

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE email =:username AND password = :password")
    fun login(username: String, password: String):User?

    @Query("SELECT * FROM User WHERE email =:email")
    fun getuser(email: String):User?

    @Update
    fun updateuser(user: User):Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User):Long

}