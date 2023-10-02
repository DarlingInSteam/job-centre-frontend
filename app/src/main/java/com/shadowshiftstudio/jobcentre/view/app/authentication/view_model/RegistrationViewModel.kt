package com.shadowshiftstudio.jobcentre.view.app.authentication.view_model

import android.content.Context
import android.text.TextUtils
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shadowshiftstudio.jobcentre.model.enum.LoginStates

class RegistrationViewModel(private val context: Context): ViewModel() {
    var login: MutableState<String> = mutableStateOf("")
    var email: MutableState<String> = mutableStateOf("")
    var password: MutableState<String> = mutableStateOf("")
    var repeatPassword: MutableState<String> = mutableStateOf("")
    val registerStatusLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun isLoginValid(login: String): LoginStates {
        val pattern = Regex("^[a-zA-Z0-9!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]*$")
        var res: LoginStates = LoginStates.VALID
        if (login.isEmpty())
            res = LoginStates.EMPTY
        else
            if (login.length < 3 || login.length > 20)
                res = LoginStates.INVALID_LENGTH
            else
                if (pattern.matches(login) == false)
                    res = LoginStates.INVALID_CHARACTERS
        return res
    }

    fun isEmailValid(email: String): Boolean {
        return !(TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isPasswordsMatch(): Boolean
    {
        return repeatPassword.value.equals(password.value)
    }
    fun isPasswordValid(password: String): Boolean
    {
        var res = true
        val pattern = Regex("^[a-zA-Z0-9!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]*$")

        if (password.length < 8)
            res = false
        else if (!pattern.matches(password))
            res = false
        else if (password.filter { it.isDigit() }.firstOrNull() == null)
            res = false
        else if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null)
            res = false
        else if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null)
            res = false
        else if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null)
            res = false

        return res
    }

    fun isAllDataEntered():Boolean
    {
        var res = false
        if(isLoginValid(login.value) == LoginStates.VALID
            && isPasswordValid(password.value)
            && isEmailValid(email.value)
            && isPasswordsMatch())
            res = true
        return res
    }
}