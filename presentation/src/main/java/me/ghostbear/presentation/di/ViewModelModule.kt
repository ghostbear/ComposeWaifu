package me.ghostbear.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import me.ghostbear.ui.favorities.FavoriteViewState
import me.ghostbear.ui.gallery.GalleryViewState
import me.ghostbear.ui.picture.PictureViewState

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideFavoriteViewState(): FavoriteViewState {
        return FavoriteViewState.Empty
    }

    @Provides
    fun provideGalleryViewState(): GalleryViewState {
        return GalleryViewState.Empty
    }

    @Provides
    fun providePictureViewState(): PictureViewState {
        return PictureViewState.Empty
    }

}