package com.tamtyberg.tammyshutterflyproj.data

import com.tamtyberg.tammyshutterflyproj.model.CarouselItem

interface CarouselRepository {
    suspend fun getCarouselItems(): List<CarouselItem>
}
