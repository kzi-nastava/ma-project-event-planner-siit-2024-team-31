package com.example.data.network.interceptor

import com.example.data.network.util.NoAuth
import com.example.data.storage.UserStorageService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userStorage: UserStorageService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val invocation = request.tag(Invocation::class.java)
        val noAuthAnnotation = invocation?.method()?.getAnnotation(NoAuth::class.java)

        if (noAuthAnnotation != null) {
            // Skip authentication for requests annotated with @NoAuth
            return chain.proceed(request)
        }

        val user = runBlocking { userStorage.userFlow.first() }

        val newRequest = request.newBuilder()
            .apply {
                if (!user?.token.isNullOrBlank()) {
                    addHeader("Authorization", "Bearer ${user.token}")
                }
            }
            .build()

        return chain.proceed(newRequest)
    }
}