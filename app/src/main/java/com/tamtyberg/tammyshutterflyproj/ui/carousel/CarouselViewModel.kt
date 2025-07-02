package com.tamtyberg.tammyshutterflyproj.ui.carousel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamtyberg.tammyshutterflyproj.model.CarouselItem
import com.tamtyberg.tammyshutterflyproj.data.CarouselRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarouselViewModel @Inject constructor(
    private val repository: CarouselRepository
) : ViewModel() {

    private var _items = mutableStateOf<List<CarouselItem>>(emptyList())
    val items: State<List<CarouselItem>> get() = _items

    init {
        viewModelScope.launch {
            _items.value = repository.getCarouselItems()
        }
    }
}
