package com.laohei.mydemo.shared_nav

import androidx.compose.animation.EnterExitState
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.laohei.mydemo.R
import kotlinx.serialization.Serializable

@Preview(showBackground = true)
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedNav() {
    val navController = rememberNavController()
    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = SharedNavRoute.Home,
            enterTransition = { fadeIn(tween(300),1f) },
            exitTransition = { fadeOut(tween(300),1f) },
            popEnterTransition = { fadeIn(tween(300),1f) },
            popExitTransition = { fadeOut(tween(300),1f) }
        ) {
            composable<SharedNavRoute.Home> {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        Modifier
                            .size(60.dp)
                            .background(Color.Green)
                            .sharedBounds(
                                rememberSharedContentState(key = "image_key1"),
                                animatedVisibilityScope = this@composable,
                                boundsTransform = { _, _ ->
                                    tween(300)
                                },

                            )
                            .clickable {
                                navController.navigate(SharedNavRoute.Second(0))
                            }
                    )
                    Spacer(Modifier.height(30.dp))
                    Box(
                        Modifier
                            .size(60.dp)
                            .background(Color.Green)
                            .sharedBounds(
                                rememberSharedContentState(key = "image_key2"),
                                animatedVisibilityScope = this@composable,
                                boundsTransform = { _, _ ->
                                    tween(300)
                                },
                            )
                            .clickable {
                                navController.navigate(SharedNavRoute.Second(1))
                            }
                    )
                }
            }
            composable<SharedNavRoute.Second> { entry ->
                val params = entry.toRoute<SharedNavRoute.Second>()
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize()
                        .sharedBounds(
                            rememberSharedContentState(key = if (params.index == 0) "image_key1" else "image_key2"),
                            animatedVisibilityScope = this@composable,
                            boundsTransform = { _, _ ->
                                tween(300)
                            },
                            enter = fadeIn(initialAlpha = 1f),
                            exit = fadeOut(targetAlpha = 0f),
                        )
                        .clip(RectangleShape),
                    state = rememberPagerState(initialPage = params.index) { 2 }
                ) { index ->
                    if (index == 0) {
                        Box(

                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.image_front),
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )

                            Button(onClick = { navController.navigateUp() }) {
                                Text(text = "返回")
                            }
                        }
                    } else {
                        Box(

                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.image_back),
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )

                            Button(onClick = { navController.navigateUp() }) {
                                Text(text = "返回")
                            }
                        }
                    }

                }
            }
        }
    }
}

@Serializable
sealed class SharedNavRoute {
    @Serializable
    data object Home : SharedNavRoute()

    @Serializable
    data class Second(val index: Int) : SharedNavRoute()
}