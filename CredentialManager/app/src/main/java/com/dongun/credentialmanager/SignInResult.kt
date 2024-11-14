package com.dongun.credentialmanager

sealed interface SignInResult {
    data class Success(val username: String): SignInResult
    data object Cancelled: SignInResult
    data object Failure: SignInResult
    data object NoCredentials: SignInResult
}