plugins {
    kotlin("multiplatform") version "1.7.20"
}

kotlin {
    jvm()
    js {
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()
}
