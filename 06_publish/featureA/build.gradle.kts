plugins {
    kotlin("multiplatform") version "1.7.20"
    `maven-publish`
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
