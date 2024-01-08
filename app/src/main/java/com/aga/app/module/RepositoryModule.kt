package com.aga.app.module

import com.aga.data.data.repository.alarm.AlarmRepositoryImpl
import com.aga.data.data.repository.invite.InviteRepositoryImpl
import com.aga.data.data.repository.team.TeamRepositoryImpl
import com.aga.data.data.repository.teammember.TeamMemberRepositoryImpl
import com.aga.data.data.repository.user.UserRepositoryImpl
import com.aga.domain.repository.AlarmRepository
import com.aga.domain.repository.InviteRepository
import com.aga.domain.repository.TeamMemberRepository
import com.aga.domain.repository.TeamRepository
import com.aga.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindTeamRepository(teamRepositoryImpl: TeamRepositoryImpl): TeamRepository

    @Singleton
    @Binds
    abstract fun bindTeamMemberRepository(teamMemberRepositoryImpl: TeamMemberRepositoryImpl): TeamMemberRepository

    @Singleton
    @Binds
    abstract fun bindAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository

    @Singleton
    @Binds
    abstract fun bindInviteRepository(inviteRepositoryImpl: InviteRepositoryImpl): InviteRepository
}