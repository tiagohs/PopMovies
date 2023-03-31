package br.com.tiagohs.data.auth.useCases

import br.com.tiagohs.core.helpers.state.ResultState
import br.com.tiagohs.data.auth.models.UserManager
import br.com.tiagohs.data.auth.repository.AuthRepository

interface CheckUserStateUseCase {
    suspend operator fun invoke(): ResultState<Boolean>
}

internal class CheckUserStateUseCaseImpl(
    private val authRepository: AuthRepository,
    private val userManager: UserManager = UserManager
): CheckUserStateUseCase {
    override suspend fun invoke(): ResultState<Boolean> {
        return try {
            val user = authRepository.isUserAuthenticate()
            val isUserAuthenticated = user != null

            if (isUserAuthenticated) {
                userManager.userInfo = authRepository.getUserInfo(user!!.email)
            }

            ResultState.Success(
                data = isUserAuthenticated
            )
        } catch (ex: Exception) {
            ResultState.Error(
                error = ex
            )
        }
    }
}