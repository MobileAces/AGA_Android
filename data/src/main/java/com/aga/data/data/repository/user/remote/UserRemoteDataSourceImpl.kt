package com.aga.data.data.repository.user.remote

import com.aga.data.data.api.UserService
import com.aga.data.data.model.mapper.toJoinRequest
import com.aga.data.data.model.mapper.toLoginRequest
import com.aga.data.data.model.mapper.toUser
import com.aga.data.data.model.mapper.toUserUpdateRequest
import com.aga.domain.model.User
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userService: UserService
): UserRemoteDataSource {
    override suspend fun login(user: User): User {
        val response = userService.login(user.toLoginRequest())
        return if(response.code == 200){
            response.data!!.toUser()
        }else{
            User("FAIL","","","")
        }
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

    override suspend fun join(user: User): User {
        val response = userService.join(user.toJoinRequest())
        return if(response.code == 201){
            response.data!!.toUser()
        }else{
            User("FAIL","","","")
        }
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
        return userService.updatePassword(id, prePw, newPw) == "Success"
    }
}