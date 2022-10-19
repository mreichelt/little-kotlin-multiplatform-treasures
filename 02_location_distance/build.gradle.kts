plugins {
    kotlin("multiplatform") version "1.7.20"
}

kotlin {
    macosArm64 {
        binaries { executable() }
    }
    linuxX64 {
        binaries { executable() }
    }
}
