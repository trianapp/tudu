package app.trian.tudu.di

import app.trian.tudu.data.repository.UserRepositoryImpl
import app.trian.tudu.data.repository.design.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(value = [SingletonComponent::class])
object NetworkModule {
    @Provides
    fun provideUserRepository():UserRepository{
        return UserRepositoryImpl()
    }
}