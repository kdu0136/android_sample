package com.example.errorhandle.data

import com.example.errorhandle.domain.AuthRepository
import com.example.errorhandle.domain.DataError
import com.example.errorhandle.domain.User
import com.example.errorhandle.domain.Result
import retrofit2.HttpException

class AuthRepositoryImpl: AuthRepository {

    override suspend fun register(password: String): Result<User, DataError.Network> {
        // API call logic
        return try {
            val user = User("dummy", "dummy", "dummy")
            Result.Success(user)
        } catch(e: HttpException) {
            when(e.code()) {
                408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
                413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }
        }
    }
}