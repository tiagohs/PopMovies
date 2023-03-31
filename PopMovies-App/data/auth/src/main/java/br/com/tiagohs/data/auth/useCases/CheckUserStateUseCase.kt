package br.com.tiagohs.data.auth.useCases

import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.auth.repository.AuthRepository

interface CheckUserStateUseCase {
    suspend operator fun invoke(): ResultState<Boolean>
}

internal class CheckUserStateUseCaseImpl(
    private val authRepository: AuthRepository
): CheckUserStateUseCase {
    override suspend fun invoke(): ResultState<Boolean> {
        return try {
            ResultState.Success(
                data = authRepository.isUserAuthenticate()
            )
        } catch (ex: Exception) {
            ResultState.Error(
                error = ex
            )
        }
    }
}