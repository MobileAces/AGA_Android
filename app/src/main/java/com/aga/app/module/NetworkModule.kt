package com.aga.app.module

import com.aga.data.data.api.AlarmDetailService
import com.aga.data.data.api.AlarmService
import com.aga.data.data.api.ApiClient.BASE_URL
import com.aga.data.data.api.InviteService
import com.aga.data.data.api.TeamMemberService
import com.aga.data.data.api.TeamService
import com.aga.data.data.api.UserService
import com.aga.data.data.api.WakeUpService
import com.aga.data.data.api.WeatherService
import com.aga.data.data.repository.user.UserRepositoryImpl
import com.aga.domain.repository.UserRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun moshi() = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providesRetrofitClient(
        moshi: Moshi
    ): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideTeamService(retrofit: Retrofit): TeamService{
        return retrofit.create(TeamService::class.java)
    }

    @Provides
    @Singleton
    fun provideTeamMemberService(retrofit: Retrofit): TeamMemberService{
        return retrofit.create(TeamMemberService::class.java)
    }

    @Provides
    @Singleton
    fun providerAlarmService(retrofit: Retrofit): AlarmService{
        return retrofit.create(AlarmService::class.java)
    }

    @Provides
    @Singleton
    fun provideWakeUpService(retrofit: Retrofit): WakeUpService{
        return retrofit.create(WakeUpService::class.java)
    }

    @Provides
    @Singleton
    fun providerInviteService(retrofit: Retrofit): InviteService{
        return retrofit.create(InviteService::class.java)
    }

    @Provides
    @Singleton
    fun providerAlarmDetailService(retrofit: Retrofit): AlarmDetailService{
        return retrofit.create(AlarmDetailService::class.java)
    }

    @Provides
    @Singleton
    fun providerWeatherService(retrofit: Retrofit): WeatherService{
        return retrofit.create(WeatherService::class.java)
    }
}