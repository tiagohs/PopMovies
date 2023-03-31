package br.com.tiagohs.data.auth.useCases

import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.auth.models.User
import br.com.tiagohs.data.auth.repository.AuthRepository

interface SignInFromEmailUseCase {
    suspend operator fun invoke(email: String, password: String): ResultState<User>
}

internal class SignInFromEmailUseCaseImpl(
    private val authRepository: AuthRepository
): SignInFromEmailUseCase {
    override suspend fun invoke(email: String, password: String): ResultState<User> {
        return try {
            ResultState.Success(
                data = authRepository.signIn(email, password)
            )
        } catch (ex: Exception) {
            ResultState.Error(
                error = ex
            )
        }
    }
}