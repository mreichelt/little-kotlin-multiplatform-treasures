import kotlin.test.Test
import kotlin.test.assertContains
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

    @Test
    fun hasTreasure() {
        val actualTreasure = getTreasure()
        println(actualTreasure)

        assertContains(actualTreasure, "🗃💎👑")
    }
}
