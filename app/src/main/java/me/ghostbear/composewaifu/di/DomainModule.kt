package me.ghostbear.composewaifu.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ghostbear.data.waifu.repository.WaifuRepositoryImpl
import me.ghostbear.domain.waifu.repository.WaifuRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun provideWaifuRepository(waifuRepositoryImpl: WaifuRepositoryImpl): WaifuRepository

}