package com.aga.app.module

import com.aga.data.data.repository.alarm.remote.AlarmRemoteDataSource
import com.aga.data.data.repository.alarm.remote.AlarmRemoteDataSourceImpl
import com.aga.data.data.repository.statistics.remote.StatisticsRemoteDataSource
import com.aga.data.data.repository.statistics.remote.StatisticsRemoteDataSourceImpl
import com.aga.data.data.repository.team.remote.TeamRemoteDataSource
import com.aga.data.data.repository.team.remote.TeamRemoteDataSourceImpl
import com.aga.data.data.repository.teammember.remote.TeamMemberRemoteDataSource
import com.aga.data.data.repository.teammember.remote.TeamMemberRemoteDataSourceImpl
import com.aga.data.data.repository.user.remote.UserRemoteDataSource
import com.aga.data.data.repository.user.remote.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindTeamRemoteDataSource(teamRemoteDataSourceImpl: TeamRemoteDataSourceImpl): TeamRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindTeamMemberRemoteDataSource(teamMemberRemoteDataSourceImpl: TeamMemberRemoteDataSourceImpl): TeamMemberRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindAlarmRemoteDataSource(alarmRemoteDataSourceImpl: AlarmRemoteDataSourceImpl): AlarmRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindStatisticsRemoteDataSource(statisticsDataSourceImpl: StatisticsRemoteDataSourceImpl): StatisticsRemoteDataSource
}