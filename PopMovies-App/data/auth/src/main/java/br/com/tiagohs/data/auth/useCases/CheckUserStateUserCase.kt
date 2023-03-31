package br.com.tiagohs.data.auth.useCases

import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.auth.repository.AuthRepository


interface CheckUserStateUserCase {
    suspend operator fun invoke(): ResultState<Boolean>
}

internal class CheckUserStateUserCaseImpl(
    private val authRepository: AuthRepository
): CheckUserStateUserCase {
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