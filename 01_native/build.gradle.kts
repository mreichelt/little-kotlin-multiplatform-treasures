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
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }
}
