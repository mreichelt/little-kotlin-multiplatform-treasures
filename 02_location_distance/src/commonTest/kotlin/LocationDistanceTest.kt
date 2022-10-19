import kotlin.test.Test
import kotlin.test.assertEquals

class LocationDistanceTest {

    @Test
    fun distanceBetweenTwoLocations() {
        val droidconLondon = Location(latitude = 51.5355432, longitude = -0.1056121)
        val frankfurt = Location(latitude = 50.110924, longitude = 8.682127)
        val distance = distance(droidconLondon, frankfurt)

        println("Distance between Droidcon London and Frankfurt is $distance meters")

        assertEquals(expected = 636846.19, actual = distance, absoluteTolerance = 0.01)

        val reverseDistance = distance(frankfurt, droidconLondon)
        // reverse should be the same
        assertEquals(expected = 636846.19, actual = reverseDistance, absoluteTolerance = 0.01)
    }

    @Test
    fun distanceBetweenSameLocationsShallBeZero() {
        val droidconLondon = Location(latitude = 51.5355432, longitude = -0.1056121)
        val frankfurt = Location(latitude = 50.110924, longitude = 8.682127)

        assertEquals(0.0, distance(droidconLondon, droidconLondon))
        assertEquals(0.0, distance(frankfurt, frankfurt))
    }

}
