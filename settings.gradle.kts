include(":01_native")
include(":02_location_distance")
include(":05_multi_module_project")

(1..3).forEach { i ->
    include(":feature$i")
    project(":feature$i").apply {
        name = "feature$i"
        projectDir = file("05_multi_module_project/feature$i")
    }
}

rootProject.name = "Treasures"
