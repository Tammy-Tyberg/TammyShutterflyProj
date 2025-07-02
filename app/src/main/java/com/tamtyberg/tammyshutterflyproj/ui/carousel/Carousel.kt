package com.tamtyberg.tammyshutterflyproj.ui.carousel

import android.content.ClipData
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tamtyberg.tammyshutterflyproj.model.CarouselItem


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Carousel(items: List<CarouselItem>) {
    val haptics = LocalHapticFeedback.current

    HorizontalUncontainedCarousel(
        state = rememberCarouselState { items.size },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        itemWidth = 124.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = items[i]

        Image(
            modifier = Modifier
                .background(Color.LightGray.copy(alpha = 0.05f))
                .clip(MaterialTheme.shapes.medium)
                .padding(8.dp)
                .dragAndDropSource {
                    detectTapGestures(
                        onLongPress = {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            startTransfer(
                                DragAndDropTransferData(
                                    ClipData(
                                        "draggedImageRes",
                                        arrayOf("image/resource-id"),
                                        ClipData.Item(item.imageResId.toString())
                                    )
                                )
                            )
                        }
                    )
                }
                .height(124.dp),
            painter = painterResource(id = item.imageResId),
            contentDescription = item.contentDescription,
            contentScale = ContentScale.Inside
        )
    }
}


