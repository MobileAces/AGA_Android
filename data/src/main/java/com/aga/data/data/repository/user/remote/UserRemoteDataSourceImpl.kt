package com.aga.data.data.repository.user.remote

import android.util.Log
import com.aga.data.data.api.UserService
import com.aga.data.data.model.mapper.toJoinRequest
import com.aga.data.data.model.mapper.toLoginRequest
import com.aga.data.data.model.mapper.toUser
import com.aga.data.data.model.mapper.toUserUpdateRequest
import com.aga.data.data.model.user.passwordchange.PasswordChangeRequest
import com.aga.domain.model.User
import javax.inject.Inject

private const val TAG = "UserRemoteDataSourceImp_AWSOME"
class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
): UserRemoteDataSource {
    override suspend fun login(user: User): String {
        val response = userService.login(user.toLoginRequest())
        return if (response.isSuccessful){
            response.body()!!.data!!.userId
        }else
            "LOGIN_FAIL"
    }

    override suspend fun isDuplicatedId(id: String): Boolean {
        return userService.isDuplicatedId(id).body()?.data!!.result
    }

    override suspend fun isDuplicatedPhone(phone: String): Boolean {
        return userService.isDuplicatedPhone(phone).body()?.data!!.result
    }

    override suspend fun isDuplicatedNickname(nickname: String): Boolean {
        return userService.isDuplicatedNickname(nickname).body()?.data!!.result
    }

    override suspend fun join(user: User): Boolean {
        return userService.join(user.toJoinRequest()).isSuccessful
    }

    override suspend fun getUserInfo(id: String): User {
        val response = userService.getUserInfo(id)
        return if (response.isSuccessful)
            response.body()?.data!!.toUser()
        else User("NOT_FOUND", "", "", "")
    }

    override suspend fun deleteUser(id: String): Boolean {
        return userService.deleteUser(id).isSuccessful
    }

    override suspend fun updateUser(user: User): User {
        val response = userService.updateUser(user.toUserUpdateRequest())
        return if(response.isSuccessful){
            response.body()?.data!!.toUser()
        }else{
            User("UPDATE_FAIL","","","")
        }
    }

    override suspend fun updatePassword(id: String, prePw: String, newPw: String): String {
        val response = userService.updatePassword(
            PasswordChangeRequest(
                id,
                prePw,
                newPw
            )
        )
        Log.d(TAG, "updatePassword: ${response.code()}")
        return if (response.isSuccessful)
            response.body()!!.message
        else
            "UPDATE_FAIL"
    }

}