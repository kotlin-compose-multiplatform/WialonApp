package com.gs.wialonlocal.core.locale

import cafe.adriel.lyricist.LyricistStrings
import com.gs.wialonlocal.core.locale.Locales
import com.gs.wialonlocal.core.locale.Strings

@LyricistStrings(languageTag = Locales.EN)
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
    configureTabView = "Configure tab view",
    infoSettings = "“ Info” tab settings",
    mapSettings = "Map settings",
    infoSettingsDescription = "Select  the sections you want to see on the “Info” tab of units. These settings are applied to all units.",
    customFields = "Custom fields",
    sensors = "Sensors",
    profile = "Profile",
    hardware = "Hardware",
    counters = "Counters",
    parameters = "Parameters",
    trailers = "Trailers",
    drivers = "Drivers",
    altitude = "Altitude",
    satellites = "Satellites",
    mapSource = "Map source",
    traffic = "Traffic",
    unitIcons = "Unit icons",
    unitNames = "Unit names",
    driverName = "Driver names",
    iconGrouping = "Icon grouping",
    unitTrace = "Unit trace",
    zoomButtons = "Zoom buttons",
    selectItems = "Select Items",
    configureView = "Configure unit view",
    search = "Search",
    workList = "Work list generation mode",
    showAll = "Show All",
    showAllDescription = "All units and groups are shown. The option works if you have 20 unit or fewer. If you have more, you should configure the work list manually",
    synchronizeWeb = "Synchronize with the web version",
    synchronizeWebDesc = "The work list is synchronized with that from the web version. you can’t edit it  manually",
    configureManually = "Configure manually",
    configureManuallyDesc = "You configure the work list manually",
    unitView = "Unit view",
    title = "Title",
    subTitle = "Subtitle",
    unitName = "Unit name",
    indicatorsAndSensors = "Indicators and sensors",
    bottomRow = "Buttom row",
    address = "Address",
    none = "None",
    indicators = "Indicators",
    speed = "Speed",
    ignition = "Ignition",
    motionParkingTime = "Motion and parking start time",
    motionParkingDuration = "Motion and parking duration",
    currentMileage = "Mileage in current trip",
    fuelLevel = "Fuel level",
    battery = "Battery",
    power = "Power",
    workListEmpty = "The Work list is empty",
    emptyDescription = "Tap the icon in the top bar to add units",
    template = "Template",
    unit = "Unit",
    notSelected = "Not selected",
    interval = "Interval",
    orientation = "Orientation",
    portrail = "Portrait",
    executeReport = "Execute report",
    today = "Today",
    yesterday = "Yesterday",
    week = "Week",
    month = "Month",
    landscape = "Landscape",
    cancel = "Cancel",
    showDescription = "Show description",
    copyGeofences = "Copy geofences",
    makeInvisible = "Make invisible",
    online = "Online",
    offline = "Offline",
    stationary = "Stationary",
    ignitionOn = "Stationary with ignition on",
    moving = "Moving",
    lbsDetected = "LBS detected data",
    noActualState = "No actual state",
    noMessages = "No messages",
    tapToChange = "Tap to change account",
    interfaceCustom = "Interface customization",
    theme = "Theme",
    sameAsDevice = "Same as device",
    navigationBar = "Navigation bar",
    pushNotification = "Push notificaton",
    other = "Other",
    geoFencesAsAddress = "Geofences as adresses",
    autoLock = "Auto-Lock",
    enabled = "Enabled",
    help = "Help",
    feedback = "Feedback",
    light = "Light",
    dark = "Dark",
    disabled = "Disabled",
    disabledWhileCharging = "Disabled while charging",
    visibleTabs = "Visible tabs",
    hiddenTabs = "Hidden tabs",
    dragToHide = "Drag here to hide a tap",
    hideDescription = "After the tab is hidden, akk related functionality in the application is hidden too.",
    feedback1 = "The location of the unit is displayed incorrectly on the map",
    feedback10 = "The monitoring system displays the data sent by equipment. If the location of the unit in the mobile application or monitoring system is incorrect, it is recommended to check the equipment and its settings.",
    feedback2 = "Push notifications do not work in the application",
    feedback20 = "Make sure that:\n" +
            "• the \"Notifications\" option is enabled in the application settings;\n" +
            "• sending mobile notifications is configured in the web interface of the monitoring system;\n" +
            "• the \"Mobile notifications\" service is activated for your account.\n" +
            "If you lack access rights to perform these actions, contact your service provider.",
    feedback3 = "The list of units in the application is different from the list in the web interface",
    feedback30 = "Activate the \"Units from web monitoring panel\" switch to synchronize the worklist of the application with the worklist of the monitoring panel of the web version. When this option is enabled, manual editing of the list of units is not possible.\n" +
            "When you first log into the application, the worklist contains all the units displayed in the worklist of the monitoring panel of the web version.",
    feedback4 = "Events are not displayed",
    feedback40 = "Possible reasons:\n" +
            "• The upload of data from the black box for an interval over 24 hours.\n" +
            "The system automatically generates events based on the messages received in the last 24 hours. The messages generated more than 24 hours ago are not registered as events after being uploaded from the black box.\n" +
            "• The trip detection settings.\n" +
            "The mobile application uses GPS-speed to determine the movement regardless of the settings of the trip detection in the web version, that is why the specified value of the minimum speed must be strictly greater than 0. In addition, the \"Allow GPS correction\" block of the settings is always used when determining the intervals of movement.",
    feedback5 = "The information in the application is different from the information in the web interface",
    feedback50 = "Some values of event parameters (time intervals, fuel volume, location) may differ from the values of similar parameters in the reports of the monitoring system. This is due to the use of various calculation systems in the web interface and the mobile application. For instance, when determining fuel fillings/thefts in the mobile application, the time of the last message received before the beginning of the fuel level change is used. In contrast, when determining fuel fillings/thefts in the monitoring system, the time of the first message from the interval of the highest fuel level drop is used. In addition, the state of the unit movement is determined in the mobile application only by GPS-speed, while in the monitoring system other methods can be used.\n" +
            "Note that in the mobile application, the correctness of the data received on the basis of an event depends on the parameters of the trip detection.",
    feedback6 = "My problem is not on the list",
    feedback60 = "",
    viewDocs = "View documentation",
    description = "Description",
    groups = "GROUPS",
    units = "UNITS",
    language = "Language",
    history = "History",
    info = "Info"
)