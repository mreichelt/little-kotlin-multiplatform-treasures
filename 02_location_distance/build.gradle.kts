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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.squareup.okio:okio:3.2.0") {
                    because("access file system info on native")
                }
            }
        }
    }
}
