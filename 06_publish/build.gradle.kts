plugins {
    kotlin("multiplatform") version "1.7.20"
    `maven-publish`
    // the maven-publish configuration is in the top-level build.gradle.kts
}

kotlin {
    jvm()
    js {
        browser()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "06_publish"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // or api(...) if you want to export their APIs
                implementation(project(":featureA"))
                implementation(project(":featureB"))
                implementation(project(":featureC"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsMain by getting {
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}
