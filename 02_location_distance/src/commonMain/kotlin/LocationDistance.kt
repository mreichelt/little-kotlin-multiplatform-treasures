import kotlin.math.*

fun main() {
    val droidconLondon = Location(latitude = 51.5355432, longitude = -0.1056121)
    val frankfurt = Location(latitude = 50.110924, longitude = 8.682127)

    println("Hello, Droidcon London!")

    val distanceKm = distance(droidconLondon, frankfurt) / 1000.0
    println("The distance between us and Frankfurt is ${distanceKm.roundToInt()} km")

    findTreasure()
}

data class Location(val latitude: Double, val longitude: Double)

/**
 * Gets the distance between two locations in meters, using haversine formula.
 */
fun distance(location1: Location, location2: Location): Double {
    val lat1 = location1.latitude.toRadians()
    val lat2 = location2.latitude.toRadians()

    val latitudeDiff = (location2.latitude - location1.latitude).toRadians()
    val longitudeDiff = (location2.longitude - location1.longitude).toRadians()

    val a = sin(latitudeDiff / 2).pow(2) +
            cos(lat1) * cos(lat2) * sin(longitudeDiff / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    val earthRadius = 6371000
    return earthRadius * c
}

/** degrees to radians */
private fun Double.toRadians() = this * PI / 180
