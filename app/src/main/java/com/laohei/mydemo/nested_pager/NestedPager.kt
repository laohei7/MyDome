package com.laohei.mydemo.nested_pager

import android.content.ContentResolver
import android.net.Uri
import android.view.TextureView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.laohei.mydemo.R

@Composable
fun NestedPager() {
    val context = LocalContext.current
    val outerPagerState = rememberPagerState() { 5 }
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    HorizontalPager(
        state = outerPagerState
    ) { index ->
        val outActive = index == outerPagerState.currentPage
        when (index) {
            0 -> {
                val midPagerState = rememberPagerState() { 3 }
                HorizontalPager(
                    state = midPagerState
                ) { midIndex ->
                    val midActive = midIndex == midPagerState.currentPage
                    when (midIndex) {
                        0 -> VideoContent(
                            poster = R.drawable.image_front,
                            exoPlayer = exoPlayer,
                            video = null,
                            isActive = outActive && midActive
                        )

                        1 -> VideoContent(
                            poster = R.drawable.image_back,
                            exoPlayer = exoPlayer,
                            video = null,
                            isActive = outActive && midActive
                        )

                        else -> VideoContent(
                            poster = R.drawable.image_front,
                            exoPlayer = exoPlayer,
                            video = R.raw.v1,
                            isActive = outActive && midActive
                        )
                    }
                }
            }

            1 -> {
                val midPagerState = rememberPagerState() { 3 }
                HorizontalPager(
                    state = midPagerState
                ) { midIndex ->
                    val midActive = midIndex == midPagerState.currentPage
                    when (midIndex) {
                        0 -> VideoContent(
                            poster = R.drawable.image_front,
                            exoPlayer = exoPlayer,
                            video = null,
                            isActive = outActive && midActive
                        )

                        1 -> {
                            val thirdPagerState = rememberPagerState() { 2 }
                            HorizontalPager(
                                state = thirdPagerState
                            ) { innerIndex ->
                                val thirdActive = innerIndex == thirdPagerState.currentPage
                                when (innerIndex) {
                                    0 -> VideoContent(
                                        poster = R.drawable.image_front,
                                        exoPlayer = exoPlayer,
                                        video = R.raw.v2,
                                        isActive = outActive && midActive && thirdActive
                                    )

                                    1 -> VideoContent(
                                        poster = R.drawable.image_front,
                                        exoPlayer = exoPlayer,
                                        video = R.raw.v3,
                                        isActive = outActive && midActive && thirdActive
                                    )
                                }
                            }
                        }

                        else -> VideoContent(
                            poster = R.drawable.image_back,
                            exoPlayer = exoPlayer,
                            video = null,
                            isActive = outActive && midActive
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PosterImage(res: Int) {
    Image(
        painter = painterResource(res),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun VideoContent(
    poster: Int, video: Int?, exoPlayer: ExoPlayer, isActive: Boolean
) {
    var isShowCover by remember { mutableStateOf(true) }
    LaunchedEffect(isActive, video) {
        if (isActive && video != null) {
            val uri =
                Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE).path(video.toString())
                    .build()

            exoPlayer.setMediaItem(MediaItem.fromUri(uri))
            exoPlayer.prepare()
            exoPlayer.play()
        } else {
            // 当页面不可见或没有视频时，停止或暂停
            exoPlayer.pause() // 建议用 pause，比 stop 切换更流畅
        }
    }

    // 2. 处理首帧监听：控制封面显示
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onRenderedFirstFrame() {
                isShowCover = false
            }

            override fun onPlaybackStateChanged(state: Int) {
                // 如果开始缓冲或停止，可以显示封面防止黑屏
                if (state == Player.STATE_BUFFERING) {
                    // isShowCover = true // 可选
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            isShowCover = true
        }
    }

    if (video != null) {
        Box() {
//            PlayerSurface(player = exoPlayer, surfaceType = SURFACE_TYPE_TEXTURE_VIEW)
            AndroidView(factory = {
                TextureView(it)
            }, update = {
                exoPlayer?.setVideoTextureView(it)
            })
            if (isShowCover) {
                PosterImage(poster)
            }
        }

    } else {
        PosterImage(poster)
    }
}


fun pagerNestedScrollConnection(
    pagerState: PagerState
): NestedScrollConnection = object : NestedScrollConnection {

    override fun onPreScroll(
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        // ❗这里千万别吃
        return Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        if (source == NestedScrollSource.SideEffect) {
            return Offset(available.x, 0f)
        }

        if (source != NestedScrollSource.UserInput) return Offset.Zero

        val deltaX = available.x
        val isScrolling = pagerState.currentPageOffsetFraction != 0f

        val canScrollLeft =
            deltaX > 0 && (pagerState.currentPage > 0 || isScrolling)

        val canScrollRight =
            deltaX < 0 && (pagerState.currentPage < pagerState.pageCount - 1 || isScrolling)

        return if (canScrollLeft || canScrollRight) {
            Offset(deltaX, 0f)
        } else {
            Offset.Zero
        }
    }
}

@Composable
fun TestNestedPager() {
    val pager1 = rememberPagerState() { 3 }
    HorizontalPager(state = pager1) { index1 ->
        val pager2 = rememberPagerState() { 5 }
        Box(
            modifier = Modifier.nestedScroll(
                pagerNestedScrollConnection(pager2)
            )
        ) {
            HorizontalPager(
                state = pager2,
            ) { index2 ->
                val pager3 = rememberPagerState() { 2 }
                Box(
                    modifier = Modifier.nestedScroll(
                        pagerNestedScrollConnection(pager3)
                    )
                ) {
                    HorizontalPager(
                        state = pager3,

                        ) { index3 ->
                        val pager4 = rememberPagerState() { 6 }
                        Box(
                            modifier = Modifier.nestedScroll(
                                pagerNestedScrollConnection(pager4)
                            )
                        ) {
                            HorizontalPager(
                                state = pager4,
                            ) { index4 ->
                                // 最内层内容
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "$index1 $index2 $index3 $index4")
                                }
                            }
                        }

                    }
                }

            }
        }

    }

}