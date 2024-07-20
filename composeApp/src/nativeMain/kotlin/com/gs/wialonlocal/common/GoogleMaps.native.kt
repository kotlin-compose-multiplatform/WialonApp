package com.gs.wialonlocal.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSAdvancedMarker.Companion.markerImageWithColor
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSCircle
import cocoapods.GoogleMaps.GMSCoordinateBounds
import cocoapods.GoogleMaps.GMSFeatureLayer
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMapViewDelegateProtocol
import cocoapods.GoogleMaps.GMSMarker
import cocoapods.GoogleMaps.GMSMutablePath
import cocoapods.GoogleMaps.GMSPolyline
import cocoapods.GoogleMaps.animateWithCameraUpdate
import cocoapods.GoogleMaps.kGMSTypeNormal
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreLocation.CLLocationCoordinate2D
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.MapKit.MKMapView
import platform.UIKit.UIColor
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun GoogleMaps(
    modifier: Modifier,
    isMapOptionSwitchesVisible: Boolean,
    isTrackingEnabled: Boolean,
    userLocation: LatLong?,
    markers: List<Marker>?,
    shouldCalcClusterItems: Boolean,
    onDidCalculateClusterItemList: () -> Unit,
    shouldSetInitialCameraPosition: CameraPosition?,
    shouldCenterCameraOnLatLong: LatLong?,
    onDidCenterCameraOnLatLong: () -> Unit,
    cameraLocationBounds: CameraLocationBounds?,
    polyLine: List<LatLong>?,
    onMapClick: ((LatLong) -> Unit)?,
    onMapLongClick: ((LatLong) -> Unit)?,
    onMarkerInfoClick: ((Marker) -> Unit)?,
    seenRadiusMiles: Double,
    cachedMarkersLastUpdatedLocation: Location?,
    onToggleIsTrackingEnabledClick: (() -> Unit)?,
    onFindMeButtonClick: (() -> Unit)?,
    isMarkersLastUpdatedLocationVisible: Boolean,
    shouldShowInfoMarker: Marker?,
    onDidShowInfoMarker: () -> Unit,
    shouldZoomToLatLongZoom: LatLongZoom?,
    onDidZoomToLatLongZoom: () -> Unit,
    shouldAllowCacheReset: Boolean,
    onDidAllowCacheReset: () -> Unit
) {
    var isMapSetupCompleted by remember { mutableStateOf(false) }

    var gmsMapViewType by remember { mutableStateOf(kGMSTypeNormal) }
    var didMapTypeChange by remember { mutableStateOf(false) }

    var didCameraPositionLatLongBoundsChange by remember { mutableStateOf(false) }
    var didCameraPositionChange by remember { mutableStateOf(false) }
    var didCameraLocationLatLongChange by remember { mutableStateOf(false) }
    var isMapRedrawTriggered by remember { mutableStateOf(true) }

    // Local UI state
    var isMarkersEnabled by remember { mutableStateOf(true) }
    // var isHeatMapEnabled by remember { mutableStateOf(false) }  // reserved for future use
    var showSomething = remember { false } // leave for testing purposes

    val googleMapView = remember(isMapRedrawTriggered) {
        GMSMapView().apply {
            setMyLocationEnabled(true)
            settings.myLocationButton = true
            settings.setMyLocationButton(true)
            settings.setScrollGestures(true)
            settings.setZoomGestures(true)
            settings.setCompassButton(false)
        }
    }
    val appleMapView = remember(isMapRedrawTriggered) { MKMapView() }

    LaunchedEffect(userLocation, markers) {
        if (userLocation != null) {
            isMapRedrawTriggered = true
        }
        if (markers != null) {
            isMapRedrawTriggered = true
        }
    }

    LaunchedEffect(isMarkersLastUpdatedLocationVisible) {
        isMapRedrawTriggered = true
    }

    LaunchedEffect(seenRadiusMiles) {
        isMapRedrawTriggered = true
    }

    LaunchedEffect(cameraLocationBounds) {
        if (cameraLocationBounds != null) {
            didCameraPositionLatLongBoundsChange = true
        }
    }

    LaunchedEffect(shouldSetInitialCameraPosition) {
        if (shouldSetInitialCameraPosition != null) {
            didCameraPositionChange = true
        }
    }

    LaunchedEffect(shouldCenterCameraOnLatLong) {
        if (shouldCenterCameraOnLatLong != null) {
            didCameraLocationLatLongChange = true
        }
    }

    // Only used to track selected marker
    val mapMarkers = remember(markers) { mutableStateMapOf<String, GMSMarker>() }
    var selectedMarker by remember(googleMapView.selectedMarker) { mutableStateOf(googleMapView.selectedMarker) }



    LaunchedEffect(shouldShowInfoMarker) {
        if (shouldShowInfoMarker != null) {
            onDidShowInfoMarker()
            selectedMarker = mapMarkers[shouldShowInfoMarker.id]
        }
    }

    Box(modifier.fillMaxSize()) {

        UIKitView(
            modifier = Modifier.fillMaxSize(),
            interactive = true,
            factory = {
                googleMapView.apply {
                    setDelegate(delegate)
                    this.selectedMarker = selectedMarker
                    this.setMyLocationEnabled(true)
                }

                googleMapView
            },
            update = { view ->

                if (isTrackingEnabled) {
                    userLocation?.let { myLocation ->
                        view.animateWithCameraUpdate(
                            GMSCameraUpdate.setTarget(
                                CLLocationCoordinate2DMake(
                                    latitude = myLocation.latitude,
                                    longitude = myLocation.longitude
                                )
                            )
                        )
                    }
                } else {
                    if (!isMapSetupCompleted) { // Sets the camera once during setup, this allows the user to move the map around
                        shouldSetInitialCameraPosition?.let { cameraPosition ->
                            view.animateWithCameraUpdate(
                                GMSCameraUpdate.setTarget(
                                    CLLocationCoordinate2DMake(
                                        latitude = cameraPosition.target.latitude,
                                        longitude = cameraPosition.target.longitude
                                    )
                                )
                            )
                        }
                    }
                }

                // set the map up only once, this allows the user to move the map around
                if (!isMapSetupCompleted) {
                    view.settings.setAllGesturesEnabled(true)
                    view.settings.setScrollGestures(true)
                    view.settings.setZoomGestures(true)
                    view.settings.setCompassButton(false)

                    view.myLocationEnabled = true // show the users dot
                    view.settings.myLocationButton = false // we use our own location circle

                    isMapSetupCompleted = true
                }

                if (didMapTypeChange) {
                    didMapTypeChange = false
                    view.mapType = gmsMapViewType
                }

                if (didCameraPositionChange) {
                    didCameraPositionChange = false
                    shouldSetInitialCameraPosition?.let { cameraPosition ->
                        view.setCamera(
                            GMSCameraPosition.cameraWithLatitude(
                                cameraPosition.target.latitude,
                                cameraPosition.target.longitude,
                                cameraPosition.zoom // Note Zoom level is forced here, which changes user's zoom level
                            )
                        )
                    }
                }

                if (didCameraLocationLatLongChange) {
                    didCameraLocationLatLongChange = false
                    shouldCenterCameraOnLatLong?.let { cameraLocation ->
                        view.animateWithCameraUpdate(
                            GMSCameraUpdate.setTarget(
                                CLLocationCoordinate2DMake(
                                    latitude = cameraLocation.latitude,
                                    longitude = cameraLocation.longitude
                                )
                            )
                        )
                    }
                }

                if (didCameraPositionLatLongBoundsChange) {
                    didCameraPositionLatLongBoundsChange = false
                    cameraLocationBounds?.let { cameraPositionLatLongBounds ->
                        var bounds = GMSCoordinateBounds()

                        cameraPositionLatLongBounds.coordinates.forEach { latLong ->
                            bounds = bounds.includingCoordinate(
                                CLLocationCoordinate2DMake(
                                    latitude = latLong.latitude,
                                    longitude = latLong.longitude
                                )
                            )
                        }
                        view.animateWithCameraUpdate(
                            GMSCameraUpdate.fitBounds(
                                bounds,
                                cameraPositionLatLongBounds.padding.toDouble()
                            )
                        )
                    }
                }

                if (isMapRedrawTriggered) {
                    // reset the markers & polylines, selected marker, etc.
                    val oldSelectedMarker = view.selectedMarker
                    var curSelectedMarker: GMSMarker? = null
                    val curSelectedMarkerId = view.selectedMarker?.userData as? String
                    view.clear()

                    // render the user's location "talk" circle
                    userLocation?.let {
                        GMSCircle().apply {
                            position = CLLocationCoordinate2DMake(
                                userLocation.latitude,
                                userLocation.longitude
                            )
                            radius = seenRadiusMiles.milesToMeters()
                            fillColor = UIColor.blueColor().colorWithAlphaComponent(0.4)
                            strokeColor = UIColor.whiteColor().colorWithAlphaComponent(0.8)
                            strokeWidth = 2.0
                            map = view
                        }
                    }

                    // render the "lastMarkerCacheUpdateLocation" circle
                    // Show Last cache loaded location
                    if (isMarkersLastUpdatedLocationVisible) {
                        cachedMarkersLastUpdatedLocation?.let { cachedMarkersLastUpdatedLocation ->
                            GMSCircle().apply {
                                position = CLLocationCoordinate2DMake(
                                    cachedMarkersLastUpdatedLocation.latitude,
                                    cachedMarkersLastUpdatedLocation.longitude
                                )
                                radius = 100.0
                                fillColor = UIColor.yellowColor().colorWithAlphaComponent(0.1)
                                strokeColor = UIColor.whiteColor().colorWithAlphaComponent(0.3)
                                strokeWidth = 2.0
                                map = view
                            }
                        }
                    }

                    // render the markers
                    if (isMarkersEnabled) {
                        mapMarkers.clear()
                        markers?.forEach { marker ->
                            val tempMarker = GMSMarker().apply {
                                position = CLLocationCoordinate2DMake(
                                    marker.position.latitude,
                                    marker.position.longitude
                                )
                                title = marker.title
                                userData = marker.id
                                icon = if(marker.isSeen)
                                    markerImageWithColor(UIColor.grayColor())
                                else
                                    markerImageWithColor(UIColor.redColor())
                                map = view
                                snippet = marker.id
                            }
                            mapMarkers[marker.id] = tempMarker

                            if (tempMarker.userData as String == curSelectedMarkerId) {
                                curSelectedMarker = tempMarker
                            }
                        }
                    }

                    // render the polyline
                    polyLine?.let { polyLine ->
                        val points = polyLine.map {
                            CLLocationCoordinate2DMake(it.latitude, it.longitude)
                        }
                        val path = GMSMutablePath().apply {
                            points.forEach { point ->
                                addCoordinate(point)
                            }
                        }

                        GMSPolyline().apply {
                            this.path = path
                            this.map = view
                        }
                    }

                    // re-select the marker (if it was selected before)
                    oldSelectedMarker?.let { _ ->
                        view.selectedMarker = curSelectedMarker
                    }

                    isMapRedrawTriggered = false
                }

                selectedMarker?.let { selectedMarker ->
                    view.setSelectedMarker(selectedMarker)
                }
            },
        )
    }
}

// https://mapstyle.withgoogle.com/
fun mapStyle1(): String {
    return """
    [
  {
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#242f3e"
      }
    ]
  },
  {
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#746855"
      }
    ]
  },
  {
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#242f3e"
      }
    ]
  },
  {
    "featureType": "administrative.land_parcel",
    "elementType": "labels",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "administrative.locality",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#d59563"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#d59563"
      }
    ]
  },
  {
    "featureType": "poi.business",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#263c3f"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#6b9a76"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#38414e"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#212a37"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9ca5b3"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#746855"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#1f2835"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#f3d19c"
      }
    ]
  },
  {
    "featureType": "road.highway.controlled_access",
    "stylers": [
      {
        "visibility": "simplified"
      }
    ]
  },
  {
    "featureType": "road.highway.controlled_access",
    "elementType": "geometry",
    "stylers": [
      {
        "visibility": "simplified"
      }
    ]
  },
  {
    "featureType": "road.highway.controlled_access",
    "elementType": "labels",
    "stylers": [
      {
        "visibility": "on"
      }
    ]
  },
  {
    "featureType": "road.local",
    "elementType": "labels",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "transit",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#2f3948"
      }
    ]
  },
  {
    "featureType": "transit.station",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#d59563"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#17263c"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#515c6d"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#17263c"
      }
    ]
  }
]
    """.trimIndent()

}
