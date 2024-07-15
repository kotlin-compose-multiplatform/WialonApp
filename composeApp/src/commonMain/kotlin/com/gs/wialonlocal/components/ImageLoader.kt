package com.gs.wialonlocal.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.painterResource
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.car

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    url: String,
    contentScale: ContentScale = ContentScale.Inside
) {
    Image(
        painter = painterResource(Res.drawable.car),
        modifier = modifier,
        contentDescription = null,
        contentScale = contentScale
    )
}