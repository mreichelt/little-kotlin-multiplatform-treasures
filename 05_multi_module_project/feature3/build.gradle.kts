plugins {
    kotlin("multiplatform") version "1.7.20"
}

kotlin {
    jvm()
    js {
        browser()
    }

    ios()
    iosSimulatorArm64()
}
