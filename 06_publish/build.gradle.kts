import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.security.MessageDigest

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

    val featureProjects = allprojects
        .filter { it.name in listOf("featureA", "featureB", "featureC") }

    val xcFramework = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "06_publish"
            xcFramework.add(this)
            featureProjects.forEach { project ->
                export(project)
            }
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

val frameworkName = "06_publish"

val zipXCFramework by tasks.register<Zip>("zipXCFramework") {
    dependsOn("assembleXCFramework")

    from(layout.buildDirectory.dir("XCFrameworks/release"))
    include("$frameworkName.xcframework/**")
    archiveFileName.set("$frameworkName.zip")
    isPreserveFileTimestamps = false // same contents will produce exact same SHA256 sum
    destinationDirectory.set(layout.buildDirectory.dir("swiftPackage"))
}

val computeChecksumZipXCFramework by tasks.register("computeChecksumZipXCFramework") {
    dependsOn(zipXCFramework)

    val inputZipFile = layout.buildDirectory.file("swiftPackage/$frameworkName.zip")
    inputs.file(inputZipFile)
    val outputChecksumFile = layout.buildDirectory.file("swiftPackage/$frameworkName.zip.sha256")
    outputs.file(outputChecksumFile)

    doLast {
        fun sha256(byteArray: ByteArray): String {
            val digest = MessageDigest.getInstance("SHA-256").digest(byteArray)
            // convert to hex
            return digest.fold("") { str, it -> str + "%02x".format(it) }
        }

        val checksum = sha256(inputZipFile.get().asFile.readBytes())
        outputChecksumFile.get().asFile.writeText(checksum)
    }
}

// a URL where the artifact is going to be available for download - could be any URL, not just artifactory
val artifactUrl =
    "http://localhost:8081/artifactory/droidcon-generic/06_shared/release/$version/$frameworkName.zip"

val createSwiftPackageManifest by tasks.register("createSwiftPackageManifest") {
    dependsOn(
        computeChecksumZipXCFramework,
        "assembleXCFramework",
    )

    val inputChecksumFile =
        layout.buildDirectory.file("swiftPackage/$frameworkName.zip.sha256")
    inputs.file(inputChecksumFile)
    inputs.property("version", version)

    val outputManifestFile = layout.buildDirectory.file("swiftPackage/Package.swift")
    outputs.file(outputManifestFile)

    doLast {
        // a URL where the artifact is going to be available for download - could be any URL, not just artifactory
        val sha256 = inputChecksumFile.get().asFile.readText().trim()
        val manifest = """
        // swift-tools-version:5.5
        import PackageDescription

        let package = Package(
            name: "$frameworkName",
            platforms: [
                .iOS(.v14)
            ],
            products: [
                .library(
                    name: "$frameworkName",
                    targets: ["$frameworkName"]
                ),
            ],
            targets: [
                .binaryTarget(
                    name: "$frameworkName",
                    url: "$artifactUrl",
                    checksum: "$sha256"
                ),
            ]
        )
        """.trimIndent()
        outputManifestFile.get().asFile.writeText(manifest)
    }
}

val uploadFrameworkZipToArtifactory by tasks.register<Exec>("uploadFrameworkZipToArtifactory") {
    dependsOn(zipXCFramework)

    val frameworkZip = layout.buildDirectory.file("swiftPackage/$frameworkName.zip").get().asFile
    workingDir(layout.buildDirectory.dir("swiftPackage"))

    executable("curl")

    val username = System.getenv("DROIDCON_USERNAME") ?: ""
    val password = System.getenv("DROIDCON_PASSWORD") ?: ""
    args(
        "--user", "$username:$password",
        "--data-binary", "@" + frameworkZip.absolutePath,
        "--request", "PUT",
        "--fail", // fails on 403 errors (sets exit code)
        artifactUrl,
    )
}

val deploySwiftPackageManifestToGit by tasks.register<Exec>("deploySwiftPackageManifestToGit") {
    dependsOn(createSwiftPackageManifest)

    workingDir(layout.buildDirectory.dir("swiftPackage"))
    executable(layout.projectDirectory.file("deploy_swiftpackage_manifest_to_git.sh"))
    args(version)
}

val deploySwiftPackage = tasks.register("deploySwiftPackage") {
    dependsOn(
        createSwiftPackageManifest,
        uploadFrameworkZipToArtifactory,
        deploySwiftPackageManifestToGit,
    )
    uploadFrameworkZipToArtifactory.mustRunAfter(createSwiftPackageManifest)
    deploySwiftPackageManifestToGit.mustRunAfter(uploadFrameworkZipToArtifactory)
}
