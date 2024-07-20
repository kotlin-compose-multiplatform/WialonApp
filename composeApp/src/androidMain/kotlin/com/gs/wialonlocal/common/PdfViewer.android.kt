package com.gs.wialonlocal.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

@Composable
actual fun PdfViewer(modifier: Modifier, url: String) {
    val pdfState = rememberVerticalPdfReaderState(
        resource = ResourceType.Base64(""),
        isZoomEnable = true,
    )

    VerticalPDFReader(
        state = pdfState,
        modifier = modifier,
    )
}