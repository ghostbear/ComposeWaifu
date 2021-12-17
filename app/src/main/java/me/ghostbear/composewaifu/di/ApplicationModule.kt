package me.ghostbear.composewaifu.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

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

}