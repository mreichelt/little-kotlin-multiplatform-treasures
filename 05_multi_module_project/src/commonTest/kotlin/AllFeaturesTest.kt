import kotlin.test.Test
import kotlin.test.assertEquals

class AllFeaturesTest {

    @Test
    fun testPrintAllFeatures() {
        printAllFeatures()
    }

    @Test
    fun hasAllFeatures() {
        assertEquals(expected = 3, allFeatures.size)
    }
}
