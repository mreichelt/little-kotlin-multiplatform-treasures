import okio.FileSystem
import okio.Path.Companion.toPath

fun findTreasure() {
    if (Platform.osFamily != OsFamily.LINUX) return

    println()

    val metadata = FileSystem.SYSTEM.metadataOrNull("/usr/bin".toPath())
    if (metadata != null && metadata.isDirectory) {
        println("🗃💎👑 Treasure found: Build Docker image with Kotlin Native")
    } else {
        // no /usr/bin folder - smaller image size :-)
        println("🗃💎👑 Treasure found: Tiny Docker image")
    }
}
