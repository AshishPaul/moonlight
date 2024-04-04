package com.zerogravity.moonlight.mobile.android.presentation.authentication

import android.app.Activity
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.zerogravity.moonlight.mobile.common.domain.AppLogger
import com.zerogravity.moonlight.mobile.common.domain.model.AuthData
import com.zerogravity.moonlight.mobile.common.domain.model.AuthError
import com.zerogravity.moonlight.mobile.common.domain.model.AuthResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

// TODO() Move this to a more secure location
private const val SERVER_KEY =
    "459857664269-i83jet625liripnfd5hj3flf93o93oje.apps.googleusercontent.com"

private const val GoogleAndroidAuthProviderTag = "GoogleAndroidAuthProvider"

class GoogleAndroidAuthProvider(private val appLogger: AppLogger) {

    suspend fun signUp(activity: Activity): AuthResult {
        appLogger.i("$GoogleAndroidAuthProviderTag : Signup")
        return createCredentialRequestAndGetCredentials(activity, false)
    }

    suspend fun login(activity: Activity): AuthResult {
        appLogger.i("$GoogleAndroidAuthProviderTag : Login")
        return createCredentialRequestAndGetCredentials(activity, true)
    }

    suspend fun signOut(activity: Activity) {
        CredentialManager.create(activity).clearCredentialState(ClearCredentialStateRequest())
    }

    private suspend fun createCredentialRequestAndGetCredentials(
        activity: Activity,
        setFilterByAuthorizedAccounts: Boolean,
    ): AuthResult {
        val request: GetCredentialRequest = getCredentialRequest(setFilterByAuthorizedAccounts)

        val authResult = try {
            val getCredentialResponse = CredentialManager.create(activity).getCredential(
                request = request,
                context = activity,
            )
            getCredentialResponse.handleCredentialResponse()
        } catch (e: GetCredentialException) {
            appLogger.e(e)
            errorToAuthResult(e)
        }

        return if (setFilterByAuthorizedAccounts // i.e. from signIn not from signUp
            && authResult is AuthResult.Error
            && authResult.authError is AuthError.AccountNotFound
        ) {
            signUp(activity)
        } else {
            authResult
        }
    }

    private fun getCredentialRequest(setFilterByAuthorizedAccounts: Boolean): GetCredentialRequest {
        val googleIdOption: GetGoogleIdOption =
            GetGoogleIdOption.Builder().setAutoSelectEnabled(setFilterByAuthorizedAccounts)
                .setFilterByAuthorizedAccounts(setFilterByAuthorizedAccounts)
                .setServerClientId(SERVER_KEY).build()

        return GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()
    }

    private fun GetCredentialResponse.handleCredentialResponse(): AuthResult =
        when (this.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        AuthResult.Successful(AuthData(googleIdTokenCredential.idToken))
                    } catch (e: GoogleIdTokenParsingException) {
                        appLogger.e(e, "Received an invalid google id token response")
                        AuthResult.Error(AuthError.InvalidAuthData(e))
                    }
                } else {
                    appLogger.e(null, "Unexpected type of credential")
                    AuthResult.Error(AuthError.AccountNotFound(Throwable("Unexpected type of credential")))
                }
            }

            else -> {
                appLogger.e(null, "Unexpected type of credential")
                AuthResult.Error(AuthError.AccountNotFound(Throwable("Unexpected type of credential")))
            }
        }

    private fun errorToAuthResult(e: GetCredentialException): AuthResult =
        when (e) {
            is NoCredentialException -> {
                AuthResult.Error(AuthError.AccountNotFound(e))
            }

            else -> {
                AuthResult.Error(AuthError.InvalidAuthData(e))
            }
        }
}
