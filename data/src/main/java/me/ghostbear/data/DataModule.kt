package me.ghostbear.data

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import me.ghostbear.data.waifu.local.WaifuDatabase
import me.ghostbear.data.waifu.remote.WaifuApi
import me.ghostbear.data.waifu.remote.WaifuApiImpl
import javax.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindWaifuApi(waifuApiImpl: WaifuApiImpl): WaifuApi

    @Module
    @InstallIn(SingletonComponent::class)
    object Provider {

        @Provides
        fun provideHttpClient(): HttpClient {
            return HttpClient(CIO) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }
        }

        @Provides
        @Singleton
        fun provideWaifuDatabase(@ApplicationContext context: Context): WaifuDatabase {
            return Room.databaseBuilder(
                context,
                WaifuDatabase::class.java,
                "waifu-database"
            ).build()
        }
    }
}