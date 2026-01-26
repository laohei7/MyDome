package com.laohei.mydemo.pixel_design

import android.content.Intent
import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.laohei.mydemo.R
import com.laohei.mydemo.component.BlurButton
import com.laohei.mydemo.service.FloatingService
import com.laohei.mydemo.ui.theme.LatoStyle
import com.laohei.mydemo.util.checkOverlayPermission
import com.laohei.mydemo.util.px
import com.laohei.mydemo.util.requestOverlayPermission
import com.laohei.mydemo.util.startSplitScreen
import com.laohei.mydemo.util.startSplitScreenSync
import com.laohei.mydemo.util.textPx

private data class Role(
    val name: Int,
    val cover: Int,
    val score: Float,
    val ep: Int,
    val movie: Int
)

private val populars = listOf(
    Role(
        name = R.string.loki,
        cover = R.mipmap.popular_1,
        score = 4.5f,
        ep = 6,
        movie = R.string.superhero
    ),
    Role(
        name = R.string.chernobyl,
        cover = R.mipmap.popular_2,
        score = 5f,
        ep = 5,
        movie = R.string.mini_series
    ),
    Role(
        name = R.string.rick_and_morty,
        cover = R.mipmap.popular_3,
        score = 5f,
        ep = 49,
        movie = R.string.fantasy
    ),
)

@Composable
fun TVShowsPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Swiper()
        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 60.px(), start = 68.px()),
            text = "Popular on TinyMoviez",
            style = LatoStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 30.textPx(),
                color = Color.White
            )
        )

        LazyRow(
            modifier = Modifier
                .padding(top = 30.px()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.px())
        ) {
            item { Spacer(Modifier.width(38.px())) }
            items(populars) { item ->
                PopularRoleItem(role = item)
            }
            item { Spacer(Modifier.width(25.px())) }
        }
    }
}

@Composable
private fun Swiper() {
    val activity = LocalActivity.current
    val context = LocalContext.current
    val swiperState = rememberPagerState() { 1 }
    Box(
        modifier = Modifier
            .padding(top = 62.px())
            .width(770.px())
            .height(350.px())
    ) {
        HorizontalPager(
            state = swiperState
        ) {
            SwiperItem(
                cover = R.mipmap.tv_1,
                title = "The Crown"
            )
        }

        BlurButton(
            onClick = {
                context.startSplitScreen("com.youdao.dict", "tv.danmaku.bilibilihd")
                if (activity?.checkOverlayPermission() == true) {
                    val intent = Intent(context, FloatingService::class.java)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                    } else {
                        context.startService(intent)
                    }
                    context.startService(intent)
                } else {
                    activity?.requestOverlayPermission()
                }
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.px())
                .size(50.px())
                .clip(RoundedCornerShape(15.px())),
            blur = 10.px(),
            shape = RoundedCornerShape(15.px()),
            background = buttonColor.copy(0.2f),
            contentPadding = PaddingValues.Zero
        ) {
            Image(
                painter = painterResource(R.mipmap.previous),
                contentDescription = null,
                modifier = Modifier
                    .width(8.px())
                    .height(14.px())
            )
        }

        BlurButton(
            onClick = {
                if (activity?.checkOverlayPermission() == true) {
                    val intent = Intent(context, FloatingService::class.java)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(intent)
                    } else {
                        context.startService(intent)
                    }
                    context.startService(intent)
                } else {
                    activity?.requestOverlayPermission()
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.px())
                .size(50.px())
                .clip(RoundedCornerShape(15.px())),
            blur = 10.px(),
            shape = RoundedCornerShape(15.px()),
            background = buttonColor.copy(0.2f),
            contentPadding = PaddingValues.Zero
        ) {
            Image(
                painter = painterResource(R.mipmap.next),
                contentDescription = null,
                modifier = Modifier
                    .width(8.px())
                    .height(14.px())
            )
        }

        BlurButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.px())
                .padding(bottom = 21.px())
                .width(160.px())
                .height(56.px())
                .clip(RoundedCornerShape(15.px())),
            blur = 10.px(),
            shape = RoundedCornerShape(15.px()),
            background = buttonColor.copy(0.2f),
            contentPadding = PaddingValues(horizontal = 25.px(), vertical = 19.px())
        ) {
            Image(
                painter = painterResource(R.mipmap.icon_add),
                contentDescription = null,
                modifier = Modifier.size(17.px())
            )
            Spacer(Modifier.width(15.px()))
            Text(
                text = stringResource(R.string.watchlist),
                style = LatoStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.textPx(),
                )
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.px())
                .padding(bottom = 21.px())
                .width(190.px())
                .height(56.px()),
            colors = watchButtonColors(),
            shape = RoundedCornerShape(15.px()),
            contentPadding = PaddingValues(horizontal = 47.px(), vertical = 19.px())
        ) {
            Text(
                text = "Watch Now",
                style = LatoStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.textPx(),
                )
            )
        }
    }
}

@Composable
private fun SwiperItem(
    cover: Int,
    title: String
) {
    Box {
        Image(
            painter = painterResource(cover),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.px())),
            contentScale = ContentScale.Crop
        )

        Text(
            text = title,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.px())
                .padding(start = 40.px()),
            style = LatoStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 48.textPx(),
                color = Color.White
            )
        )
    }
}

@Composable
private fun PopularRoleItem(
    role: Role
) {
    Box(
        modifier = Modifier
            .width(237.px())
            .height(298.px())
            .clip(RoundedCornerShape(20.px()))
    ) {
        Image(
            painter = painterResource(role.cover),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = imageCoverBrush
                    )
                }
        )
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 21.px())
                .padding(start = 21.px()),
            verticalArrangement = Arrangement.spacedBy(12.px())
        ) {
            Text(
                text = stringResource(role.name),
                style = LatoStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.textPx(),
                    color = Color.White
                )
            )


            Row() {
                repeat(5) { index ->
                    val starIndex = index + 1
                    val painter = when {
                        role.score >= starIndex -> painterResource(R.mipmap.star)

                        role.score >= starIndex - 0.5f -> painterResource(R.mipmap.half_star)

                        else -> null
                    }
                    painter?.let {
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier.size(15.px()) // 根据你的尺寸调整
                        )

                        if (index < 4) {
                            Spacer(modifier = Modifier.width(5.62f.px()))
                        }
                    }
                }
            }
        }



        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.px())
                .padding(bottom = 10.px()),
            verticalArrangement = Arrangement.spacedBy(14.px())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${role.ep}Ep",
                    style = LatoStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.textPx(),
                        color = buttonFocusColor
                    )
                )

                Text(
                    text = "${role.movie}",
                    style = LatoStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.textPx(),
                        color = buttonFocusColor
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BlurButton(
                    onClick = {},
                    modifier = Modifier
                        .size(57.px())
                        .clip(RoundedCornerShape(15.px())),
                    blur = 10.px(),
                    shape = RoundedCornerShape(15.px()),
                    background = buttonColor.copy(0.2f),
                    contentPadding = PaddingValues.Zero
                ) {
                    Image(
                        painter = painterResource(R.mipmap.icon_add),
                        contentDescription = null,
                        modifier = Modifier
                            .size(17.px())
                    )
                }

                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(150.px())
                        .height(57.px()),
                    colors = watchButtonColors(),
                    shape = RoundedCornerShape(15.px()),
                    contentPadding = PaddingValues(horizontal = 47.px(), vertical = 19.px())
                ) {
                    Text(
                        text = "Watch",
                        style = LatoStyle(
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.textPx(),
                        )
                    )
                }
            }
        }
    }
}