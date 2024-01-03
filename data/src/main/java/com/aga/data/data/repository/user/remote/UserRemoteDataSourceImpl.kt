package com.aga.data.data.repository.user.remote

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
        return userService.isDuplicatedId(id)
    }

    override suspend fun isDuplicatedPhone(phone: String): Boolean {
        return userService.isDuplicatedPhone(phone)
    }

    override suspend fun isDuplicatedNickname(nickname: String): Boolean {
        return userService.isDuplicatedNickname(nickname)
    }

    override suspend fun join(user: User): Boolean {
        return userService.join(user.toJoinRequest()).isSuccessful
    }

    override suspend fun getUserInfo(id: String): User {
        val response = userService.getUserInfo(id)
        return if (response.code == 200){
            response.data!!.toUser()
        }else{
            User("FAIL","","","")
        }
    }

    override suspend fun deleteUser(id: String): Boolean {
        return userService.deleteUser(id) == "Success"
    }

    override suspend fun updateUser(user: User): User {
        val response = userService.updateUser(user.toUserUpdateRequest())
        return if(response.code == 200){
            response.data!!.toUser()
        }else{
            User("FAIL","","","")
        }
    }

    override suspend fun updatePassword(id: String, prePw: String, newPw: String): Boolean {
        return userService.updatePassword(
            PasswordChangeRequest(
                id,
                prePw,
                newPw
            )
        ).data
    }

}