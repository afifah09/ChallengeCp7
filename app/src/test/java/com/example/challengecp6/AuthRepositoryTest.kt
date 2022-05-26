package com.example.challengecp6

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.challengecp5.AuthRepository
import com.example.challengecp5.MyDatabase
import com.example.challengecp5.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class AuthRepositoryTest {

    private lateinit var myDatabase: MyDatabase
    private lateinit var userDataStoreManager: UserDataStoreManager
    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        myDatabase = mockk()
        userDataStoreManager = mockk()
        authRepository = AuthRepository(myDatabase, userDataStoreManager)
    }

    @Test
    fun login() {
        val returnLogin = mockk<User>()

        every {
            runBlocking {
                myDatabase.userDao().login("email", "password")
            }
        } returns returnLogin
        runBlocking {
            authRepository.login("email", "password")
        }

        verify {
            runBlocking {
                myDatabase.userDao().login("email", "password")
            }
        }
    }
}