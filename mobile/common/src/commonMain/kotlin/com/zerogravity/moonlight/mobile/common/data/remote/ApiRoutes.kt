package com.zerogravity.moonlight.mobile.common.data.remote

object ApiRoutes {
    const val BASE_URL = "https://chaperone.onrender.com/"

    sealed class Endpoint(val path: String) {
        data object SignUp : Endpoint("/signup")
        data object AuthWithCredential : Endpoint("/authenticate/credential")
        data object AuthWithToken : Endpoint("/authenticate/token")
        data object RefreshToken : Endpoint("refreshToken")
        data object Category : Endpoint("/category")
        data object ServiceByCategoryId : Endpoint("/category/{categoryId?}/service")
        data object Service : Endpoint("/service")
        data object MyProfile : Endpoint("/myProfile")
        data object UpdateProfile : Endpoint("/updateProfile")
        data object User : Endpoint("user")
    }
}