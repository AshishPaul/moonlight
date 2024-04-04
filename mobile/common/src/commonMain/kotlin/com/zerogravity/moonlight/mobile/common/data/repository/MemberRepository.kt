//package com.zerogravity.moonlight.mobile.common.data.repository
//
//import com.zerogravity.moonlight.mobile.common.data.model.Member
//import com.zerogravity.moonlight.mobile.common.data.model.NewMember
//import com.zerogravity.moonlight.mobile.common.data.remote.api.MemberApi
//
//class MemberRepository(private val memberApi: MemberApi) {
//
//    suspend fun addMember(newMember: NewMember): Member {
//        return memberApi.addNewMember(newMember)
//    }
//
//    suspend fun getMembers(userId: String): List<Member> {
//        return memberApi.getMembersByUser(userId)
//    }
//}