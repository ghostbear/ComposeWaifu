package me.ghostbear.composewaifu.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.ghostbear.composewaifu.data.WaifuApi
import me.ghostbear.composewaifu.data.WaifuApiImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindWaifuApi(waifuApiImpl: WaifuApiImpl): WaifuApi

}