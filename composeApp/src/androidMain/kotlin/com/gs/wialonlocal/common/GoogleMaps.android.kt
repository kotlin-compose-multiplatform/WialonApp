package com.gs.wialonlocal.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.decode.SvgDecoder
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.gs.wialonlocal.features.monitoring.data.entity.history.Trip
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

// In-memory cache to store downloaded bitmaps
val bitmapCache = mutableMapOf<String, Bitmap>()

// Function to download and resize a bitmap from a URL, with caching
suspend fun getBitmapFromURL(imageUrl: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        // Check if the bitmap is already in the cache
        if (bitmapCache.containsKey(imageUrl)) {
            return@withContext bitmapCache[imageUrl]
        }

        try {
            // Open a connection to the image URL
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            // Get the input stream and decode it into a bitmap
            val inputStream = connection.inputStream
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            // Resize the bitmap
            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 100, 100, false)

            // Cache the bitmap for future use
            bitmapCache[imageUrl] = resizedBitmap

            // Return the resized bitmap
            resizedBitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    cameraPosition: com.gs.wialonlocal.common.CameraPosition,
    units: List<UnitModel>,
    onUnitClick: (UnitModel) -> Unit
) {
    val mapProperties by remember { mutableStateOf(MapProperties(
        isMyLocationEnabled = false,
        mapType = MapType.HYBRID
    )) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude), cameraPosition.zoom) // Example LatLng
    }

    val markerImages = remember { mutableStateOf<Map<String, Bitmap>>(emptyMap()) }
    val coroutineScope = rememberCoroutineScope()

//    LaunchedEffect(cameraPosition) {
//        cameraPositionState.animate(
//            update = CameraUpdateFactory.newCameraPosition(
//                CameraPosition(
//                    LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude),
//                    cameraPosition.zoom, 0f, 0f)
//            ),
//            durationMs = 1000
//        )
//    }

    fun getImages() {
        coroutineScope.launch {
            markerImages.value = emptyMap()
            units.forEach { unit ->
                try {
                    val bitmap = getBitmapFromURL(unit.image)
                    if (bitmap != null) {
                        markerImages.value = markerImages.value.plus(unit.image to bitmap)
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
        }
    }

    LaunchedEffect(units) {
        getImages()
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = mapProperties,
        cameraPositionState = cameraPositionState
    ) {
        key(units) {
            units.forEachIndexed { index, unitModel ->
                val bitmap = markerImages.value[unitModel.image]
                val bitmapDescriptor = bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }

                Marker(
                    state = MarkerState(position = LatLng(unitModel.latitude, unitModel.longitude)),
                    icon = bitmapDescriptor,
                    title = unitModel.carNumber,
                    snippet = unitModel.address,
                    onClick = {
                        onUnitClick(unitModel)
                        true
                    }
                )

                Polyline(
                    points = unitModel.trips.map {
                        LatLng(it.latitude, it.longitude)
                    },
                    color = Color.Blue
                )
            }
        }
    }
}

