package com.example.challengecp6

import androidx.room.Room
import com.example.challengecp5.MyDatabase
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(get()
            , MyDatabase::class.java, "mydatabase.db").build()
    }

    single {
        get<MyDatabase>().userDao()
    }

}