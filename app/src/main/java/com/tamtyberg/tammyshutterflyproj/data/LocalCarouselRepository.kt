package com.tamtyberg.tammyshutterflyproj.data

import com.tamtyberg.tammyshutterflyproj.R
import com.tamtyberg.tammyshutterflyproj.model.CarouselItem


class LocalCarouselRepository : CarouselRepository {
    override suspend fun getCarouselItems(): List<CarouselItem> = listOf(
        CarouselItem(0, R.drawable.yacht, "yacht"),
        CarouselItem(1, R.drawable.woman, "woman"),
        CarouselItem(2, R.drawable.radio, "radio"),
        CarouselItem(3, R.drawable.pamela_hat, "pamela_hat"),
        CarouselItem(4, R.drawable.hawaiian_shirt, "hawaiian_shirts"),
        CarouselItem(5, R.drawable.guitar, "guitar"),
        CarouselItem(6, R.drawable.coconut_drink, "coconut_drink"),
        CarouselItem(7, R.drawable.parasol, "parasol"),
    )
}
