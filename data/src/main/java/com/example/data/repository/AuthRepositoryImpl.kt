package com.example.data.repository

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.example.data.network.api.AuthApi
import com.example.data.network.dto.auth.LoginRequestDTO
import com.example.data.network.util.parseErrorBody
import com.example.data.storage.SystemUser
import com.example.data.storage.UserStorageService
import com.example.domain.entity.auth.RegisterParams
import com.example.domain.entity.user.Role
import com.example.domain.repository.AuthRepository
import com.example.domain.usecase.auth.RegisterUseCase
import com.example.domain.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val userStorage: UserStorageService,
    @ApplicationContext private val context: Context
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<Unit> {
        return try {
            val response = api.login(
                request = LoginRequestDTO(email = email, password = password)
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    val systemUser = SystemUser(
                        token = body.token,
                        role = Role.valueOf(body.role.uppercase())
                    )
                    userStorage.saveUser(systemUser)
                    Resource.Success(Unit)
                } else {
                    Resource.Error("An unexpected error occurred: response body is null.")
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown login error"
                Resource.Error(errorBody)
            }
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server, check your internet connection.")
        } catch (e: IllegalArgumentException) {
            Resource.Error("Received an invalid user role from the server.")
        }
    }

    override suspend fun register(params: RegisterParams): Resource<Unit> {
        return try {

            fun String?.toTextRequestBody(): RequestBody {
                return this?.toRequestBody("text/plain".toMediaTypeOrNull())
                    ?: "".toRequestBody("text/plain".toMediaTypeOrNull())
            }

            val photoParts = params.photos.map { uriString ->
                createPartFromUri(uriString.toUri(), "photos")
            }

            val response = api.register(
                email = params.email.toTextRequestBody(),
                password = params.password.toTextRequestBody(),
                role = params.role.toString().toRequestBody(),
                firstName = params.firstName.toTextRequestBody(),
                lastName = params.lastName.toTextRequestBody(),
                phoneNumber = params.phoneNumber.toTextRequestBody(),
                country = params.country.toTextRequestBody(),
                city = params.city.toTextRequestBody(),
                address = params.address.toTextRequestBody(),
                zipCode = params.zipCode.toTextRequestBody(),
                description = params.description.toTextRequestBody(),
                photos = photoParts
            )

            if (response.isSuccessful) {
                Resource.Success(Unit)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown registration error"
                Resource.Error(errorBody)
            }
        } catch (e: IOException) {
            Resource.Error("Couldn't create files or reach server.")
        }
    }

    private fun createPartFromUri(uri: Uri, partName: String): MultipartBody.Part {
        val file = getFileFromUri(uri)
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }

    private fun getFileFromUri(uri: Uri): File {
        val file = File(context.cacheDir, "temp_file_${System.currentTimeMillis()}")
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        return file
    }
}