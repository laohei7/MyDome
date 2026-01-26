package com.laohei.mydemo.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.laohei.mydemo.util.px

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    leadingIcon: Int,
    title: Int,
    iconSize: Dp=24.px(),
    textStyle: TextStyle = TextStyle.Default
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.px())
    ) {
        Image(
            painter = painterResource(leadingIcon),
            contentDescription = null,
            modifier = Modifier.size(iconSize)
        )

        Text(
            text = stringResource(title),
            style = textStyle
        )
    }
}