package com.tamtyberg.tammyshutterflyproj.di

import com.tamtyberg.tammyshutterflyproj.data.CarouselRepository
import com.tamtyberg.tammyshutterflyproj.data.LocalCarouselRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CarouselModule {

    @Provides
    fun provideCarouselRepository(): CarouselRepository {
        return LocalCarouselRepository()
    }
}
