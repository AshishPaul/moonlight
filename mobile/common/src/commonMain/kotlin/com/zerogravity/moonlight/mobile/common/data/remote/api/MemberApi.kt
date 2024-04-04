//package com.zerogravity.moonlight.mobile.common.data.remote.api
//
//import com.zerogravity.moonlight.mobile.common.data.model.Member
//import com.zerogravity.moonlight.mobile.common.data.model.NewMember
//import com.zerogravity.moonlight.mobile.common.data.remote.ApiRoutes
//import io.ktor.client.HttpClient
//import io.ktor.client.call.body
//import io.ktor.client.request.get
//import io.ktor.client.request.post
//import io.ktor.client.request.setBody
//
//class MemberApi(private val httpClient: HttpClient) {
//
//    suspend fun getMembersByUser(userId: String): List<Member> {
//        return httpClient.get(ApiRoutes.Endpoints.MEMBERS_BY_USERS.replace("{userId}", userId))
//            .body()
//    }
//
//    suspend fun addNewMember(newMember: NewMember): Member {
//        val response = httpClient.post(
//            ApiRoutes.Endpoints.MEMBERS_BY_USERS.replace(
//                "{userId}",
//                newMember.userId
//            )
//        ) {
//            setBody(newMember)
//        }
//        return response.body()
//    }
//}