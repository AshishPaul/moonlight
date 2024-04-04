//@file:OptIn(ExperimentalCoroutinesApi::class)
//
//package com.zerogravity.moonlight.mobile.common.data.local.dao
//
//import app.cash.sqldelight.coroutines.asFlow
//import app.cash.sqldelight.coroutines.mapToOneOrNull
//import com.zerogravity.moonlight.mobile.common.data.local.db.MoonlightMobileDb
//import com.zerogravity.moonlight.mobile.common.data.model.User
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.mapLatest
//
//class UserDao(
//    private val appDatabase: MoonlightMobileDb,
//    private val databaseDispatcher: CoroutineDispatcher
//) {
//    fun getUser(userId: String): Flow<User?> {
//        return appDatabase.userQueries.getById(userId).asFlow().mapToOneOrNull(databaseDispatcher)
//            .mapLatest {
//                it?.let {
//                    User(it.id, it.givenName, it.familyName, it.email, it.phoneNumber)
//                }
//            }
//    }
//
//    fun saveUser(user: User) {
//        appDatabase.userQueries.apply {
//            transaction {
//                insertOrUpdateItem(
//                    user.id, user.givenName, user.familyName, user.email, user.phoneNumber
//                )
//            }
//        }
//    }
//}