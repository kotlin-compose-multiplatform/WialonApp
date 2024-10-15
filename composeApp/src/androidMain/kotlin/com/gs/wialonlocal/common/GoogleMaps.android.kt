package com.gs.wialonlocal.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
import com.google.maps.android.PolyUtil
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.gs.wialonlocal.R
import com.gs.wialonlocal.features.geofence.data.entity.geofence.P
import com.gs.wialonlocal.features.monitoring.data.entity.history.Trip
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

fun joinPolylines(encodedPolylines: List<String>): String {
    val allPoints = mutableListOf<LatLng>()

    // Decode each polyline string and add points to the list
    for (encodedPolyline in encodedPolylines) {
        val decodedPoints = PolyUtil.decode(encodedPolyline)
        allPoints.addAll(decodedPoints)
    }

    // Encode the combined list of LatLng points into a single polyline string
    return PolyUtil.encode(allPoints)
}


// In-memory cache to store downloaded bitmaps
val bitmapCache = mutableMapOf<String, Bitmap>()


// Helper function to interpolate between two colors
fun interpolateColor(startColor: Color, endColor: Color, fraction: Float): Color {
    return Color(
        red = lerp(startColor.red, endColor.red, fraction),
        green = lerp(startColor.green, endColor.green, fraction),
        blue = lerp(startColor.blue, endColor.blue, fraction),
        alpha = 1f
    )
}

// Linear interpolation function
fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return (start + fraction * (stop - start))
}

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
    geofences: Map<String, List<P>>,
    onUnitClick: (UnitModel) -> Unit,
    mapType: com.gs.wialonlocal.features.settings.data.settings.MapType,
    polyline: List<String?>?,
    singleMarker: LatLong?
) {
    val mapProperties by remember { mutableStateOf(MapProperties(
        isMyLocationEnabled = false,
        isBuildingEnabled = true,
        isTrafficEnabled = false,
        mapType = when(mapType) {
            com.gs.wialonlocal.features.settings.data.settings.MapType.SATELLITE -> MapType.SATELLITE
            com.gs.wialonlocal.features.settings.data.settings.MapType.HYBRID -> MapType.HYBRID
            else -> MapType.NORMAL
        }
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
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true
        )
    ) {
        key(geofences) {
            geofences.entries.forEachIndexed { index, entry ->
                entry.value.forEach { geofence ->
                    Circle(
                        center = LatLng(geofence.y, geofence.x),
                        radius = geofence.r.toDouble(),
                        strokeColor = Color.Red,  // Customize the color
                        fillColor = Color(0x5500FF00),  // Semi-transparent green fill color
                        strokeWidth = 3f
                    )
                }

                // Draw the transparent polygon connecting the center points
                if (entry.value.size > 2) { // We need at least 3 points to form a polygon
                    Polygon(
                        points = entry.value.map { LatLng(it.y, it.x) },  // Use the center points of the geofences
                        strokeColor = Color.Transparent,  // Transparent stroke
                        fillColor = Color(0x550000FF),    // Semi-transparent blue fill color
                        strokeWidth = 0f  // Set stroke width to 0 for fully transparent borders
                    )
                }
            }
        }

        key(singleMarker) {
            singleMarker?.let {
                Marker(
                    state = MarkerState(
                        LatLng(
                            singleMarker.latitude,
                            singleMarker.longitude
                        )
                    ),
                    title = "Parking",
                )
            }
        }

        key(polyline) {
            polyline?.let { path->
                path.forEach { p->
                    val list = PolyUtil.decode(p)
                    Polyline(
                        points = list,
                        color = Color(0xFF009AFF),
                        width = 10.0f
                    )
                }

                if(path.isNotEmpty()) {
                    val list = PolyUtil.decode(path.first())
                    if(list.isNotEmpty()) {
                        MarkerComposable(
                            state = MarkerState(position = list.first()),
                        ) {
                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = R.drawable.points),
                                contentDescription = "Start",
                            )
                        }


                    }

                    val end = PolyUtil.decode(path.last())
                    if(end.isNotEmpty()) {
                        MarkerComposable(
                            state = MarkerState(position = list.last()),
                        ) {
                            Image(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(id = R.drawable.finish2),
                                contentDescription = "End",
                            )
                        }
                    }
                }
            }
        }
        key(units) {
            units.forEachIndexed { index, unitModel ->
                val bitmap = markerImages.value[unitModel.image]
                val bitmapDescriptor = bitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }
                val markerState = rememberMarkerState(unitModel.carNumber.plus(unitModel.id), LatLng(unitModel.latitude, unitModel.longitude))
                Marker(
                    state = markerState,
                    icon = bitmapDescriptor,
                    title = unitModel.carNumber,
                    onClick = {
                        markerState.showInfoWindow()
                        true
                    },
                    onInfoWindowClick = {
                        onUnitClick(unitModel)
                    }
                )


                // Gradient colors
                val startColor = Color.Gray
                val endColor = Color.Green

                val points = unitModel.trips.map {
                    LatLng(it.latitude, it.longitude)
                }

                for (i in 0 until  points.size - 1) {
                    val startPoint = points[i]
                    val endPoint = points[i + 1]
                    val fraction = i / (points.size - 1).toFloat()
                    val color = interpolateColor(startColor, endColor, fraction)

                    // Draw polyline segment
                    Polyline(
                        points = listOf(startPoint, endPoint),
                        color = color,
                        width = 10f
                    )
                }


            }
        }
    }
}

