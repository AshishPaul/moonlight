package com.zerogravity.moonlight.mobile.common.domain

import com.auth0.android.jwt.DecodeException
import com.auth0.android.jwt.JWT
import com.zerogravity.moonlight.mobile.common.data.local.model.UserDbModel

class JwtTokenDecoder {

    fun isValidToken(token: String?): Boolean {
        return try {
            val jwt = JWT(token!!)
            isValidJwt(jwt)
        } catch (e: NullPointerException) {
            false
        } catch (e: DecodeException) {
            false
        }
    }

    fun getUserFromToken(token: String?): UserDbModel? {
        var jwt: JWT? = null
        val isValidToken = try {
            jwt = JWT(token!!)
            isValidJwt(jwt)
        } catch (e: NullPointerException) {
            false
        } catch (e: DecodeException) {
            false
        }
        if (isValidToken && jwt != null) {
            val id = jwt.getClaim("id").asString()!!
            val givenName = jwt.getClaim("givenName").asString()!!
            val familyName = jwt.getClaim("familyName").asString()!!
            val profilePictureUrl = jwt.getClaim("profilePictureUrl").asString()!!
            val phoneNumber = jwt.getClaim("phoneNumber").asString()!!
            val email = jwt.getClaim("email").asString()!!

            return UserDbModel(
                id = id,
                givenName = givenName,
                familyName = familyName,
                email = email,
                phoneNumber = phoneNumber,
                profilePictureUrl = profilePictureUrl
            )
        } else {
            return null
        }
    }

    private fun isValidJwt(jwt: JWT): Boolean {
        val issuer = jwt.issuer //get registered claims
        val isExpired = jwt.isExpired(10) // Do time validation with 10 seconds leeway
        return "moonlight-backend" == issuer && !isExpired
    }
}