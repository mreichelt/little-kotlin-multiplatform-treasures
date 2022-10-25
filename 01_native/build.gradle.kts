plugins {
    kotlin("multiplatform") version "1.7.20"
}

kotlin {
    macosX64 {
        binaries { executable() }
    }
    macosArm64 {
        binaries { executable() }
    }
    linuxX64 {
        binaries { executable() }
    }
    mingwX64 {
        binaries { executable() }
    }
    js {
        browser()
        binaries.executable()
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by sourceSets.getting

        val otherMain by creating {
            val otherMainRef = this
            dependsOn(commonMain)
            listOf(
                "macosX64Main",
                "macosArm64Main",
                "mingwX64Main",
                "jsMain",
                "iosX64Main",
                "iosArm64Main",
                "iosSimulatorArm64Main",
            ).forEach { sourceSetName ->
                get(sourceSetName).dependsOn(otherMainRef)
            }
        }

    }
}
