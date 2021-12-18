package me.ghostbear.composewaifu.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import me.ghostbear.composewaifu.local.WaifuDatabase

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    @Provides
    fun provideWaifuDatabase(@ApplicationContext context: Context): WaifuDatabase {
        return Room.databaseBuilder(
            context,
            WaifuDatabase::class.java,
            "waifu-database"
        ).build()
    }

}