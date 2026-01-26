package com.laohei.mydemo.pixel_design

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.util.fastForEach
import com.laohei.mydemo.R
import com.laohei.mydemo.component.MenuItem
import com.laohei.mydemo.ui.theme.LatoStyle
import com.laohei.mydemo.util.px
import com.laohei.mydemo.util.textPx
import kotlinx.coroutines.launch

private val menus = listOf(
    R.mipmap.home to R.string.home,
    R.mipmap.discover to R.string.discover,
    R.mipmap.award to R.string.award,
    R.mipmap.celebrities to R.string.celebrities,
)

private val libraries = listOf(
    R.mipmap.recent to R.string.recent,
    R.mipmap.top_rated to R.string.top_rated,
    R.mipmap.downloaded to R.string.downloaded,
    R.mipmap.playlists to R.string.playlists,
    R.mipmap.watchlist to R.string.watchlist,
    R.mipmap.completed to R.string.completed,
)

private val generals = listOf(
    R.mipmap.settings to R.string.settings,
    R.mipmap.log_out to R.string.log_out,
)

private val tabs = listOf(
    R.string.movies,
    R.string.tv_shows,
    R.string.Anime,
)


@Composable
fun DesignScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        LeftArea()
        MainArea()
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(485.px())
                .background(background1)
                .padding(end = 132.px())
        ) { }
    }
}

@Composable
fun LeftArea() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(248.px())
            .background(background1)
            .padding(start = 39.px())
    ) {
        Image(
            painter = painterResource(R.mipmap.logo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 48.px())
                .width(70.px())
                .height(49.px())
        )

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 69.px())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.px())
            ) {
                Text(
                    text = "Menu",
                    fontSize = 18.textPx(),
                    style = LatoStyle(
                        fontSize = 18.textPx(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                menus.fastForEach {
                    MenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.px()),
                        leadingIcon = it.first,
                        title = it.second,
                        textStyle = LatoStyle(
                            fontSize = 18.textPx(),
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 59.px())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.px())
            ) {
                Text(
                    text = "Library",
                    fontSize = 18.textPx(),
                    style = LatoStyle(
                        fontSize = 18.textPx(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                libraries.fastForEach {
                    MenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.px()),
                        leadingIcon = it.first,
                        title = it.second,
                        textStyle = LatoStyle(
                            fontSize = 18.textPx(),
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 59.px(), bottom = 47.82f.px())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.px())

            ) {
                Text(
                    text = "General",
                    fontSize = 18.textPx(),
                    style = LatoStyle(
                        fontSize = 18.textPx(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )
                generals.fastForEach {
                    MenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.px()),
                        leadingIcon = it.first,
                        title = it.second,
                        textStyle = LatoStyle(
                            fontSize = 18.textPx(),
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainArea() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 1) { tabs.size }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(906.px())
            .background(background2)
    ) {

        MainHeader(currentIndex = pagerState.currentPage) { index ->
            scope.launch { pagerState.animateScrollToPage(index) }
        }

        HorizontalPager(
            state = pagerState
        ) { index ->
            when (index) {
                1 -> TVShowsPage()
                else -> {
                    Text(text = "$index")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHeader(
    currentIndex: Int,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 48.px())
            .padding(horizontal = 68.px())
            .fillMaxWidth()
            .height(56.px()),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        PrimaryTabRow(
            modifier = Modifier.weight(1f),
            selectedTabIndex = currentIndex,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(
                        currentIndex,
                        matchContentSize = true
                    ),
                    width = Dp.Unspecified,
                    color = buttonFocusColor
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, tab ->
                val selected = currentIndex == index
                Tab(
                    selected = selected,
                    selectedContentColor = buttonFocusColor,
                    unselectedContentColor = Color.White,
                    text = {
                        Text(
                            text = stringResource(tab),
                            style = LatoStyle(
                                fontSize = 24.textPx(),
                                fontWeight = if (selected) FontWeight.ExtraBold
                                else FontWeight.SemiBold,
                            )
                        )
                    },
                    onClick = { onClick(index) }
                )
            }
        }

        OutlinedTextField(
            modifier = Modifier
                .padding(start = 16.px())
                .width(315.px())
                .height(56.px()),
            value = "",
            onValueChange = {},
            shape = RoundedCornerShape(12.px()),
            colors = searchTextFieldColors(),
            leadingIcon = {
                Image(
                    painter = painterResource(R.mipmap.search),
                    contentDescription = null,
                    modifier = Modifier.size(24.px())
                )
            },
            trailingIcon = {
                Image(
                    painter = painterResource(R.mipmap.filter),
                    contentDescription = null,
                    modifier = Modifier
                        .height(24.px())
                        .width(20.px())
                )
            }
        )
    }
}