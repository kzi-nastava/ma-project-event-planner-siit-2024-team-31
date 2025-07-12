
/**
 * ПОТОК ДАННЫХ ОТ UI ДО СЕРВЕРА И ОБРАТНО
 * ===============================================
 */

// 1. PRESENTATION LAYER - UI
class LoginFragment {
    fun onLoginButtonClick() {
        // 👆 Пользователь нажал кнопку
        viewModel.login(email, password)
    }

    fun observeChanges() {
        // 👀 Слушаем изменения и обновляем UI
        viewModel.state.observe { state ->
            when(state) {
                Loading -> showProgress()
                Success -> navigateToMain()
                Error -> showError()
            }
        }
    }
}

// 2. PRESENTATION LAYER - ViewModel
class LoginViewModel(private val loginUseCase: LoginUseCase) {
    fun login(email: String, password: String) {
        // 🔄 Связующее звено между UI и бизнес-логикой
        viewModelScope.launch {
            _state.value = Loading
            try {
                val result = loginUseCase(email, password) // ⬇️ В Domain
                _state.value = Success(result)
            } catch (e: Exception) {
                _state.value = Error(e.message)
            }
        }
    }
}

// 3. DOMAIN LAYER - Use Case (Бизнес-логика)
class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): User {
        // 🧠 Бизнес-правила (валидация, обработка)
        validateInput(email, password)

        // ⬇️ Вызываем Repository (но не знаем КАК он работает)
        val response = authRepository.login(LoginRequest(email, password))

        // 🔄 Маппинг из DTO в Domain модель
        return response.toDomainUser()
    }
}

// 4. DOMAIN LAYER - Repository Interface
interface AuthRepository {
    // 📋 Контракт - ЧТО должно быть сделано (но не КАК)
    suspend fun login(request: LoginRequest): LoginResponse
}

// 5. DATA LAYER - Repository Implementation
class AuthRepositoryImpl(
    private val apiService: AuthApi,
    private val storage: SecureStorage
) : AuthRepository {

    override suspend fun login(request: LoginRequest): LoginResponse {
        // 🌐 КАК именно получаем данные (HTTP, база, кэш)
        val apiResponse = apiService.login(request.toDTO())

        // 💾 Сохраняем токен
        storage.saveToken(apiResponse.token)

        return apiResponse.toDomainResponse()
    }
}

// 6. DATA LAYER - Network API
interface AuthApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequestDTO): LoginResponseDTO
}

/**
 * DEPENDENCY INJECTION - КАК ВСЕ СВЯЗЫВАЕТСЯ
 * ==========================================
 */

@Module
class DataModule {
    @Provides
    fun provideAuthRepository(
        apiService: AuthApi,
        storage: SecureStorage
    ): AuthRepository = AuthRepositoryImpl(apiService, storage)
}

@Module
class DomainModule {
    @Provides
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase = LoginUseCase(authRepository)
}

/**
 * МОДЕЛИ НА КАЖДОМ СЛОЕ
 * =====================
 */

// DOMAIN - Чистые модели бизнес-логики
data class User(val id: String, val name: String, val email: String)

// DATA - DTO для API
data class LoginRequestDTO(val email: String, val password: String)
data class LoginResponseDTO(val user: UserDTO, val token: String)

// PRESENTATION - UI состояния
sealed class LoginState {
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}
