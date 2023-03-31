package br.com.tiagohs.data.auth.useCases

import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.auth.models.User
import br.com.tiagohs.data.auth.repository.AuthRepository

interface CreateUserFromEmailUseCase {
    suspend operator fun invoke(name: String, email: String, password: String): ResultState<User>
}

internal class CreateUserFromEmailUseCaseImpl(
    private val authRepository: AuthRepository
): CreateUserFromEmailUseCase {
    override suspend fun invoke(name: String, email: String, password: String): ResultState<User> {
        return try {
            ResultState.Success(
                data = authRepository.signUp(name, email, password)
            )
        } catch (ex: Exception) {
            ResultState.Error(
                error = ex
            )
        }
    }
}