package com.dongun.credentialmanager.login

import com.dongun.credentialmanager.SignInResult
import com.dongun.credentialmanager.SignUpResult

sealed interface LoginAction {
    data class OnSignIn(val result: SignInResult): LoginAction
    data class OnSignUp(val result: SignUpResult): LoginAction
    data class OnUsernameChange(val username: String): LoginAction
    data class OnPasswordChange(val password: String): LoginAction
    data object OnToggleIsRegister: LoginAction
}