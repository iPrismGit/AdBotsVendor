package com.iprism.adbotsvendor.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class WelcomeBanner(
    val image: Int
)

@Composable
fun Banners(banners: List<WelcomeBanner>) {
    val pagerState = rememberPagerState(pageCount = { banners.size })
    val scope = rememberCoroutineScope()

    // Auto Scroll Effect
    LaunchedEffect(pagerState.currentPage) {
        delay(2500)  // delay for each slide
        val nextPage =
            if (pagerState.currentPage == banners.lastIndex) 0
            else pagerState.currentPage + 1

        scope.launch {
            pagerState.animateScrollToPage(nextPage)
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalPager(state = pagerState) { page ->
            BannerItem(banners[page])
        }
        Row(
            modifier = Modifier.height(8.dp)
        ) {
            repeat(pagerState.pageCount) {
                val color = if (pagerState.currentPage == it) Color.Red else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(10.dp)
                        .background(color = color, shape = CircleShape)
                ) {
                }
            }
        }
    }
}

@Composable
fun BannerItem(banner: WelcomeBanner) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(banner.image),
            contentDescription = null,
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Fit
        )
    }
}