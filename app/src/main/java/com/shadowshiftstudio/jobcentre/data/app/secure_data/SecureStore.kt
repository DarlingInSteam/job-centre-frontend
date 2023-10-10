package com.shadowshiftstudio.jobcentre.data.app.secure_data

import android.content.Context
import android.content.SharedPreferences
import com.shadowshiftstudio.jobcentre.domain.model.enum.Role

object SecureStore {
    private lateinit var preferences: SharedPreferences

    fun initialize(context: Context) {
        preferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    }

    fun putUserRole(role: Role) {
        preferences.edit().putString("role", role.toString()).commit()
    }

    fun getUserRole(): Role {
        return if (preferences.getString("role", null) == "UNEMPLOYED") Role.UNEMPLOYED else Role.EMPLOYED
    }

    fun putAccessToken(token: String) {
        preferences.edit().putString("accessToken", token).commit()
    }

    fun getAccessToken(): String? {
        return preferences.getString("accessToken", null)
    }

    fun putUpdateToken(token: String) {
        preferences.edit().putString("updateToken", token).commit()
    }

    fun getUpdateToken(): String? {
        return preferences.getString("updateToken", null)
    }

    fun putIsLogin() {
        preferences.edit().putString("isLogin", "1").commit()
    }

    fun getIsLogin(): String? {
        return preferences.getString("isLogin", null)
    }

    fun putUsername(username: String) {
        preferences.edit().putString("username", username).commit()
    }

    fun getUsername(): String? {
        return preferences.getString("username", null)
    }
}