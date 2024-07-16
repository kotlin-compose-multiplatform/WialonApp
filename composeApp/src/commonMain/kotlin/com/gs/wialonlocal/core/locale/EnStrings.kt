package com.gs.wialonlocal.core.locale

import cafe.adriel.lyricist.LyricistStrings
import com.gs.wialonlocal.core.locale.Locales
import com.gs.wialonlocal.core.locale.Strings

@LyricistStrings(languageTag = Locales.EN, default = true)
internal val EnStrings = Strings(
    monitoring = "Monitoring",
    map = "Map",
    reports = "Reports",
    notifications = "Notification",
    geofences = "Geofences",
    status = "Status",
    settings = "Settings",

    sendCommands = "Send Commands",
    shareLocation = "Share Location",
    navigationApps = "Navigation Apps",
    copyCoordinates = "Copy Coordinates",
    executeReports = "Execute Reports",
    edit = "Edit",
    configureTabView = "Configure tab view"
)