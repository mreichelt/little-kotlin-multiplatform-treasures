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
}
