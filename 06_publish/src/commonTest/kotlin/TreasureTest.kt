import kotlin.test.Test
import kotlin.test.assertTrue

class TreasureTest {

    @Test
    fun treasureShouldBeValid() {
        println(treasure)

        assertTrue(treasure.startsWith("🗃💎👑"))
    }

}
