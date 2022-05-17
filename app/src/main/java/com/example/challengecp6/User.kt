package com.example.challengecp5

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val username: String,
    val email: String,
    val password: String,
    val foto: String = ""
)