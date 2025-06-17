package com.example.masterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.masterapp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> get() = _authState

    data class AuthState(
        val isSignUpSuccessful: Boolean = false,
        val isLoginSuccessful: Boolean = false,
        val isAdminLoggedIn: Boolean = false,
        val errorMessage: String? = null
    )

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            val result = repository.signUp(email, password)
            if (result.isSuccess) {
                _authState.value = AuthState(
                    isSignUpSuccessful = true,
                    isLoginSuccessful = false
                )
            } else {
                _authState.value = AuthState(
                    isSignUpSuccessful = false,
                    isLoginSuccessful = false,
                    errorMessage = result.exceptionOrNull()?.message ?: "Signup failed"
                )
            }
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            // Block admin credentials from using user login
            if (email != "pushp.hans1502@gmail.com" && password != "Pushphans") {
                val signInResult = repository.signIn(email, password)
                if (signInResult.isSuccess) {
                    _authState.value = AuthState(
                        isLoginSuccessful = true,
                        isSignUpSuccessful = false
                    )
                } else {
                    _authState.value = AuthState(
                        isLoginSuccessful = false,
                        isSignUpSuccessful = false,
                        errorMessage = signInResult.exceptionOrNull()?.message ?: "Login failed"
                    )
                }
            } else {
                _authState.value = AuthState(
                    errorMessage = "Not allowed to enter invalid details"
                )
            }
        }
    }

    fun adminSignIn(email: String, password: String) {
        viewModelScope.launch {
            if (email == "pushp.hans1502@gmail.com" && password == "Pushphans") {
                val adminSignInResult = repository.adminSignIn(email, password)
                if (adminSignInResult.isSuccess) {
                    _authState.value = AuthState(
                        isAdminLoggedIn = true
                    )
                } else {
                    _authState.value = AuthState(
                        isAdminLoggedIn = false,
                        errorMessage = adminSignInResult.exceptionOrNull()?.message ?: "Admin login failed"
                    )
                }
            } else {
                _authState.value = AuthState(
                    errorMessage = "Admin details are not valid"
                )
            }
        }
    }

    fun signOut() {
        repository.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    fun getUserEmail(): String? {
        return repository.getUserEmail()
    }

    fun resetAuthState() {
        _authState.value = AuthState()
    }
}
