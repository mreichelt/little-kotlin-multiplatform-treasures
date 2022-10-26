include(":01_native")
include(":02_location_distance")
include(":05_multi_module_project")

listOf(1, 2, 3).forEach { i ->
    include(":feature$i")
    project(":feature$i").apply {
        name = "feature$i"
        projectDir = file("05_multi_module_project/feature$i")
    }
}

include(":06_publish")

listOf("A", "B", "C").forEach { i ->
    include(":feature$i")
    project(":feature$i").apply {
        name = "feature$i"
        projectDir = file("06_publish/feature$i")
    }
}

rootProject.name = "Treasures"
