package com.example.data.network.interceptor

import com.example.data.storage.UserStorageService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userStorage: UserStorageService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val user = runBlocking { userStorage.userFlow.first() }

        val request = chain.request().newBuilder()
        user?.token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(request.build())
    }
}