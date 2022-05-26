package com.example.challengecp5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2)
abstract class MyDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao



}