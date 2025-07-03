package com.tamtyberg.tammyshutterflyproj.di

import com.tamtyberg.tammyshutterflyproj.UndoRedoManager
import com.tamtyberg.tammyshutterflyproj.data.CarouselRepository
import com.tamtyberg.tammyshutterflyproj.data.LocalCarouselRepository
import com.tamtyberg.tammyshutterflyproj.util.CanvasUndoRedoManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CarouselModule {

    @Provides
    fun provideCarouselRepository(): CarouselRepository {
        return LocalCarouselRepository()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object UndoRedoModule {

    @Provides
    fun provideUndoRedoManager(): UndoRedoManager = CanvasUndoRedoManager()
}
