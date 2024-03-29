package com.aga.app.module

import com.aga.data.data.repository.alarm.AlarmRepositoryImpl
import com.aga.data.data.repository.alarmdetail.AlarmDetailRepositoryImpl
import com.aga.data.data.repository.statistics.StatisticsRepositoryImpl
import com.aga.data.data.repository.statistics.remote.StatisticsRemoteDataSourceImpl
import com.aga.data.data.repository.invite.InviteRepositoryImpl
import com.aga.data.data.repository.team.TeamRepositoryImpl
import com.aga.data.data.repository.teammember.TeamMemberRepositoryImpl
import com.aga.data.data.repository.user.UserRepositoryImpl
import com.aga.data.data.repository.weather.WeatherRepositoryImpl
import com.aga.domain.repository.AlarmDetailRepository
import com.aga.domain.repository.AlarmRepository
import com.aga.domain.repository.StatisticsRepository
import com.aga.domain.repository.InviteRepository
import com.aga.domain.repository.TeamMemberRepository
import com.aga.domain.repository.TeamRepository
import com.aga.domain.repository.UserRepository
import com.aga.domain.repository.WeatherRepository
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
    abstract fun bindStatisticsRepository(statisticsRepositoryImpl: StatisticsRepositoryImpl): StatisticsRepository

    @Singleton
    @Binds
    abstract fun bindInviteRepository(inviteRepositoryImpl: InviteRepositoryImpl): InviteRepository

    @Singleton
    @Binds
    abstract fun bindAlarmDetailRepository(alarmDetailRepositoryImpl: AlarmDetailRepositoryImpl): AlarmDetailRepository

    @Singleton
    @Binds
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}