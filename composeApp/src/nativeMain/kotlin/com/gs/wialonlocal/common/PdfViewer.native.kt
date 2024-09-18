package com.gs.wialonlocal.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.PDFKit.PDFDocument
import platform.PDFKit.PDFView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PdfViewer(modifier: Modifier, url: String, base64: String,  share: (()-> Unit)-> Unit) {
    val pdfView = rememberSaveable {
        PDFView().apply {
            autoScales = true
        }
    }

    UIKitView(
        modifier = modifier,
        factory = {
            pdfView.apply {
                val document = PDFDocument(uRL = NSURL(string = "https://pdfobject.com/pdf/sample.pdf"))
                pdfView.document = document
            }
            pdfView
        },
        update = { view->

        }
    )
}